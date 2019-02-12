package com.ferret.bean.InterfaceBean;


import lombok.Data;

/**
 * 1:1接口返回值实体类  y 1224
 */
@Data
public class CompareOneToOne {
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
     * 相似度
     */
    private String score;
}
