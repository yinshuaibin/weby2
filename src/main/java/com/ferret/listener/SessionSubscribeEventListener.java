package com.ferret.listener;

import com.ferret.dao.SqliteDao;
import com.ferret.service.bukong.impl.BuKongServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * 对websocket注册事件进行监听
 *
 * @author cc;
 * @version 1.0
 * @since 2018/3/19;
 */
@Slf4j
@Component
public class SessionSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private BuKongServiceImpl buKongService;

    @Autowired
    private SqliteDao sqliteDao;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        // 对不同的事件响应进行处理
    }

}
