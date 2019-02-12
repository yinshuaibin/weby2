package com.ferret.bean.bukong;

import com.ferret.bean.BuKong;
@Deprecated
public class BuKongRequest {
	private String request;
	private BuKong reqInfo;
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public BuKong getReqInfo() {
		return reqInfo;
	}
	public void setReqInfo(BuKong reqInfo) {
		this.reqInfo = reqInfo;
	}
}
