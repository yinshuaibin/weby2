package com.ferret.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 图片人像信息
 * @author cc;
 * @since 2018/4/16;
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFaceInfo {
    /**
     * 倾斜角度,从-90到+90,左为负,右为正
     */
    private Integer pitch;
    /**
     * 翻转角度,从-90到+90,左为负,右为正
     */
    private Integer roll;
    /**
     * 偏航角,角度范围同上
     */
    private Integer yaw;
    /**
     * 特征id值
     */
    private Integer featureId;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 图片中的人脸个数
     */
    private Integer faceNumber;
    /**
     * 对应的特征数据的index
     */
    private Integer featurePosition;
    /**
     * pitch, roll, yaw 三个角度的置信度,
     * 取值区间 [ 0-100 ]
     */
    private Integer confidence;
    /**
     * 左眼位置
     */
    private String eyeL;
    /**
     * 右眼位置
     */
    private String eyeR;
    /**
     * 嘴巴位置
     */
    private String mouth;
    /**
     * 鼻子位置
     */
    private String nose;
    /**
     * 人脸质量, 取值区间[0-100]
     */
    private Integer quality;
    /**
     * 人脸范围,长方形,在图片中的坐标
     */
    private String rect;


}
