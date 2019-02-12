package com.ferret.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cc;
 * @since 2018/1/19;
 * 新加背景图片映射 走IIS映射,类历史查询图片地址也走IIS
 * 修改人:y
 * 修改时间:0718
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "image.prefix")
public class ImagePrefixProperties {

    private String uploadDir;

    private String uploadUrl;

    private String historyDir;

    private String historyUrl;
    
    /**
     * 背景图片映射   y
     * 0718
     */
    private String backGroundUrl;

    private String bukongDir;

    private String bukongUrl;

    private String clusterDir;

    private String clusterUrl;
    
    private String historyDir2;

    private String alarmUrl;
    
    private String alarmDir;
    
    /**
     * 静态图片映射
     */
    
    private String staticDir;
    
    private String staticUrl;

    /**
     * 新中间件接口请求前缀
     *
     */
    private String interfaceUrl;

    /**
     * 新中间件布控图片前缀  y 1225
     */
    private String bukongImageUrl;

    /**
     * 新中间件抓拍图片前缀
     */
    private String historyImageUrl;

    /**
     * 新中间件图片实际存储路径
     */
    private String historyImageDir;

    /**
     * 新中间件报警图片前缀
     */
    private String alarmImageUrl;

    /**
     * 图片抓拍后的存放路径(未处理)
     */
    private String makeCluserDir;
}
