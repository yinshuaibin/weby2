package com.ferret.utils.staticUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReadTXTFileUtils {
	
	private static final String URL="jdbc:mysql://127.0.0.1:3306/db_checkface?useUnicode=true&useSSL=false&amp;characterEncoding=utf-8";  
    private static final String URL2="jdbc:mysql://127.0.0.1:3306/db_newfacest?useUnicode=true&useSSL=false&amp;characterEncoding=utf-8";  
    private static final String USER="root";  
    private static final String PASSWORD="123456"; 

	public static void read(List<String> readFile,String sql) throws Exception {
		System.err.println("开始存入数据！！！！！！");
		String sql2 = sql;
		for (String string : readFile) {
			String[] split = string.split("\",\"");
				for (int i = 0; i < split.length; i++) {
					if(i == 0) {
						split[i] = split[i].substring(1);
					}else if(i == 22) {
						split[i] = split[i].substring(0,split[i].length()-1);
					}else {
						
					}
					
					sql2 = sql2 + "'" + split[i] + "',";
				}
			sql2 = sql2.substring(0,(sql2.length()-1)) + ")";
			System.err.println(sql2);
			insert(sql2);
			System.err.println("成功存入一条数据！！！！！！");
			sql2 = sql;
		}
		System.err.println("数据保存完毕！！！！！！");
	}
	
	public static void insert(String sql) throws Exception {
    	System.err.println("开始插入路径----------------------------------");
    	//创建链接并使用循环遍历集合中数据并插入数据库
        Class.forName("com.mysql.jdbc.Driver");  
        int i = 1;
        Connection con2=DriverManager.getConnection(URL2, USER, PASSWORD);

        Statement statement2=con2.createStatement();
        if(null != statement2) {
            statement2.executeUpdate(sql);
        }
        System.out.println("添加完成！！！！！！");
        try {
        	//关闭连接
            if (statement2 != null) 
            	statement2.close();
            if (con2 != null) 
                con2.close();
        } catch (SQLException e)  {
            e.printStackTrace(); 
        }
    }
	
	public static List<String> readFile(String str) {
		List<String> lineData = new ArrayList<>();
        BufferedReader br = null;
        try {
	            File dir = new File(str);//指定路径  
	            String charset=get_charset(dir);
	            if (charset == "GBK") {  
	                InputStreamReader reader = new InputStreamReader(  
	                        new FileInputStream(new File(str)), "gb2312");  
	                br = new BufferedReader(reader);
	            }  
	            if (charset == "UTF-8") {  
	                br = new BufferedReader(new InputStreamReader(  
	                        new FileInputStream(str), "UTF-8"));  
	            }  
	            String s;  
	            while((s = br.readLine()) != null) 
	            	lineData.add(s);
	        } catch(Exception e) {  
	            e.printStackTrace();  
	        }  finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return lineData;
	}
	
	public static String get_charset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];//首先3个字节
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(
                    new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                // int len = 0;
                int loc = 0;

                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return charset;
    }
}
