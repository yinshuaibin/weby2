package com.ferret.dao.provider;

import java.util.ArrayList;
import java.util.Map;

public class RoleCameraMapperProvider {
	
	public String insertList(Map map){
		String roleId = (String) map.get("param1");
		ArrayList<String> cameraId = (ArrayList) map.get("param2");
		
		StringBuilder sql = new StringBuilder("INSERT INTO tb_role_camera (role_id, camera_number) VALUES ");
		
		for(String str : cameraId){
			sql.append(" ('" + roleId + "','" + str + "'), " );
		}
		
		sql.deleteCharAt(sql.lastIndexOf(","));
		
		System.err.println(sql);
		
		return sql.toString();
	}
}
