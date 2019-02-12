package com.ferret.bean;

import java.util.Date;

/**
 * 大数据统计，记录每天，每周，每月的新进人数
 * @author lee
 * @since 2018-03-12
 */


public class Bigdata {

	Date c_starttime;
	Date c_endtime;
	
	String startTime;
	String endTime;
	String countName;
	Integer count;
	public Date getC_starttime() {
		return c_starttime;
	}
	public void setC_starttime(Date c_starttime) {
		this.c_starttime = c_starttime;
	}
	public Date getC_endtime() {
		return c_endtime;
	}
	public void setC_endtime(Date c_endtime) {
		this.c_endtime = c_endtime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCountName() {
		return countName;
	}
	public void setCountName(String countName) {
		this.countName = countName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		if (count==null) {
			this.count = 0;
		}else {
		this.count = count;
		}
	}
	

}
