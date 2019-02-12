package com.ferret.bean.photoReceive;

import lombok.Data;

@Data
public class PhotoReceiveResult {

    /**
     * 主键id
     */
    private transient int id;

    /**
     * 业务id
     */
    private String taskid;

    /**
     * 聚类id
     */
    private String nPersonId;

    /**
     * 需要发送的地址
     */
    private transient String sendUrl;
}
