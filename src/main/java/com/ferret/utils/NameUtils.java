package com.ferret.utils;

import java.util.HashMap;
import java.util.Map;

public class NameUtils {

	private static Integer NameNum=0;
	private static Map<String, Integer> IpMap = new HashMap<>();
	private static Integer FiveNum = 0;
	/**
	 * 生成 1-99999  格式的字符串
	 * @param IP
	 * @return
	 */
	public static Integer getNameNum(String IP) {
		// 是否为空,为空直接添加进map
		if (IpMap.isEmpty()) {
			IpMap.put(IP, NameNum);
		}
		// 是否有这个key
		if (IpMap.containsKey(IP)) {
			// 如果有,获取到对应的NameNum
			NameNum = IpMap.get(IP);
			// 判断
			NameNum = NameNum < 9999 ? NameNum + 1 : 1;
			IpMap.put(IP, NameNum);
			return NameNum;
		} else {
			IpMap.put(IP, 1);
			return 1;
		}
	}
	
	/**
	 * 获得5位 0 - 99999的字符串
	 * @param IP
	 * @return
	 */
	public static String getFiveNum() {
		FiveNum += 1;
		if (FiveNum > 99999) {
			FiveNum = 1;
		}
		return String.format("%05d",FiveNum);
	};
	
	public static String getNameNumFive(String IP) {
		return String.format("%05d", getNameNum(IP));
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
	public static void main(String[] args) {
		for (int i = 0; i < 12000; i++) {
			String fiveNum2 = NameUtils.getFiveNum();
			System.out.println(fiveNum2);
		}
	}
}
