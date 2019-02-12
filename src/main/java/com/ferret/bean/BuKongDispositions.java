package com.ferret.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BuKongDispositions {

	@JsonProperty("Reason")
	private String  reason; //布 控 原 因 
	@JsonProperty("EndTime")
	private String  endTime;
	@JsonProperty("OperateType")
	private int  operateType;
	@JsonProperty("DispositionID")
	private String  dispositionID;
	@JsonProperty("BeginTime")
	private String  beginTime;
	@JsonProperty("Title")
	private String  title; //布 控 标 题
	@JsonProperty("TargetFeature")
	private String  targetFeature; //1=白起
	@JsonProperty("ApplicantName")
	private String  applicantName; //申请人姓名
	@JsonProperty("ApplicantOrg")
	private String  applicantOrg; //申请人单位
	@JsonProperty("TargetImageURI")
	private String  targetImageURI; //http://192.168.1.1:80/image/1.jpg
	@JsonProperty("DispositionArea")
	private String  dispositionArea; //640100;64
	@JsonProperty("PriorityLevel")
	private int  priorityLevel; //2  等级
	@JsonProperty("ApplicantInfo")
	private String  applicantInfo; //申请人联系方式
	@JsonProperty("CreatTime")
	private String  creatTime;
	@JsonProperty("TollgateList")
	private String  tollgateList; //"64010000001210000001;64010000001210000002"
	
	@JsonProperty("ReceiveAddr")
	private String  receiveAddr; //"http://192.168.1.1/rest/notify", 
	@JsonProperty("ReceiveMobile")
	private String  receiveMobile; //"13401234567;13487654321",
	@JsonProperty("SubImageList")
	private SubImageInfo  subImageList;
}
