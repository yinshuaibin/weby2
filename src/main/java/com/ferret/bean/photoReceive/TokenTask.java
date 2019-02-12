package com.ferret.bean.photoReceive;


import lombok.Data;

@Data
public class TokenTask {
    private int id;

    /**
     * 接口令牌, 每个发送过来的请求都对应一个令牌, 如果不对应, 则不处理该请求
     */
    private String token;

    /**
     * 接收到请求后,返回给对方的业务id
     */
    private String taskId;

    /**
     * 传递过来的图片的相机ip
     */
    private String cameraIp;

    /**
     * 图片名称
     */
    private String imagePath;

    /**
     * 该条请求是否已经完成聚类 1:已完成 0:未完成
     */
    private int flag;
}
