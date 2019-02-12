package com.ferret.dao.provider;

import org.apache.ibatis.annotations.Param;

public class CityMapperProvider {

	public String findByCode(@Param("code") Integer code) {
		StringBuffer sb=new StringBuffer("SELECT * FROM tb_city WHERE code REGEXP");
		sb.append("'^"+code+"'");
		return sb.toString();
	}
}
