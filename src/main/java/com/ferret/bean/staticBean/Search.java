package com.ferret.bean.staticBean;

public class Search {
	private String ImageBase64;//图片base64字节
	private Float threshold;//阈值
	private Integer resultSize;//默认15
	private String sex;//性别
	private int minage;//最小年龄
	private int maxage;//最大年龄
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
	public Search(String imageBase64, Float threshold, Integer resultSize, String sex, int minage, int maxage) {
		super();
		ImageBase64 = imageBase64;
		this.threshold = threshold;
		this.resultSize = resultSize;
		this.sex = sex;
		this.minage = minage;
		this.maxage = maxage;
	}
	public Search() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Search [ImageBase64=" + ImageBase64 + ", threshold=" + threshold + ", resultSize=" + resultSize
				+ ", sex=" + sex + ", minage=" + minage + ", maxage=" + maxage + "]";
	}
	
	
}
