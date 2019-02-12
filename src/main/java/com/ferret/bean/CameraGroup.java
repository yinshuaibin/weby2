package com.ferret.bean;

import lombok.Data;

@Data
public class CameraGroup {
    /** 相机ID（自增）*/
    private Integer id;
    /** 相机分组名 (4114240001)*/
    private String name;
    /** 相机描述*/
    private String description;
    /** 相机父节点*/

    private String placeId;
       /** 摄像头编码,省市县分组组成 */
    private String number;
    /** 获取相机分组下的相机数量 */
    private int count;
}