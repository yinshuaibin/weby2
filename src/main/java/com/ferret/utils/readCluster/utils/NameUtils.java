package com.ferret.utils.readCluster.utils;

import java.io.File;

import com.ferret.utils.readCluster.tools.CalendTools;

public class NameUtils {
	public static String getCameraInfo(String imageName,int infoIndex) {
		String[] ss=imageName.split("[_]");
		for(int i=0;i<ss.length;i++) {
			
		}
		switch(infoIndex) {
		case 0:
			return ss[0];
		case 2:
			return CalendTools.string2String(ss[2], "yyyyMMddHHmmssSSS", "yyyy-MM-dd HH:mm:ss.SSS");
			default :
				return ss[0];
		}
	}
	
	public static String addZero(String head,String foot,int length) {
		int addLength=length-head.length()-foot.length();
		StringBuffer  sb=new StringBuffer(head);
		for(int i=0;i<addLength;i++) {
			sb.append('0');
		}
		sb.append(foot);
		return sb.toString();
	}
}

