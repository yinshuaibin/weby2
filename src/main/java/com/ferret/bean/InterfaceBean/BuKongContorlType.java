package com.ferret.bean.InterfaceBean;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 布控分组实体类   y1224
 */
@Data
public class BuKongContorlType {
    private String id;
    private String typename;
    private Integer iscontrol;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String remark;
}
