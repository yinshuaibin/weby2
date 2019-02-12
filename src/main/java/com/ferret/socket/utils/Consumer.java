package com.ferret.socket.utils;

import com.ferret.socket.bean.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 在项目启动时运行,启动线程监听消息队列,只要有消息则广播出去
 *
 * @author cc;
 * @since 2017/12/20;
 */

@Slf4j
@Component
@Profile("!test")
@Order(4)
public class Consumer implements CommandLineRunner{

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final static BlockingQueue<WebSocketMessage> queue = new LinkedBlockingQueue<>();

    /**
     * 加入消息队列中
     *
     * @param webSocketMessage
     */
    public static void add(WebSocketMessage webSocketMessage) {
        try {
            queue.put(webSocketMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 推送消息
     *
     * @param message
     */
    public void broker(WebSocketMessage message) {
        // 判断 userId 不为空则是订阅, 否则广播
        if (StringUtils.isEmpty(message.getUserId())) {
            simpMessagingTemplate.convertAndSend(message.getDestination(), message.getData());
        } else {
            simpMessagingTemplate.convertAndSendToUser(message.getUserId(), message.getDestination(), message.getData());
        }
    }


    @Override
    public void run(String... args) {
        log.info("------------------------ 开启websocket数据读取 --------------------------------------------------");
        new Thread(()->{
            while (true) {
                try {
                    WebSocketMessage webSocketMessage = queue.take();
                    if (webSocketMessage != null) {
                        broker(webSocketMessage);
                        log.debug(webSocketMessage.toString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}

