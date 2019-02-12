package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 根据wc修改数据库,来修改实体类  int-->String
 * 0814  y
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuKong {
    private Integer bkId;

    private Integer featureId;

    private String bkGroupId;

    private String yrydId;

    private String userId;

    private String roleId;
    
    private String name;

    private String reason;

    private String user;

    private String contact;//联系方式
    
    private String comments;//备注

    private Boolean local;

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String imagePath;

    private String idcard;

    private Integer type;

    private int enabled;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date onTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date offTime;

    private String cancelUser;

    private String cancelReason;
    
    private String cancelContact;
    
    private String imageData;
    
    private String imageId;

    private String context;

    /*************** 新增一个amount,表示报警的次数 ******************/
    private Integer amount;

    private Integer sampleStatus;
}