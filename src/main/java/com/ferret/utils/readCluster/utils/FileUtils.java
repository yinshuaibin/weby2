package com.ferret.utils.readCluster.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**   
 * CSV操作(导出和导入)
 *
 * @author qin
 * @version 1.0   
 */
public class FileUtils {
    
    /**
     * 导出
     * 
     * @param file 文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportFile(File file,List<String> dataList){
        boolean isSucess=false;
        System.out.println("文件生成开始");
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out,"utf-8");
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\n");
                }
            }
            isSucess=true;
        } catch (IOException e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        System.out.println("文件生成完毕");
        return isSucess;
    }
    /**
     * 导出
     * 
     * @param file 文件(路径+文件名)，csv文件不存在会自动创建
     * @param data 数据
     * @return
     */
    
    public static boolean exportFile(File file,String data){
    	 boolean isSucess=false;
         System.out.println("文件生成开始");
         FileOutputStream out=null;
         OutputStreamWriter osw=null;
         BufferedWriter bw=null;
         try {
             out = new FileOutputStream(file);
             osw = new OutputStreamWriter(out,"utf-8");
             bw =new BufferedWriter(osw);
             if(data!=null && !data.isEmpty()){
                     bw.append(data).append("\n");
             }
             isSucess=true;
         } catch (IOException e) {
             isSucess=false;
         }finally{
             if(bw!=null){
                 try {
                     bw.close();
                     bw=null;
                 } catch (IOException e) {
                     e.printStackTrace();
                 } 
             }
             if(osw!=null){
                 try {
                     osw.close();
                     osw=null;
                 } catch (IOException e) {
                     e.printStackTrace();
                 } 
             }
             if(out!=null){
                 try {
                     out.close();
                     out=null;
                 } catch (IOException e) {
                     e.printStackTrace();
                 } 
             }
         }
         System.out.println("文件生成完毕");
         return isSucess;
    }
    /**
     * 导入
     * 
     * @param file 文件(路径+文件)
     * @return
     */
    public static ArrayList<String> importFile(String file){
        ArrayList<String> dataList=new ArrayList<String>();
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String line = ""; 
            while ((line = br.readLine()) != null) { 
                dataList.add(line);
            }
        }catch (IOException e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }
    public static String getFileContent(String uri){
    	 BufferedReader br=null;
    	 StringBuffer fileString=new StringBuffer();
         try { 
             br = new BufferedReader(new InputStreamReader(new FileInputStream(uri),"utf-8"));
             String line = ""; 
             while ((line = br.readLine()) != null) { 
                 fileString.append(line);
             }
         }catch (IOException e) {
         }finally{
             if(br!=null){
                 try {
                     br.close();
                     br=null;
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
         return fileString.toString();
    }
    public static Map getProperties(String uri){
    	Map<String,String> map=new HashMap<String,String>();
    	List<String> ls=FileUtils.importFile(uri);
    	String[] param;
    	System.out.println("行数："+ls.size());
    	for(String s:ls){
    		//System.out.println(s);
    		if(s.trim().length()>0&&!s.substring(0,1).equals("#")){
    		  param=s.split("==");
    		 // System.out.println(param.length);
    		  if(param.length>1)
    			  map.put(param[0].trim(),param[1].trim());
    		  else
    			  map.put(param[0].trim(),null);
    		  //System.out.println(param[0].trim()+"       "+param[1].trim());
    		}
    	}
    	return map;
    }
    public static boolean setPorperties(File f,Map map){
    	Iterator itor=map.keySet().iterator();
    	List<String> ls =new ArrayList<String>();
    	String key,value;
    	while(itor.hasNext()){
    		key=(String)itor.next();
    		value=map.get(key).toString();
    		ls.add(key+"=="+value);
    	}
    	return exportFile(f,ls);
    }
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public  static boolean deleteAllDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null && children.length > 0) {
                for (int i=0; i<children.length; i++) {
                    boolean success = deleteAllDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    /**
     * 删除指定目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public  static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null && children.length > 0) {
                for (int i=0; i<children.length; i++) {
                    (new File(dir,children[i])).delete();
                }
            }
        }
    }
}