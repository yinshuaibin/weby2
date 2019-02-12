package com.ferret.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.bean.CameraInfo;
import com.ferret.utils.Path2UrlSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cc;
 * @since 2018/1/18;
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPassDTO {
    /**
     * 特征id;
     */
    private Integer featureId;
    /**
     * 通行时间
     */
    private String imageDateTime;
    /**
     * 图片地址
     */
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String imagepath;

    /**
     * 摄像头名称
     */
    private String cameraName;

    private CameraInfo cameraInfo;

}
