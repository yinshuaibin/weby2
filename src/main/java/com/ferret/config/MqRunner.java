package com.ferret.config;


import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.RealTimeAlarm;
import com.ferret.mq.MQSubMsg;
import com.ferret.service.alarm.AlarmService;
import com.ferret.socket.bean.WebSocketMessage;
import com.ferret.socket.service.impl.RealTimeAlarmSocketServiceImpl;
import com.ferret.socket.utils.Consumer;
import com.ferret.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;

@Slf4j
@Component
public class MqRunner implements ApplicationRunner{
    @Autowired
    private MQSubMsg mqSubMsg;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private RealTimeAlarmSocketServiceImpl alarmSocketService;
    private static final String ALARM_TOPIC = "Alarm/realtime/#";
    @Override
    public void run(ApplicationArguments applicationArguments){
        log.info("开始配置mqtt");
        // 增加报警主题handler
        //mqCallBack.addHandler(ALARM_TOPIC, realTimeAlarmSocketService);
        // 连接mtqq服务器
        if(mqSubMsg.connect(UUID.randomUUID().toString())){

            try {
                // 订阅报警主题
                mqSubMsg.sub(ALARM_TOPIC, 0, new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                        // 接收到报警信息
                        String msg = new String(mqttMessage.getPayload());
                        log.info(s + ":" +msg);
                        //alarmSocketService.handle(s + ":" +msg);
                        Thread.sleep(1000);
                        JSONObject jsonObject = JSON.parseObject(msg);
                        RealTimeAlarm realTimeAlarmByAlarmId = alarmService.getRealTimeAlarmByAlarmId(jsonObject.getBigInteger("alarmid"));
                        WebSocketServer.sendInfo(JSON.toJSONString(realTimeAlarmByAlarmId), null);
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
            log.info("配置mqtt完成");
        } else {
            log.error("mqtt连接失败");
        }
    }
}
