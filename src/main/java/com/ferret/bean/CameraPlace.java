package com.ferret.bean;

import lombok.Data;

/**
 * 相机场所实体类
 * y 0125
 */
@Data
public class CameraPlace {

    /**
     * 自增id
     */
    private int id;

    /**
     * 相机场所number,对应相机表中的场所字段
     */
    private String number;

    /**
     * 相机场所名称
     */
    private String name;

    /**
     * 父节点id(未使用)
     */
    private String pId;

    /**
     * 是否启用,启用:1 否0
     */
    private int enabled;
}
