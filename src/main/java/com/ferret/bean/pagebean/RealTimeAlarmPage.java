package com.ferret.bean.pagebean;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ferret.bean.RealTimeAlarm;

public class RealTimeAlarmPage {
	private String[] cameraNumbers;
	private Integer dataCount;
	private Integer pageNo;
	private Integer pageSize;
	private  String beginTime;
    private String endTime;
    private String groupId;
    private String reason;
    private Integer bkId;
    private List<RealTimeAlarm> listData;
	public String[] getCameraNumbers() {
		return cameraNumbers;
	}
	public void setCameraNumbers(String[] cameraNumbers) {
		this.cameraNumbers = cameraNumbers;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<RealTimeAlarm> getListData() {
		return listData;
	}
	public void setListData(List<RealTimeAlarm> listData) {
		this.listData = listData;
	}
	public Integer getDataCount() {
		return dataCount;
	}
	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getBkId() {
		return bkId;
	}

	public RealTimeAlarmPage setBkId(Integer bkId) {
		this.bkId = bkId;
		return this;
	}
}
