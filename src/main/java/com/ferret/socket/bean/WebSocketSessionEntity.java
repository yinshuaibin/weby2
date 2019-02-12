package com.ferret.socket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;

/**
 * @author cc
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WebSocketSessionEntity {
    /**
     * 对应连接的唯一id
     */
    private String sessionId;

    /**
     * 连接的uri地址
     */
    private URI uri;
    /**
     * 保存的参数和值
     */
    private Map<String,Object> attributes;

    /**
     * 请求头信息
     */
    private HttpHeaders httpHeaders;

    /**
     * 接收请求的服务器地址
      */
    private InetSocketAddress localAddress;
    /**
     * 发送请求远程客户端的地址
     */
    private InetSocketAddress remoteAddress;

}
