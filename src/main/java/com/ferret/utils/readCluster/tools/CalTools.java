package com.ferret.utils.readCluster.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CalTools {
	//ָ����ʽ���ַ�����ת��Ϊ�����ʽ���ַ��� ,ֻ�ʺϱ���Ŀ
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
	//������ݿ��ַ����ڳ��̲�һ����
	public static Date strToDate(String date,String toFormat){
		SimpleDateFormat tsdf=new SimpleDateFormat(toFormat);
		Date dt=null;
		if(date.length()==12)
			date=date+"00";
		if(!StringUtils.contains(date, "null")){
			try {
				//System.out.println(date);
				dt=tsdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}
	//��ǰʱ���N��
	public static Date addYear(int years){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int currentYear=calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR,currentYear-years );
		Date dt=new Date(calendar.getTimeInMillis());
		return dt;
	}
	//��ǰʱ���n��
	public static Date addMonths(int months){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int currentYear=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH,currentYear-months );
		Date dt=new Date(calendar.getTimeInMillis());
		return dt;
	}
	//��ʼʱ��  ,index ʱ������:1�꣬2�£�3�գ�4ʱ��5��;increament ʱ��������Ϊ����ʱΪ��ʱ�䣬toformat ���ʱ���ʽ
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
		default:
			c.add(Calendar.SECOND, increment);
		}
		String endTime=CalTools.date2str(c.getTime(), toFormat);
		return endTime;
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
	public static Date addYears(Date date,int increment){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int currentYear=calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR,currentYear+increment );
		Date dt=new Date(calendar.getTimeInMillis());
		return dt;
	}
	public static Date addMonths(Date date,int months){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int currentYear=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH,currentYear-months );
		Date dt=new Date(calendar.getTimeInMillis());
		return dt;
	}
	public static Calendar getDateToCalendar(Date d){
		if(d==null)return null;
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}
	/**
	 * ��ȡ��ǰ��
	 * */
	public static Date getCalendarToDate(Calendar c){
		if(c==null)return null;		
		Date d=c.getTime();
		return d;
	}
	
	public static Calendar calAddTime(Calendar c,int t,int i){
		if(c==null)return null;
		c.add(t,i);
		return c;
	}
	
	public static Calendar getStrToCalendar(String s,String format){
		if(s==null||format==null)return null;
		int flen=format.length();
		int slen=format.length();
		if(slen>flen)s=s.substring(0,flen);
		if(slen<flen)format=format.substring(0,slen);
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cal=Calendar.getInstance();
		try {			
			cal.setTime(df.parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return cal;
	}
	
	public static String getCalendarToStr(Calendar c,String format){
		if(c==null||format==null)return null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(c.getTime());
	}
	
	public static Timestamp str2Timestamp(String str,String fmt){
		Date date=str2Date(str,fmt);				
		return new Timestamp(date.getTime());
	}
	
	public static Date str2Date(String str,String fmt){
		if(fmt==null||"".equals(fmt))return null;
		SimpleDateFormat sdf=new SimpleDateFormat(fmt);
		Date date=null;
		try{
			date=sdf.parse(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	public static String timestamp2str(Timestamp time,String fmt){
		Date date=null;
		if(time==null){
			return null;
		}
		date=new Date(time.getTime());
		
		return date2str(date,fmt);
	}
	
	public static String date2str(Date date,String fmt){
		if(date==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(fmt);
		return sdf.format(date);
	}
	
	public static String date2str(long time,String fmt) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		return df.format(cd.getTime());
	}
}
