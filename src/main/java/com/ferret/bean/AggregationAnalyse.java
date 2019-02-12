package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/**
 * 聚集点分析实体类
 * @author zwc
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggregationAnalyse {

    //返回结果集封装
    private String cameraIp;
    private int personNum;
    private int countNum;
    private String cameraName;
}
