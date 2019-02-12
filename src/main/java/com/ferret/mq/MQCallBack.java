package com.ferret.mq;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * mq回调
 */
@Slf4j
@Component
public class MQCallBack implements MqttCallback {
    Map<String, Handler> handlerMap = new HashMap<>();
    public void addHandler(String topic, Handler handler) {
        if(!handlerMap.containsKey(topic)) {
            handlerMap.put(topic, handler);
        }
    }
    public void removeHandler(String topic) {
        if(!handlerMap.containsKey(topic)) {
            handlerMap.remove(topic);
        }
    }
    @Override
    public void connectionLost(Throwable throwable) {
        // 连接断开
        log.debug("mq connect lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        // 消息到达
        log.debug(topic + ":" + mqttMessage.toString());
        Handler handler = handlerMap.get(topic);
        if(handler!=null) {
            handler.handle(mqttMessage);
        }
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // 发送完成
        log.debug("deliveryComplete");
    }
}
