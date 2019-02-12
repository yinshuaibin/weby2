package com.ferret.socket.handler;

import com.ferret.utils.ConstantsUtils;
import com.ferret.utils.WebSocketSessionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;


/**
 * @author cc;
 * @since 2018/3/22;
 * @version 1.0
 */
public class RealTimeWebSocketHandler extends WebSocketHandlerDecorator {


    public RealTimeWebSocketHandler(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketSessionUtils.add(session.getId(),session.getAttributes().get(ConstantsUtils.SESSION_USER));
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session,message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session,exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        WebSocketSessionUtils.remove(session.getId());
        super.afterConnectionClosed(session,closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
