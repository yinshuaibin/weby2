package com.ferret.bean.InterfaceBean;

import lombok.Data;

import java.util.List;

/**
 * @Descriptions 动态检索 返回结果
 * @Author xieyingchao 2018/12/26
 */
@Data
public class DynamicFaceResult {

    /** 请求的功能类型字符串 */
    private String method;

    /** 结果编码，0-成功，其它-失败 */
    private String resultcode;

    /** 错误信息编码 */
    private String status;

    /** 特征ID */
    private String FeatureId;

    /** 返回查询图片个数 */
    private String Trails_Pic_Num;

    /** 返回图片查询数组 */
    private List<Dynamic> Trails_Pic_LIST;
}
