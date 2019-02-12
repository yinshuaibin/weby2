package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聚类维族人类
 *
 * @author cc
 * @version v1.0
 * @since 2018-04-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterWei {
    /**
     * 聚类维族人id,和文件夹名一致
     */
    private String personId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

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