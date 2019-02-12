package com.ferret.bean.staticBean;

public class PersonData {//省厅常口库返回数据格式List<PersonData>

	private String ID;
	private String name;
	private String sex;
	private String birthdate;
	private String idcard;
	private String imagepath;
	private Float threshold;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public Float getThreshold() {
		return threshold;
	}
	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}
	public PersonData(String iD, String name, String sex, String birthdate, String idcard, String imagepath,
			Float threshold) {
		super();
		ID = iD;
		this.name = name;
		this.sex = sex;
		this.birthdate = birthdate;
		this.idcard = idcard;
		this.imagepath = imagepath;
		this.threshold = threshold;
	}
	public PersonData() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PersonData [ID=" + ID + ", name=" + name + ", sex=" + sex + ", birthdate=" + birthdate + ", idcard="
				+ idcard + ", imagepath=" + imagepath + ", threshold=" + threshold + "]";
	}
	
	
}
