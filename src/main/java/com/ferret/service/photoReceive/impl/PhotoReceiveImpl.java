package com.ferret.service.photoReceive.impl;

import com.alibaba.fastjson.JSON;
import com.ferret.bean.photoReceive.PhotoReceiveResult;
import com.ferret.bean.photoReceive.TokenTask;
import com.ferret.dao.photoReceive.PhotoReceiveMapper;
import com.ferret.service.photoReceive.PhotoReceiveService;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.utils.ImagePrefixProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.*;


@Service
@Slf4j
public class PhotoReceiveImpl implements PhotoReceiveService {

    @Autowired
    private PhotoReceiveMapper photoReceiveMapper;

    @Autowired
    private ImagePrefixProperties imagePrefixProperties;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 图片地址前缀
     */
    private final String imagePathPrefix = "jh/face/";

    /**
     * 图片中间拼接
     */
    private final String imagePathSplice = "jh_";

    @Override
    public String findUrlByToken(String token) {
        return photoReceiveMapper.findUrlByToken(token);
    }

    @Override
    public String makeTask(String token,String base64,String sendImagePath) {
        Map resultMap = new HashMap();
        TokenTask tokenTask = new TokenTask();
        String taskId = UUID.randomUUID().toString();
        // 存入数据库的图片名称
        String imagePathMysql;
        // 此处应该截取,获取相机ip
        try {
            String camersIp = sendImagePath.substring(0,sendImagePath.indexOf("_"));
            tokenTask.setCameraIp(camersIp);
            // 截取时间(到天)
            String pathTimeOfDay = sendImagePath.substring(StringUtils.ordinalIndexOf(sendImagePath, "_", 2) + 1, StringUtils.ordinalIndexOf(sendImagePath, "_", 2) + 9);
            // 按照固定格式, 生成图片名称以及存入数据库的图片名称 例:jh/face/20190820/192.168.1.175_01/jh_192.168.1.175_01_20190820153001001_face_sendtask.jpg
            imagePathMysql = imagePathPrefix +pathTimeOfDay+"/"+sendImagePath.substring(0,StringUtils.ordinalIndexOf(sendImagePath, "_", 2))+"/"+imagePathSplice+sendImagePath;
            tokenTask.setImagePath(imagePathMysql);
        }catch (IndexOutOfBoundsException e){
            log.error("图片名称不符合要求,图片名称为:{},例:{}",sendImagePath,"192.168.1.1_01_2018110200000001_face.jpg");
            resultMap.put("resultcode",1);
            resultMap.put("message","图片名称不符合要求,图片名称为:"+sendImagePath+",例:192.168.1.1_01_2018110200000001_face.jpg");
            // 返回taskId给接口调用者
            return JSON.toJSONString(resultMap);
        }
        tokenTask.setToken(token);
        tokenTask.setTaskId(taskId);
        int i = photoReceiveMapper.insertTokenTask(tokenTask);
        if(i == 1){
            // 将图片生成到本地目录下, 供聚类使用
            // 如果配置文件中的makeCluserDir文件夹不存在,将会抛出FileNotFoundException,此处不做处理
            ImageBase64Utils.generateImage(base64,imagePrefixProperties.getMakeCluserDir()+sendImagePath);
            resultMap.put("resultcode",0);
            resultMap.put("taskid",taskId);
            // 返回taskId给接口调用者
            return JSON.toJSONString(resultMap);
        }
        resultMap.put("message","插入失败");
        resultMap.put("resultcode",1);
        return JSON.toJSONString(resultMap);
    }

    /**
     * 每5分钟执行一次,将已经聚类完成的任务返回给对应的请求方,并删除表中对应的数据
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void sendTaskAndClusterId(){
        List<PhotoReceiveResult> flageIsOne = photoReceiveMapper.findFlageIsOne();
        if(flageIsOne !=null && flageIsOne.size()>1){
            // 按照不同的ulr发送出去,有多少个url就发送多少次
            Set<String> urlSet = new HashSet<>();
            for (PhotoReceiveResult p : flageIsOne){
                String sendUrl = p.getSendUrl();
                if (urlSet.size() < 1 || !urlSet.contains(sendUrl)){
                    List<PhotoReceiveResult> list = new ArrayList<>();
                    // id的集合,删除时使用
                    List<Integer> delIds = new ArrayList<>();
                    for(PhotoReceiveResult p1 : flageIsOne){
                        if(sendUrl.equals(p1.getSendUrl())){
                            delIds.add(p1.getId());
                            list.add(p1);
                        }
                    }
                    try {
                        restTemplate.postForEntity(sendUrl, JSON.toJSONString(list),Object.class);
                        photoReceiveMapper.deleteByids(delIds);
                    }catch (Exception e){
                        log.error("推送地址{}出现错误,请校验此地址,该url的请求数据不再删除,错误信息为:{}",sendUrl,e.getMessage());
                    }
                }
                urlSet.add(sendUrl);
            }
        }
    }
}
