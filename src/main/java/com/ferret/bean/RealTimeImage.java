package com.ferret.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;

import lombok.Data;

/**
 * 实时图片实体类
 * @author cc;
 * @since 2018/5/4;
 */
@Data
public class RealTimeImage {
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath;
	private int ID;
}

