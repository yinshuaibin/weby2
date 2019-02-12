package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator;
 * @since 2018/1/15;
 * 修改原因:前台增加了一个勾选摄像头功能,改了前台传递过来的摄像头数据,原来的不再使用  y 0811
 */
@Data
public class DynamicQueryEntity {
    // 图片特征提取key
    private String img1;
    private String img2;
    // 起始时间
    private String startTime;
    // 结束时间
    private String endTime;

    private Integer count;
    // 阈值
    private Float threshold;

    // 用户勾选的摄像头数据,原有的摄像头数据不再使用
    private List<CameraInfo> cameraList;

    // 图片base64数据
    private String imageData;

}
