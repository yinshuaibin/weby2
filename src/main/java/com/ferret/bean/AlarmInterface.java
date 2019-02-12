package com.ferret.bean;

import lombok.Data;

/**
 * @Descriptions 向其他中间件提供报警数据
 * @author xyc
 * @data 2018/11/08
 */
@Data
public class AlarmInterface {

    private String name; // 姓名
    private String idcard; // 身份证号
    private String imageData; // base64数据
    private String ip; // 相机IP
    private String type; // 人员类型

    public AlarmInterface() {
    }

    public AlarmInterface(String name, String idcard, String imageData, String ip, String type) {
        this.name = name;
        this.idcard = idcard;
        this.imageData = imageData;
        this.ip = ip;
        this.type = type;
    }
}
