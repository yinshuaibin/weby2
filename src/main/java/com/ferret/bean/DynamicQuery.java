package com.ferret.bean;

import lombok.Data;

import java.util.List;

/**
 * @Descriptions 动态检索接收参数用的实体类
 * @author xieyingchao 2018/12/26
 */
@Data
public class DynamicQuery {

    /** 接收图片数据(base64) */
    private String imageData;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 相机列表 */
    private List<String> cameraIds;

    /** 阈值 */
    private Float threshold;

    /** 返回张数 */
    private int count;

}
