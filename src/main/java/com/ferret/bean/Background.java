package com.ferret.bean;

import lombok.Data;

import java.util.Date;

/**
 * 背景图表，暂时无用，目前用旧表
 * @since 2018-01-01
 * huyunlong/**
 * 	  修改人:y
 * 	  修改时间:0911
 * 	 修改原因:自行从配置文件中拿到路径替换背景图的路径,不再走统一路径替换
 *
 */

@Data
public class Background {
    private Integer ID;

    private Integer workflag;

    private Date localtime_date;
    
    private Date localtime_time;

    //@JsonSerialize(using = Path2UrlSerialize.class)
    private String bakimagepath;

}