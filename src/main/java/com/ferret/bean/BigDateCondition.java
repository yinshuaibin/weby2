package com.ferret.bean;

import lombok.Data;

import java.util.List;
@Data
public class BigDateCondition {
	private List<String> cameraNumber;//要查询的相机number
	private String onTime;//查询开始时间时间
	private String offTime;//查询结束时间
	private List<CameraInfo> cameraInfos;
	

}
