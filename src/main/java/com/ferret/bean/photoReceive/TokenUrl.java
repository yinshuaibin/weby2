package com.ferret.bean.photoReceive;

import lombok.Data;

@Data
public class TokenUrl {
    private int id;

    /**
     * 接口令牌, 每个发送过来的请求都对应一个令牌, 如果不对应, 则不处理该请求
     */
    private String token;

    /**
     * 不同的token对应不同的发送地址
     */
    private String sendUrl;

}
