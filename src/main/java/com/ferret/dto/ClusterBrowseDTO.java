package com.ferret.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ClusterBrowseDTO {

    private String personId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private int count;
    // 地点
    private String cameraName;
    //经纬度
    private double longitude;
    private double latitude;
    // 相似度
    private double filimt;
    // 代表图
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String imagePath;

    // 查询地区Id
    private String number;

    // 身份证号
    private String idcard;

    // 姓名
    private String name;


}
