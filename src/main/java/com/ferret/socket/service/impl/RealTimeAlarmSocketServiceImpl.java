package com.ferret.socket.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.RealTimeAlarm;
import com.ferret.bean.User;
import com.ferret.dao.BuKongGroupMapper;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.dao.UserCameraMapper;
import com.ferret.mq.Handler;
import com.ferret.scheduler.ClusterDataPushTask;
import com.ferret.service.alarm.impl.AlarmServiceImpl;
import com.ferret.socket.bean.WebSocketMessage;
import com.ferret.socket.utils.Consumer;
import com.ferret.utils.WebSocketSessionUtils;
import com.ferret.utils.staticUtils.FTPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 报警服务处理.
 *
 * @author cc;
 * @since 2017/12/21;
 */
@Slf4j
@Service
public class RealTimeAlarmSocketServiceImpl implements Handler {
    @Autowired
    private CameraInfoMapper cameraInfoMapper;

    @Autowired
    private AlarmServiceImpl realTimeAlarmService;

    @Autowired
    private UserCameraMapper userCameraMapper;

    @Autowired
    private BuKongGroupMapper buKongGroupMapper;

    @Autowired
    private ClusterDataPushTask clusterDataPushTask;
    @Value("${dataPush.sign}")
    private Integer dataPush;
    @Value("${image.prefix.uploadDir}")
    private String lcoalDir;
    @Value("${ftp.sign}")
    private int ftpSign;

    @Value("${ftp.ip}")
    private String ip;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.pathname}")
    private String pathname;

    private FTPUtils ftpUtils = new FTPUtils();
