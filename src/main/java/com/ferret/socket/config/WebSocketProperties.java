package com.ferret.socket.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author cc;
 * @since 2017/12/21;
 * @version 1.0
 *
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "socket.web")
public class WebSocketProperties {
    /**
     * websocket端点连接的url地址
     */
    private String url;
    /**
     * 客户端监听的地址前缀
     */
    private String[] destinationPrefix;
}
