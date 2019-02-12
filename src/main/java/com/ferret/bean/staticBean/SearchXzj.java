package com.ferret.bean.staticBean;

import java.util.Arrays;

public class SearchXzj {
	private String ImageBase64;//图片base64字节
	private Float threshold;//阈值
	private Integer resultSize;//默认15
	private String sex;//性别
	private int minage;//最小年龄
	private int maxage;//最大年龄
	private String[] port;//查询库ID
	public SearchXzj() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getImageBase64() {
		return ImageBase64;
	}
	public void setImageBase64(String imageBase64) {
		ImageBase64 = imageBase64;
	}
	public Float getThreshold() {
		return threshold;
	}
	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}
	public Integer getResultSize() {
		return resultSize;
	}
	public void setResultSize(Integer resultSize) {
		this.resultSize = resultSize;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getMinage() {
		return minage;
	}
	public void setMinage(int minage) {
		this.minage = minage;
	}
	public int getMaxage() {
		return maxage;
	}
	public void setMaxage(int maxage) {
		this.maxage = maxage;
	}
	public String[] getPort() {
		return port;
	}
	public void setPort(String[] port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "SearchXzj [ImageBase64=" + ImageBase64 + ", threshold=" + threshold + ", resultSize=" + resultSize
				+ ", sex=" + sex + ", minage=" + minage + ", maxage=" + maxage + ", port=" + Arrays.toString(port)
				+ "]";
	}
	
}
