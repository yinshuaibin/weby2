package com.ferret.utils;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接收到的base64字符或者 http路径的url替换成本地路径。
 * @author cheng;
 * @since 2017/12/16;
 */
@Component
public class UrlToPathSerialize {

    @Autowired
    private ImagePrefixProperties imagePrefixProperties;
    
    private UrlToPathSerialize urlToPathSerialize;

    @PostConstruct
    public void init(){
    	urlToPathSerialize = this;
		//this.imagePrefixProperties = this.imagePrefixProperties;
    }
  //判断是http路径还是 base64编码 
  	public String generateImageByHttp(String imgStr, String imgPath) {
  		String bukongUrl = urlToPathSerialize.imagePrefixProperties.getBukongUrl();
  		String clusterUrl = urlToPathSerialize.imagePrefixProperties.getClusterUrl();
  		String historyUrl = urlToPathSerialize.imagePrefixProperties.getHistoryUrl();
  		String uploadUrl = urlToPathSerialize.imagePrefixProperties.getUploadUrl();
  		String alarmUrl = urlToPathSerialize.imagePrefixProperties.getAlarmUrl();
  		if (imgStr.length()>2000) {
  			if (imgStr.contains(",")) {
				String[] split = imgStr.split(",");
				imgStr = split[1];
			}
  			//base64编码(保存图片)
  			ImageBase64Utils.generateImage(imgStr, imgPath);
  		}else {
  			//String new_imgStr = imgStr.replaceAll("\\", "/");
  			//http路径   转换成本地路径
  			if (StringUtils.isNoneBlank(bukongUrl) && imgStr.contains(bukongUrl)) {
  				imgPath = imgStr.replace(bukongUrl, urlToPathSerialize.imagePrefixProperties.getBukongDir());
  			}else if (StringUtils.isNoneBlank(clusterUrl) && imgStr.contains(clusterUrl)) {
  				imgPath = imgStr.replace(clusterUrl, urlToPathSerialize.imagePrefixProperties.getClusterDir());
  			}else if (StringUtils.isNoneBlank(historyUrl) && imgStr.contains(historyUrl)) {
  				imgPath = imgStr.replace(historyUrl, urlToPathSerialize.imagePrefixProperties.getHistoryDir2());
  			}else if (StringUtils.isNoneBlank(uploadUrl) && imgStr.contains(uploadUrl)) {
  				imgPath = imgStr.replace(uploadUrl, urlToPathSerialize.imagePrefixProperties.getUploadDir());
  			}else if(StringUtils.isNoneBlank(alarmUrl) && imgStr.contains(alarmUrl)) {
  				imgPath = imgStr.replace(alarmUrl, urlToPathSerialize.imagePrefixProperties.getAlarmDir());
  			}
  		}
  		return imgPath;
  	}
}