//    /**
//     * 处理数据,并添加到消息队列.
//     *
//     * @param data server发送的报警消息
//     */
//    public void messageHandler(String data) {
//        // 反序列化对应的数据
//        SocketRequestEntity<RealTimeAlarm> requestEntity = JSON.parseObject(data,
//                new TypeReference<SocketRequestEntity<RealTimeAlarm>>() {
//                });
//        RealTimeAlarm realTimeAlarm = requestEntity.getReqInfo();
////        System.err.println(JSON.toJSONString(realTimeAlarm));
//
//        // 查询具体的报警信息.
//        CameraInfo cameraInfo = cameraInfoMapper.findById(realTimeAlarm.getCameraId());
//
//        // 根据报警内容获取报警id,然后根据报警id查询对应的具体信息.
//        if (realTimeAlarm.getAlarmId().intValue() > 0) {
//            realTimeAlarm = realTimeAlarmService.getAlarmById(realTimeAlarm.getAlarmId().intValue());
//        } else {// 如果没有记录,则将查询到的摄像头名称赋值
//            realTimeAlarm.setCameraName(cameraInfo.getName());
//            //设置经纬度,供前台使用
//            if(cameraInfo.getLatitude()!=null && cameraInfo.getLongitude()!=null) {
//                realTimeAlarm.setLongitude(cameraInfo.getLongitude());
//                realTimeAlarm.setLatitude(cameraInfo.getLatitude());
//            }
//        }
//        // 向中间件推送报警信息
//        if (dataPush == 0) {  //判断是否打开推送 0推送   1不推送
//            clusterDataPushTask.postAlarmMessage(realTimeAlarm.getAlarmImagePath(), realTimeAlarm.getBkId().toString(), cameraInfo.getIp());
//        }
//        // 判断那些在线用户有权限获取对应的报警信息.
//        RealTimeAlarm finalRealTimeAlarm = realTimeAlarm;
//        WebSocketSessionUtils.getSessionMap().forEach((String key, Object value) -> {
//            User user = (User) value;
//            List<String> list = userCameraMapper.getCameraNumByUserId(user.getUserId());
//            for (String id : list) {
//                if (cameraInfo.getNumber().startsWith(id, 1)) {
//                    // 广播地址最终为 "/topic/alarm",表示将报警信息广播
//                    WebSocketMessage message = new WebSocketMessage(String.valueOf(user.getUserId()), "/alarm", finalRealTimeAlarm);
//                    Consumer.add(message);
//                    break;
//                }
//            }
//        });
//    }

    /**
     * 获取报警消息,并通过webservice推送到前端
     * @param mqttMessage
     */
    @Override
    public void handle(MqttMessage mqttMessage) {
        log.debug("handled alarm message : " + new String(mqttMessage.getPayload()));
    }

    @Override
    public void handle(String msg) {
        //log.info("handled alarm message -: " + msg);
        JSONObject alarm = (JSONObject)JSON.parse(msg.substring(msg.indexOf(":")+1));
        BigInteger alarmId = new BigInteger((String) alarm.get("alarmid"));
        // 由于推送过来的报警id没有实时写入表中,所以等3秒以后再查询数据库,然后推送到页面
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RealTimeAlarm realTimeAlarmByAlarmId = realTimeAlarmService.getRealTimeAlarmByAlarmId(alarmId);
        // 处理图片信息，使用 ftp推送
        if (ftpSign == 0) {
            String imagPath = lcoalDir + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File file = new File(imagPath);
            if (!file.exists()){
                file.mkdirs();
            }
            // 图片保存保存路径名
            String imageName = imagPath + "/" + UUID.randomUUID().toString().replaceAll("-","") + ".jpg";
            try {
                // 获取人员类型
                String controltypeName = buKongGroupMapper.getControltypeName(realTimeAlarmByAlarmId.getControltypeid());
                // 图片信息整合
                operatingPhoto(realTimeAlarmByAlarmId.getBkImagePath(),realTimeAlarmByAlarmId.getAlarmImagePath(),imageName,
                        realTimeAlarmByAlarmId.getSimilar(),realTimeAlarmByAlarmId.getAlarmTime(),realTimeAlarmByAlarmId.getCameraName(),controltypeName);
                // 调用ftp推送图片 imageName
                ftpUtils.loginFTP(ip, port, username, password);
                ftpUtils.uploadFile(pathname, imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        WebSocketSessionUtils.getSessionMap().forEach((String key, Object value) -> {
            User user = (User) value;
            // 广播地址最终为 "/topic/alarm",表示将报警信息广播
            WebSocketMessage message = new WebSocketMessage(user.getUserId(), "/alarm", realTimeAlarmByAlarmId);
            Consumer.add(message);
        });

    }


    /**
     * 合并两张图片 并填充文字
     * @throws Exception
     */
    public static void operatingPhoto(String imgPath1, String imgPath2, String destPath, String similar,String createTime,String name,String resource) throws Exception{
        BufferedImage bg ;
        BufferedImage bg2 ;
        if (imgPath1.startsWith("http")) {
            bg = ImageIO.read(new URL(imgPath1)); // 网路路径
        } else {
            bg = ImageIO.read(new File(imgPath1)); // 本地路径
        }
        if (imgPath2.startsWith("http")) {
            bg2 = ImageIO.read(new URL(imgPath2)); // 网路路径
        } else {
            bg2 = ImageIO.read(new File(imgPath2));
        }
        int w1 = bg.getWidth();
        int h1 = bg.getHeight();
        int w2 = bg2.getWidth();
        int h2 = bg2.getHeight();
        while (h1 > 250){
            h1 = h1 / 2;
            w1 = w1 /2;
        }
        while (h2 > 250){
            h2 = h2 / 2;
            w2 = w2 /2;
        }
        // 图片等比例做处理
        int i =  250 * w1 / h1;
        int j = 250 * w2 / h2;

        BufferedImage img = new BufferedImage(i + j + 30, 400, BufferedImage.TYPE_INT_RGB);//创建图片
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, i + j + 30, 400);//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g.drawImage(bg.getScaledInstance(i,250, Image.SCALE_DEFAULT), 10, 10, null); // 绘制缩小后的图
        g.drawImage(bg2.getScaledInstance(j,250, Image.SCALE_DEFAULT), 20 + i, 10, null); // 绘制缩小后的图
        // 处理字体
        g.setColor(Color.BLACK);
        int height = 280;
        g.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        if (StringUtils.isNotBlank(similar)) {
            g.drawString("比值：" + similar, 10, height);//绘制文字
            height += 20;
        }
        if (StringUtils.isNotBlank(resource)) {
            g.drawString("人员类型：" + resource , 10, height);
            height += 20;
        }
        if (StringUtils.isNotBlank(createTime)) {
            g.drawString("时间：" + createTime, 10, height);
            height += 20;
        }
        if (StringUtils.isNotBlank(name)) {
            g.drawString("位置：" + name, 10, height);
        }
        g.dispose();
        // 图片写出
        ImageIO.write(img, "jpg", new File(destPath));
    }
}
