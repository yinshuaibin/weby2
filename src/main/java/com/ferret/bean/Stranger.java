package com.ferret.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;

import lombok.Data;

@Data
public class Stranger {
	private String personId;
	private String createTime;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath;
	private String cameraName;
	private int count; //每个人在选定摄像头组下，指定时间段，不同时间段内出现的次数;
	private int count1;//在指定摄像头,执行时间段内出现次数
	private int count2;//在指定摄像头,指定时间段的开始时间前7天或者前14天或者前一个月的在此摄像头下出现次数
	//经纬度
	private double longitude;
	private double latitude;

	public void setCreateTime(String createTime) {
		if (createTime.contains(".")) {
			createTime = createTime.substring(0,createTime.indexOf("."));
		}
		this.createTime = createTime;
	}
}
