package com.ferret.bean.InterfaceBean;

import lombok.Data;

/**
 * 批量新增人员,编辑人员库数据(自增id,业务id),人员库批量删除请求
 */
@Data
public class FaceImageResult {
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
