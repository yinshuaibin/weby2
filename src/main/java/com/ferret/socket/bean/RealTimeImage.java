package com.ferret.socket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cc;
 * @since 2018/3/15;
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealTimeImage {
    /**
     * 摄像头id
     */
    private Integer cameraId;

    /**
     * 特征id
     */
    private Long featureId;

    /**
     * 图片时间
     */
    private String time;

    /**
     * 图片url地址
     */
    private String imageUrl;
    /**
     * 图片特征数据
     */
    private String featureData;
    /**
     * 人脸区域
     */
    private String rect;

    private Integer roll;
    private Integer yaw;
    private Integer pitch;
}
