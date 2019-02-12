package com.ferret.bean.bukong;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.bean.RealTimeAlarm;
import com.ferret.utils.Path2UrlSerialize;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewBuKong {
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

   //@JsonSerialize(using = Path2UrlSerialize.class)
    private List<String> imagePath;

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
    
    /**
     * 用来接受背景图,布控表中并没有此字段   y
     */
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String backGroundImg;
    
    /**
     * 布控分组名称
     */
    private String bkGroupName;
    
    /**
     * 用来接收这个布控id对应的一分钟内报警的信息
     */
    private List<RealTimeAlarm> realTimeAlarms;
}