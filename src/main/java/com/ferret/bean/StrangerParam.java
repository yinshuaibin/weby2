package com.ferret.bean;

import lombok.Data;

import java.util.List;

/**
 * 实时陌生人规则参数
 * y 0829
 */
@Data
public class StrangerParam {
    private Integer pageSize;//返回的记录数
    private List<CameraInfo> cameraList;//选择需要关注推送的相机
    private Integer enabled;//是否推送右上角消息
}
