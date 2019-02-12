package com.ferret.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;

import lombok.Data;

@Data
public class TongXingBack {

	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath;// 用来展示第一个图片
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath2;// 用来展示第二个图片

	private String dizhi;// 根据相机ip查到的地址

	private String p1d;// 用来接收第一个图片的时间
	private String p2d;// 用来接收第二个图片的时间

	@Override
	public String toString() {
		return "TongXingBack [imagePath=" + imagePath + ", imagePath2=" + imagePath2 + ", dizhi=" + dizhi + ", p1d="
				+ p1d + ", p2d=" + p2d + "]";
	}

}
