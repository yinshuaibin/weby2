package com.ferret.utils.readCluster.tools;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendTools {
	public static String formtDefault="yyyyMMddHHmmss";
	public  static String getTimeString(){
		SimpleDateFormat sd=new SimpleDateFormat(formtDefault);
		return sd.format(Calendar.getInstance().getTime());
	}
	public  static String getTimeString(String format){
		if (format ==null||format.equals("")) format =formtDefault;
		SimpleDateFormat sd=new SimpleDateFormat(format);
		return sd.format(Calendar.getInstance().getTime());
	}
	//开始时间  ,index 时间索引:1年，2月，3日，4时，5分;increament 时间增量，为负数时为减时间，toformat 输出时间格式
	public static String add(Date startTime,int index,int increment,String toFormat){
		Calendar c=Calendar.getInstance();
		c.setTime(startTime);
		switch(index){
		case 1:
			c.add(Calendar.YEAR, increment);
			break;
		case 2:
			c.add(Calendar.MONTH, increment);
			break;
		case 3:
			c.add(Calendar.DATE, increment);
			break;
		case 4:
			c.add(Calendar.HOUR, increment);
			break;
		case 5:
			c.add(Calendar.MINUTE, increment);
			break;
		case 6:
			c.add(Calendar.SECOND, increment);
			break;
		default:
			c.add(Calendar.SECOND, 0);
		}
		String endTime=CalendTools.date2str(c.getTime(), toFormat);
		return endTime;
	}

	//指定格式的字符串，转换为另外格式的字符串 ,只适合本项目
		//yyyyMMdd HHmmss   yyyy-MM-dd HH:mm:ss "yyyy-MM-dd HH:mm:ss.SSS"
		public static String string2String(String date, String dateFormat, String toFormat){
			String d=null;
			try {
				if(!StringUtils.contains(date, "null")){
					SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
					SimpleDateFormat toSdf=new SimpleDateFormat(toFormat);
					d=toSdf.format(sdf.parse(date));
				}	
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
		}
		public static Date add(Date startTime,int index,int increment){
			Calendar c=Calendar.getInstance();
			c.setTime(startTime);
			switch(index){
			case 1:
				c.add(Calendar.YEAR, increment);
				break;
			case 2:
				c.add(Calendar.MONTH, increment);
				break;
			case 3:
				c.add(Calendar.DATE, increment);
				break;
			case 4:
				c.add(Calendar.HOUR, increment);
				break;
			case 5:
				c.add(Calendar.MINUTE, increment);
				break;
			default:
				c.add(Calendar.SECOND, increment);
			}
			return c.getTime();
		}
		public static String date2str(Date date,String fmt){
			if(date==null){
				return null;
			}
			SimpleDateFormat sdf=new SimpleDateFormat(fmt);
			return sdf.format(date);
		}
}
