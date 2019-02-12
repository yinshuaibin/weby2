package com.ferret.bean.InterfaceBean;


import lombok.Data;

/**
 * 人脸提取结果返回值中人脸信息实体类
 */
@Data
public class FaceFeature {

    /**
     * Feature数组，图片人脸位置信息结构的base64
     */
    private String FacePos;

    /**
     * Feature数组，图片人脸特征的base64
     */
    private String Feature;

    /**
     * 人脸位置坐标,顶部
     */
    private int FaceTop;

    /**
     * 人脸位置坐标底部
     */
    private int FaceBottom;

    /**
     * 人脸位置坐标, 左
     */
    private int FaceLeft;

    /**
     * 人脸位置坐标, 右
     */
    private int FaceRight;
}
