package com.ferret.bean.staticBean;

import java.util.List;

public class SeachStatic {

	private String img1; // 图片1
	private float threshold; // 阈值起始区间
    private String sex; //性别
    private int solt;// 数据条数
    private int smallAge; // 查询最小年龄
    private int bigAge; // 查询最大年龄
    private int count; // 返回张数
    private List<String> prots;//查询库id
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getSolt() {
		return solt;
	}
	public void setSolt(int solt) {
		this.solt = solt;
	}
	public int getSmallAge() {
		return smallAge;
	}
	public void setSmallAge(int smallAge) {
		this.smallAge = smallAge;
	}
	public int getBigAge() {
		return bigAge;
	}
	public void setBigAge(int bigAge) {
		this.bigAge = bigAge;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getProts() {
		return prots;
	}
	public void setProts(List<String> prots) {
		this.prots = prots;
	}
	public SeachStatic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SeachStatic(String img1, float threshold, String sex, int solt, int smallAge, int bigAge,
			int count, List<String> prots) {
		super();
		this.img1 = img1;
		this.threshold = threshold;
		this.sex = sex;
		this.solt = solt;
		this.smallAge = smallAge;
		this.bigAge = bigAge;
		this.count = count;
		this.prots = prots;
	}
	@Override
	public String toString() {
		return "SeachStatic [img1=" + img1 + ", threshold=" + threshold + ", sex=" + sex + ", solt="
				+ solt + ", smallAge=" + smallAge + ", bigAge=" + bigAge + ", count=" + count + ", prots=" + prots
				+ "]";
	}
    
    
}
