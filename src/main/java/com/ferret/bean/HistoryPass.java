package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 聚类实体类
 * 修改人:hyl
 * 修改日期:0115
 * 修改原因:历史查询时不再使用存储过程
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPass {


    private Long id;

    private String clusterid;

    private Long featureid;

    private String cameraid;

    private String cameraip;

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String picpath;

    private Date pictime;

    private String featurefilename;

    private String featuresubscript;

    private String projectid;

    private String batchid;

    private String isrepeat;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String cameraname;
}