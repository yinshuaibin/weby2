package com.ferret.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;

import lombok.Data;

@Data
public class AllopatryPersonAnalyse {

	//聚类id
	private String personId;
	//A相机 ip
	private String cameraIp;
	//A 图片路径
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath;
	//A开始时间
	private String startTime;
	//A结束时间
	private String endTime;
	//A相机名称
	private String cameraNameA;
	
	//下面是  B 信息
	private String cameraIpB;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePathB;
	private String startTimeB;
	private String endTimeB;
	private String cameraNameB;
	
	//姓名
	private String name;
	//身份证号
	private String idcard;
}
