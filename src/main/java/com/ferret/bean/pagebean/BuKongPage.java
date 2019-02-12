package com.ferret.bean.pagebean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BuKongPage {
	private String[] bkGroupName;
	private String bkGroupId;
	private String user;
	private String reason;
	private String contact;//联系方式
	private String comments;//备注
	private String name;
	private String idcard;
	private String[] files;
	private String onTime;//布控有效时间
	private String offTime;//布控失效时间
	private int enabled;//布控是否有效
	private String keyword;//查询关键字
	private int pageNum;//分页数据
	
	public String getBkGroupId() {
		return bkGroupId;
	}
	public void setBkGroupId(String bkGroupId) {
		this.bkGroupId = bkGroupId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public String[] getBkGroupName() {
		return bkGroupName;
	}
	public void setBkGroupName(String[] bkGroupName) {
		this.bkGroupName = bkGroupName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String[] getFiles() {
		return files;
	}
	public void setFiles(String[] files) {
		this.files = files;
	}
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getOffTime() {
		return offTime;
	}
	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "BuKongPage [bkGroupName=" + Arrays.toString(bkGroupName) + ", bkGroupId=" + bkGroupId + ", user=" + user
				+ ", reason=" + reason + ", contact=" + contact + ", comments=" + comments + ", name=" + name
				+ ", idcard=" + idcard + ", files=" + Arrays.toString(files) + ", onTime=" + onTime + ", offTime="
				+ offTime + ", enabled=" + enabled + ", keyword=" + keyword + ", pageNum=" + pageNum
				+ ", getBkGroupId()=" + getBkGroupId() + ", getUser()=" + getUser() + ", getPageNum()=" + getPageNum()
				+ ", getBkGroupName()=" + Arrays.toString(getBkGroupName()) + ", getReason()=" + getReason()
				+ ", getContact()=" + getContact() + ", getComments()=" + getComments() + ", getFiles()="
				+ Arrays.toString(getFiles()) + ", getOnTime()=" + getOnTime() + ", getOffTime()=" + getOffTime()
				+ ", getEnabled()=" + getEnabled() + ", getKeyword()=" + getKeyword() + ", getIdcard()=" + getIdcard()
				+ ", getName()=" + getName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
