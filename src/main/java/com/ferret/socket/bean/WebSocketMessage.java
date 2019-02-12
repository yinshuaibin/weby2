package com.ferret.socket.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auth cc
 * @since 2017-12-27
 * @desc 自定义的WebSocket消息封装类
 * 服务器向浏览器发送的此类消息。
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    /**
     * 订阅的用户id,如果为空则表示是广播,否则推送给个人.
     */
    private String userId;
    /**
     * 发送的广播地址
     * 开放广播, 地址一般为 /topic/* 的地址, 其中 * 可以为任意名称,表示客户端订阅的终点
     * 个人广播: 地址一般为 /user/{userId}/* 的地址, 其中 userId表示用户的id值,*表示任意名称.
     */
    private String destination;

    /**
     * 服务端发送的数据
     */
    private Object data;


    public WebSocketMessage(String destination, Object data) {
        this.destination = destination;
        this.data = data;
    }
}
