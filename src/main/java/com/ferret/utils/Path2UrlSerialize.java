package com.ferret.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ferret.utils.ImagePrefixProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 将图片数据库中的dir地址替换为url地址
 * 
 * @author cheng;
 * @since 2017/12/16;
 */

public class Path2UrlSerialize extends JsonSerializer<String> {

	@Autowired
	private ImagePrefixProperties imagePrefixProperties;

	/**
	 * 替换地址,将图片地址或文件地址替换成url地址.
	 *
	 * @param value
	 *            Value to serialize; can <b>not</b> be null.
	 * @param gen
	 *            Generator used to output resulting Json content
	 * @param serializers
	 *            Provider that can be used to get serializers for
	 */
	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		value = value.replace("\\", "/");
		// String uploadDir = imagePrefixProperties.getUploadDir();
		String historyDir = imagePrefixProperties.getHistoryDir();
		String clusterDir = imagePrefixProperties.getClusterDir();
		String bukongDir = imagePrefixProperties.getBukongDir();
		String staticDir = imagePrefixProperties.getStaticDir();
		if (StringUtils.isNotBlank(bukongDir) && value.contains(bukongDir)) {
			// 替换布控地址url
			value = value.replace(imagePrefixProperties.getBukongDir(), imagePrefixProperties.getBukongUrl());
		} else if (StringUtils.isNotBlank(historyDir) && value.contains(historyDir)) {
			// 如果图片地址包含"BACK"则说明是背景图,这个设置是因为数据库中背景图和历史图片地址完全一致,只有这个字段不一致,所以用来判断
			//替换背景图片
			if (value.contains("BACK")) {
				value=value.replace(imagePrefixProperties.getHistoryDir(), imagePrefixProperties.getBackGroundUrl());
			} else {
				// 替换历史记录图片
				value = value.replace(imagePrefixProperties.getHistoryDir(), imagePrefixProperties.getHistoryUrl());
			}
		} else if (StringUtils.isNotBlank(clusterDir) && value.contains(clusterDir)) {
			// 替换聚类图片url
			value = value.replace(imagePrefixProperties.getClusterDir(), imagePrefixProperties.getClusterUrl());
		} else if (StringUtils.isNotBlank(staticDir) && value.contains(staticDir)){
			// 替换静态图片url
			value = value.replace(imagePrefixProperties.getStaticDir(), imagePrefixProperties.getStaticUrl());
		} else {
			// 替换上传图片url
			value = value.replace(imagePrefixProperties.getUploadDir(), imagePrefixProperties.getUploadUrl());
		}
		gen.writeString(value);
	}
}
