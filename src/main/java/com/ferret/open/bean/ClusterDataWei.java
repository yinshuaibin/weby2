package com.ferret.open.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.Data;

@Data
public class ClusterDataWei {
    /**
     * 聚类维族人id,和文件夹名一致
     */
    private String personId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 推送时间
     */
    private String sendTime;

    /**
     * 证件id
     */
    private String idcard;

    /**
     * 姓名
     */
    private String name;
    /**
     * 当前id下的图片总数
     */
    private Integer count;

    /**
     * 代表图1
     */
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String representImg1;

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String representImg2;

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String representImg3;

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String representImg4;

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String representImg5;
}
