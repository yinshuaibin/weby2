package com.ferret.bean.staticBean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;

import lombok.Data;

@Data
public class FaceReturnData {
	private String name;
	private String id;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imageOldPath;
	private Float threshold;
	private String property;
}
