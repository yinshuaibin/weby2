package com.ferret.bean.staticBean;

import lombok.Data;

@Data
public class PersonInfo {
	private String ID;	//id
	private String name;	//姓名
	private String sex;	//姓别
	private String imagepath; //图片的相对路径
	private String birthday; //出生日期
	private Float threshold; //对比值
	private String record;	//人物备注信息
	private	String idcard;//身份证号
	
}
