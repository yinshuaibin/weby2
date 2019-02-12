package com.ferret.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileOperation {
	
	
  /**
   * @description 读文件
   * @throws IOException 
   */
    public static List<String> readTxtFile(String fileName) throws IOException{
        BufferedReader br = null;
        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8" );
        br = new BufferedReader(isr);
        List<String> list = new ArrayList<>();

        String read = "";
        while((read = br.readLine()) != null) {
            list.add(read);
        }
        if(br != null){
            br.close();
        }
        return list;
    }
  
  /**
   * @description 写文件
   * @throws UnsupportedEncodingException 
   * @throws IOException
   */
    public static boolean writeTxtFile(String content,File fileName) throws UnsupportedEncodingException, IOException{
        FileOutputStream o = null;
        try{
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null != o){
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
  
  /**
   * 读取GBK格式数据
   */
  
    public static List<String> readGBKTxtFile(String fileName) throws IOException{
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)),"GBK"));
        String lineTxt = "";
        while ((lineTxt = br.readLine()) != null) {
          list.add(lineTxt);
          System.out.println("向新的camerainfo表中插入数据:      "+lineTxt);
        }
        if(br != null){
          br.close();
        }
        return list;
    }
}