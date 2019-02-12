package com.ferret.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;


public class ClusterSearch {

	private String img1; //base64字符
	private String img2; //base64字符
	private Date start_Time;
	private Date endT_ime;
	private float threshold;
	
	private String personId;
	//查询时 格式为Date 的create_time
	private Date create_time;
	//返回页面的createTime
	private String createTime;
	private Integer count;
	
	private float filimt;
	private String createtime;
	private Date  updatetime;
	private String idcard;
	private String name;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String represent_img2;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String represent_img3;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String represent_img4;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String represent_img5;

	private Integer pageNum;
	private Integer pageSize;
	private String cameraName;
	private String cameraIp;
	//经纬度
	private double longitude;
	private double latitude;
	
	
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public Date getStart_Time() {
		return start_Time;
	}
	public void setStart_Time(Date start_Time) {
		this.start_Time = start_Time;
	}
	public Date getEndT_ime() {
		return endT_ime;
	}
	public void setEndT_ime(Date endT_ime) {
		this.endT_ime = endT_ime;
	}
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold / 10;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public float getFilimt() {
		return filimt;
	}
	public void setFilimt(float filimt) {
		this.filimt = filimt;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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
	public String getRepresent_img2() {
		return represent_img2;
	}
	public void setRepresent_img2(String represent_img2) {
		this.represent_img2 = represent_img2;
	}
	public String getRepresent_img3() {
		return represent_img3;
	}
	public void setRepresent_img3(String represent_img3) {
		this.represent_img3 = represent_img3;
	}
	public String getRepresent_img4() {
		return represent_img4;
	}
	public void setRepresent_img4(String represent_img4) {
		this.represent_img4 = represent_img4;
	}
	public String getRepresent_img5() {
		return represent_img5;
	}
	public void setRepresent_img5(String represent_img5) {
		this.represent_img5 = represent_img5;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
	public String getCameraIp() {
		return cameraIp;
	}
	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
}
