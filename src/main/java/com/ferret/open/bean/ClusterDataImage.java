package com.ferret.open.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.Data;

@Data
public class ClusterDataImage {

    private String id;

    private String personId;

    private String cameraIp;

    private String imageTime; // 图片拍摄时间

    private String sendTime; // 推送时间

    private String createTime; // 维族人_时间作为判断推送位置（已经推送到哪地方了）

    @JsonSerialize(using = Path2UrlSerialize.class)
    private String imagePath;
}
