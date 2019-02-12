package com.ferret.bean.InterfaceBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.bean.CameraInfo;
import com.ferret.utils.Path2UrlSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Descriptions 动态检索查询出的图片结果
 */
@Data
public class Dynamic {


    /** 图片文件名称 */
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String PicFileName;

    /** 比对分值 */
    private Float score;

    /** 特征Id */
    private String FeatureId;

    /** --------根剧featureId查询抓拍图片相机信息---------- */

    /** 抓拍时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date snapTime;
    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private CameraInfo cameraInfo;
}
