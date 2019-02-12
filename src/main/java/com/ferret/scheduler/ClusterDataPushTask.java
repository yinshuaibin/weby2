package com.ferret.scheduler;

import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.AlarmInterface;
import com.ferret.bean.CameraInfo;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.open.bean.ClusterDataImage;
import com.ferret.open.bean.ClusterDataWei;
import com.ferret.open.service.ClusterDataPushService;
import com.ferret.service.alarm.impl.AlarmServiceImpl;
import com.ferret.socket.bean.RealTimeImage;
import com.ferret.utils.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class ClusterDataPushTask {

    @Value("${dataPush.sign}")
    private Integer dataPush;
    @Value("${image.prefix.historyDir}")
    private String historyDir;
    @Value("${image.prefix.historyUrl}")
    private String historyUrl;
    @Value("${dataPush.clusterDataPushUrl}")
    private String clusterDataPushUrl;
    @Value("${dataPush.clusterWeiPushUrl}")
    private String clusterWeiPushUrl;
    @Value("${dataPush.alarmPushUrl}")
    private String alarmPushUrl;
    @Value("${dataPush.clusterWeiPushUrl}")
    private String realTimePushUrl;
    @Autowired
    private CameraInfoMapper cameraInfoMapper;
    private static String idSign = null; //聚类档案推送标识: null 从数据库读取第一次推送点 (id)
    private static String timeSing = null; //维族人数据推送标识: null 从数据库读取第一次推送点 (create_time)
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = null;
    @Autowired
    private AlarmServiceImpl realTimeAlarmService;

    private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

    private JSONObject jsonObject = new JSONObject();

    @Autowired
    private ClusterDataPushService clusterDataPushService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void clusterDataPushTask(){
        //判断是否推送聚类数据———维族人数据   0推送   1不推送
        try {
            if (dataPush == 0) {
                // 推送聚类数据 （idSign为null  数据库中读取第一次推送点，否则从idSign的值开始推送数据）
                if (StringUtils.isBlank(idSign)) {
                    String id = clusterDataPushService.firstClusterDataPush();
                    operatorClusterData(id);
                } else {
                    operatorClusterData(idSign);
                }
                // 推送维族人数据 （timeSing为null  数据库中读取第一次推送点，否则从timeSing的值开始推送数据）
                if (StringUtils.isBlank(timeSing)) {
                    String createTime = clusterDataPushService.firstClusterWeiPush();
                    if (createTime.contains(".")){
                        createTime = createTime.substring(0,createTime.indexOf("."));
                    }
                    operatorClusterWei(createTime);
                } else {
                    operatorClusterWei(timeSing);
                }
            }
        } catch (Exception e) {
            log.info("推送失败。。。" + e.getMessage());
        }
    }
    // 普通聚类档案 使用restTemplate异步推送数据
    private void operatorClusterData(String id){
        // 设置 restTemplate的请求头
        if (headers == null){
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        }
        // 获取该id 之后的数据
        List<ClusterDataImage> clusterDataPushes = clusterDataPushService.clusterDataPush(id);
        if (clusterDataPushes != null && clusterDataPushes.size() > 0){
            //设置当前查询到的 最大id （下次从当前id开始查询）
            idSign = clusterDataPushes.get(clusterDataPushes.size() - 1).getId();
            // 更新数据库当前查询到的峰值 id
            if (StringUtils.isNotBlank(idSign)){
                clusterDataPushService.updateClusterImgId(idSign);
            }
            //异步处理数据并 推送数据
            CompletableFuture.runAsync(() ->{
                try {
                    String sendTime = dateFormat.format(new Date());
                    for (ClusterDataImage clusterDataPush :clusterDataPushes) {
                        clusterDataPush.setSendTime(sendTime);
                        // 时间处理 （数据库中查询出来的 2018-12-06 16:53:04.0）
                        String imageTime = clusterDataPush.getImageTime();
                        if (imageTime.contains(".")){
                            imageTime = imageTime.substring(0,imageTime.indexOf("."));
                            clusterDataPush.setImageTime(imageTime);
                        }

                        // 替换 图片路径
                        if (StringUtils.isNotBlank(clusterDataPush.getImagePath())) {
                            String replace = clusterDataPush.getImagePath().replace("\\", "/").replace(historyDir, historyUrl);
                            clusterDataPush.setImagePath(replace);
                        }
                    }
                    String resultJson = JSONObject.toJSONString(clusterDataPushes);
                    restTemplate.postForObject(clusterDataPushUrl, new HttpEntity<Object>(resultJson, headers), Object.class);
                } catch (Exception e) {
                    log.info("推送失败。。。" + e.getMessage());
                }
            });
        }
        // System.err.println(clusterDataPushes.size()+"test-----------"+dateFormat.format(new Date()));
    }
    // 维族人 使用restTemplate异步推送数据
    private void operatorClusterWei(String createTime){
        // 设置 restTemplate的请求头
        if (headers == null){
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        }
        // 获取该时间点  之后的数据
        List<ClusterDataWei> clusterDataWeis = clusterDataPushService.clusterWeiData(createTime);
        if (clusterDataWeis != null && clusterDataWeis.size() > 0){
            //设置当前查询到的 最大id （下次从当前id开始查询）
            timeSing = clusterDataWeis.get(clusterDataWeis.size() - 1).getCreateTime();
            // 更新数据库当前查询到的峰值 id
            if (StringUtils.isNotBlank(timeSing)){
                clusterDataPushService.updateClusterWeiTime(timeSing);
            }
            //异步处理数据并 推送数据
            CompletableFuture.runAsync(() ->{
                try {
                    String sendTime = dateFormat.format(new Date());
                    for (ClusterDataWei clusterDataWei :clusterDataWeis) {
                        clusterDataWei.setSendTime(sendTime);
                        // 时间处理 （数据库中查询出来的 2018-12-06 16:53:04.0）
                        String imageTime = clusterDataWei.getCreateTime();
                        if (imageTime.contains(".")){
                            imageTime = imageTime.substring(0,imageTime.indexOf("."));
                            clusterDataWei.setCreateTime(imageTime);
                        }
                        // 替换 图片路径
                        if (StringUtils.isNotBlank(clusterDataWei.getRepresentImg1())){
                            String replace = clusterDataWei.getRepresentImg1().replace("\\", "/").replace(historyDir, historyUrl);
                            clusterDataWei.setRepresentImg1(replace);
                        }
                        if (StringUtils.isNotBlank(clusterDataWei.getRepresentImg5())){
                            String replace = clusterDataWei.getRepresentImg5().replace("\\", "/").replace(historyDir, historyUrl);
                            clusterDataWei.setRepresentImg5(replace);
                        }
                    }
                    String resultJson = JSONObject.toJSONString(clusterDataWeis);
                    restTemplate.postForObject(clusterWeiPushUrl, new HttpEntity<Object>(resultJson, headers), Object.class);
                } catch (Exception e) {
                    log.info("推送失败。。。" + e.getMessage());
                }
            });
        }
        // System.err.println(clusterDataWeis.size()+"wei-----------"+dateFormat.format(new Date()));
    }
    /**
     * @Descriptions 向中间件推送报警数据
     * @Author xyc
     * @Data 2018-12-07
     */
    public void postAlarmMessage(String url,String bkId,String ip){
        String base = ImageBase64Utils.generateBase64ImageEntity(url).getImageData();
        AlarmInterface alarmInterface = realTimeAlarmService.findAlarmBk(Integer.valueOf(bkId));
        alarmInterface.setIp(ip);
        alarmInterface.setImageData(base);

        jsonObject.clear();
        jsonObject = (JSONObject) JSONObject.toJSON(alarmInterface);

        // 异步请求发送请求
        if (headers == null){
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        }
        HttpEntity<Object> formEntity = new HttpEntity<Object>(jsonObject, headers);
        try {
            asyncRestTemplate.postForEntity(alarmPushUrl,formEntity,String.class);
        } catch (Exception e) {
            log.info("实时报警推送失败。。。" + e.getMessage());
        }
    }
    /**
     * @Descriptions 向中间件推送实时抓拍数据
     * @Author xyc
     * @Data 2018-12-07
     */
    public void pushMessageHandler(RealTimeImage realTimeImage) throws ParseException {
        String base = ImageBase64Utils.generateBase64ImageEntity(realTimeImage.getImageUrl()).getImageData();
        // 查询实时抓拍的相机信息.
        CameraInfo cameraInfo = cameraInfoMapper.findById(realTimeImage.getCameraId());

        String time = realTimeImage.getTime().substring(0,14);
        time = dateFormat.format(sdf.parse(time));
        jsonObject.clear();
        jsonObject.put("imageData",base.toString());
        jsonObject.put("createTime",time);
        jsonObject.put("cameraIp",cameraInfo.getIp());
        jsonObject.put("cameraName",cameraInfo.getName());
//        System.err.println(jsonObject.toJSONString());
        // 异步请求发送请求
        if (headers == null){
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        }
        HttpEntity<Object> formEntity = new HttpEntity<Object>(jsonObject, headers);
        try {
            asyncRestTemplate.postForEntity(realTimePushUrl,formEntity,String.class);
        } catch (Exception e) {
            log.info("实时抓拍推送失败。。。" + e.getMessage());
        }
    }
}
