package com.ferret.socket.config;

import com.ferret.socket.handler.RealTimeWebSocketHandlerDecoratorFactory;
import com.ferret.socket.interceptor.ConnectedChannelInterceptor;
import com.ferret.socket.interceptor.WebSocketInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

/**
 * 配置websocket服务类,用于连接前端页面上开启的websocket服务;
 * 通过注册端点,实现连接
 * 通过发布消息广播,实现客户端订阅.
 * @author cc
 * @since 2017-12-17
 *
 */
@Slf4j
@Configuration
//@EnableWebMvc
@EnableWebSocketMessageBroker
// 通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,
// 此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
public class WebSocketServerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketProperties webSocketProperties;

    @Autowired
    private WebSocketInterceptor webSocketInterceptor;

    @Autowired
    private ConnectedChannelInterceptor connectedChannelInterceptor;

    @Autowired
    private RealTimeWebSocketHandlerDecoratorFactory realTimeWebSocketHandlerDecoratorFactory;

    /**
     * 端点的作用——客户端在订阅或发布消息到目的地址前，要连接该端点。
     * <p>
     * 将url路径注册为STOMP端点，这个路径与发送和接收消息的目的路径有所不同，
     * 这是一个端点，客户端在订阅或发布消息到目的地址前，要连接该端点,即用户发送请求url="/stomp"与STOMP server进行连接。
     * 之后再转发到订阅路径;
     * </p>
     *
     * @param stompEndpointRegistry stomp 服务端点注册实例
     */
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //在网页上可以通过"/stomp"来和服务器的WebSocket连接
        stompEndpointRegistry
                .addEndpoint(webSocketProperties.getUrl())
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置了一个简单的消息代理，如果不重载，默认情况下回自动配置一个简单的内存消息代理，用来处理以"/topic"为前缀的消息。
     * 这里重载configureMessageBroker()方法,消息代理将会处理前缀为下面"/topic"的消息。
     * @param registry 频道广播
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 应用程序以/app为前缀，代理目的地以/topic、/user为前缀
        registry.enableSimpleBroker(webSocketProperties.getDestinationPrefix());
        // 指定用户前缀
        registry.setUserDestinationPrefix("/user");

        // 接收客户端前缀
        // registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(realTimeWebSocketHandlerDecoratorFactory);
        super.configureWebSocketTransport(registration);
    }






    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(connectedChannelInterceptor);
        super.configureClientInboundChannel(registration);
    }





    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        super.configureClientOutboundChannel(registration);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return super.configureMessageConverters(messageConverters);
    }
}