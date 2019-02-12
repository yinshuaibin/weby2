package com.ferret.bean;

import lombok.Data;

@Data
public class PersonNumByCameraIp {

	/**
	 *  cameraName: "",
          count: "",
          countTime: "",
          countToday:""
	 */
	private String cameraIp;
	private String cameraName;
	private int count;
	private int countTime;
	private int countToday;
	
	private int yesterdayPerNum;
	
}
