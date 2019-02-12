package com.ferret.socket.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

/**
 * @author cc;
 * @date 2018/3/22;
 */
@Component
public class RealTimeWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        // 此处自定义websockethandler,完成对事件的处理
        return new RealTimeWebSocketHandler(handler);
    }
}
