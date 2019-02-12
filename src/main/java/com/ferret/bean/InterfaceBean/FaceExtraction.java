package com.ferret.bean.InterfaceBean;

import lombok.Data;

import java.util.List;

/**
 * 人脸提取返回结果实体类  y 1224
 */
@Data
public class FaceExtraction {
    /**
     * 请求的功能类型字符串
     */
    private String method;
    /**
     * 结果编码，0-成功，其它-失败
     */
    private String resultcode;
    /**
     * 错误信息编码
     */
    private String status;
    /**
     * 特征提取的特征版本标识
     */
    private String Feature_Ver;
    /**
     * 特征提取的特征版本标识
     */
    private String Feature_Len;
    /**
     * 从图片中提取的人脸数量，最大为30
     */
    private String Feature_Num;
    /**
     * 特征提取返回列表
     */
    private List<FaceFeature> Feature_List;


}
