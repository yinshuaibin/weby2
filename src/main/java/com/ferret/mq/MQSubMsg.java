package com.ferret.mq;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSubMsg{
    private static int qos = 0;
    @Autowired
    private MQConfig mqConfig;
    private MqttClient mqttClient;

    /**
     * 连接mq
     * @param clientId
     * @return
     */
    public boolean connect(String clientId) {
        return this.connect(clientId,null);
    }

    public boolean connect(String clientId, MqttCallback mqttCallback) {
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        String broker = "tcp://" + mqConfig.getIp() + ":" + mqConfig.getPort();
        connOpts.setCleanSession(true);
//        connOpts.setUserName(mqConfig.getUsername());
//        connOpts.setPassword(mqConfig.getPassword().toCharArray());
//        connOpts.setConnectionTimeout(mqConfig.getConnectTimeOut());
//        connOpts.setKeepAliveInterval(mqConfig.getKeepAlive());
        try {
            mqttClient = new MqttClient(broker, clientId, dataStore);
            if(mqttCallback != null) {
                mqttClient.setCallback(mqttCallback);
            }
            mqttClient.connect(connOpts);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 订阅 多主题
     * @param topic
     * @param qos
     * @throws MqttException
     */
    public void sub(String[] topic, int[] qos, IMqttMessageListener[] mqttMessageListeners) throws MqttException{
        if(mqttClient != null){
            mqttClient.subscribe(topic, qos, mqttMessageListeners);
        }
    }
    public void sub(String[] topic, int[] qos) throws MqttException{
        if(mqttClient != null){
            mqttClient.subscribe(topic, qos);
        }
    }

    /**
     * 订阅 单个主题
     * @param topic
     * @param qos
     * @throws MqttException
     */
    public void sub(String topic, int qos, IMqttMessageListener mqttMessageListener) throws MqttException {
        if(mqttClient != null){
            mqttClient.subscribe(topic, qos, mqttMessageListener);
        }
    }

    /**
     * 断开连接
     * @throws MqttException
     */
    public void disConnet() throws MqttException {
        this.mqttClient.disconnect();
    }

    /**
     * 设置回调
     * @param mqttCallback
     */
    public void setCallback(MqttCallback mqttCallback) {
        this.mqttClient.setCallback(mqttCallback);
    }
}
