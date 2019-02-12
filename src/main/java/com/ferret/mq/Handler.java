package com.ferret.mq;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 消息处理
 */
public interface Handler {
    public void handle(MqttMessage mqttMessage);
    public void handle(String msg);
}
