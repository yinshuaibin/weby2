package com.ferret.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 镇平临时显示维族陌生人创建的bean
 * @author cc;
 * @since 2018/4/11;
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterTestImage {

    /**
     * 根据图片名称分析出的摄像头ip
     */
    private String ip;
    /**
     * 根据图片名称分析出的抓拍时间
     */
    private String time;

    /**
     * 保存图片的上层目录,即文件编号,根据图片的绝对地址得来
     */
    private String fileNumber;

    /**
     * 图片绝对地址
     */
    @JsonSerialize(using = Path2UrlSerialize.class)
    private String imagePath;

}

