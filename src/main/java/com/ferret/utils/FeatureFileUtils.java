package com.ferret.utils;

import com.alibaba.fastjson.JSON;
import com.ferret.bean.BuKong;
import com.ferret.bean.FeatureData;
import com.ferret.bean.ImageFaceInfo;
import com.ferret.bean.ImageFeature;
import com.ferret.dao.SqliteDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 特征文件和数据库读写工具类
 *
 * @author cc;
 * @since 2018/4/17;
 */
@Slf4j
@Component
public class FeatureFileUtils {

    private static AtomicInteger integer = new AtomicInteger(1);

    // 布控特征保存父目录
    private static String parentFile = "/feature/";

    /**
     * 有序map,保存图片的 描述数据<ImageFaceInfo> 和 特征数据<String>
     */
    public final static Map<ImageFaceInfo, byte[]> map = Collections.synchronizedMap(new LinkedHashMap<>());

    @Autowired
    private SqliteDao sqliteDao;

    @Autowired
    private ImagePrefixProperties imagePrefixProperties;

    // 获取实例
    public static FeatureFileUtils getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private FeatureFileUtils singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton() {
            singleton = new FeatureFileUtils();
        }

        public FeatureFileUtils getInstance() {
            return singleton;
        }
    }


    /**
     * 保存布控对象到map中,其中map每保存1w条记录时,保存一次.
     *
     * @param imageFeature 特征对象
     * @param buKong       布控对象
     */
    public void put(ImageFeature imageFeature, BuKong buKong) throws Exception {

        // 如果key或者value有空值,直接抛出异常
        if (imageFeature == null || buKong == null) {
            throw new NullPointerException("布控特征对象或布控对象为空,请检查参数");
        }

        // 获取特征数组
        ImageFeature.ResInfo[] results = imageFeature.getResInfo();

        // 判断特征数组是否为空
        Assert.isTrue(ArrayUtils.isNotEmpty(results), imageFeature.getError());

        // 保存特征数据到map

        ImageFaceInfo faceInfo = results[0].convertToImageFaceInfo();
        faceInfo.setFeatureId(buKong.getFeatureId());
        faceInfo.setImagePath(buKong.getImagePath());
        faceInfo.setFaceNumber(imageFeature.getFacenum());
        // 同时只能有一个线程获取map的size并且往里put数据
        synchronized (map) {
            int offset = map.size() + 1;
            faceInfo.setFeaturePosition(offset);
            map.put(faceInfo, Base64.decodeBase64(results[0].getFeatdata()));
            if (offset == 10000) {
                // 当map达到1w上限,则拷贝一份,然后新建线程写文件
                LinkedHashMap<ImageFaceInfo, byte[]> cloneMap = new LinkedHashMap<>(map);
                map.clear();
                saveFeatureAndInfo(cloneMap);
                integer.getAndIncrement();
            }
        }
    }

    /**
     * 保存特征和信息文件
     *
     * @param map
     */
    public void saveFeatureAndInfo(Map<ImageFaceInfo, byte[]> map) {
        log.info("-------------------------- 当前map的长度为:{}", map.size());
        if (map.isEmpty()) {
            return;
        }
        // 生成文件保存地址, 格式:D:/ftpdata/bk/feature/1/1
        Integer num = integer.get();
        String path = imagePrefixProperties.getBukongDir() + parentFile + num + "/" + num;

        File featureFile = new File(path + FeatureData.FILE_TYPE);
        if (!featureFile.exists()) {
            boolean mkdirs =  featureFile.getParentFile().mkdirs();
            if(!mkdirs){
                log.error("名称为:"+featureFile.getParentFile().getName()+" 的文件夹创建失败,请检查文件路径");
                return;
            }
        }
        // 获取信息数据
        List<ImageFaceInfo> infos = new ArrayList<>(map.keySet());
        // 创建描述数据库
        RandomAccessFile raf = null;
        try {
            sqliteDao.insertBatch(infos, path + FeatureData.DB_TYPE);

            // 获取特征数据
            List<byte[]> features = new ArrayList<>(map.values());
            // 创建特征文件
            byte[] bytes = new FeatureData(features).convertToBytes();
            raf = new RandomAccessFile(featureFile, "rws");
            raf.write(bytes);
        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取特征文件和特征信息,并保存到map中.
     *
     * @param filePath 特征文件地址
     * @param infoPath 信息数据库地址
     */
    public void readFeatureAndInfo(String filePath, String infoPath) {
        Assert.isTrue(!StringUtils.isAnyBlank(filePath, infoPath), "参数有空值,请检查!");

        // 如果已经是1w条记录,则直接结束
        if (sqliteDao.onePackWithinTenThousandRecords(infoPath)) {
            integer.getAndIncrement();
            return;
        }
        // 读取sqlite数据
        List<ImageFaceInfo> infos = null;
        try {
            infos = sqliteDao.getFaceInfos(infoPath);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // 根据文件地址读取对应的文件
        List<byte[]> features = FeatureData.bytesToBean(FileUtils.copyFileToBytes(filePath));
        Assert.isTrue(infos != null && infos.size() == features.size(), "特征文件和信息数据库不一致");

        // 恢复map数据
        for (int i = 0; i < infos.size(); i++) {
            map.put(infos.get(i), features.get(i));
        }
        log.info("从本地读取的特征数据为:" + JSON.toJSONString(map));
    }

    /**
     * 获取特征文件目录
     *
     * @return
     */
    public String getFeatureFilePath() {
        String featurePath = "";
        String pFile = imagePrefixProperties.getBukongDir() + parentFile;
        File file = new File(pFile);
        // 如果布控目录存在, 则读取特征序号
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                integer.set(files.length);
                if (ArrayUtils.isNotEmpty(files[files.length - 1].list())) {
                    featurePath = pFile + files.length + "/" + files.length;
                }
            }
        } else { // 否则,创建目录并返回空
            if (!file.mkdirs()) {
                // 创建目录失败,则输出日志
                log.error("目录 {} 不存在,请修改目录", pFile);
            }
        }
        return featurePath;
    }

    /**
     * 读取特征和信息
     */
    public void readAfterReady() {
        String path = getFeatureFilePath();
        if (StringUtils.isNotBlank(path)) {
            readFeatureAndInfo(path + FeatureData.FILE_TYPE, path + FeatureData.DB_TYPE);
        }
    }

    /**
     * 特征文件和数据库的定时任务,每分钟保存一次
     */
    //@Scheduled(cron = "0/60 * *  * * ? ")   //每1分钟执行一次
    public void save() throws InterruptedException {
        synchronized (map) {
            if (!map.isEmpty()) {
                Map<ImageFaceInfo, byte[]> copiedMap = new LinkedHashMap<>(map);
                saveFeatureAndInfo(copiedMap);
            }
        }
    }
}
