package com.ferret.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.Data;

import java.util.Date;
@Data
public class ClusterAdvanceDTO {

    private String personId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private int count;
    private String number;
    // 地点
    private String cameraName;
    // 代表图
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String imagePath;
}
