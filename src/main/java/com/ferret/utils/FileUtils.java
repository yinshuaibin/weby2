package com.ferret.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

public class FileUtils {
	/**
	 * @return 文件的存储路径及
	 * */
	public String handleFileUpload(HttpServletRequest request) {
	    List<MultipartFile> files = ((MultipartHttpServletRequest) request)
	        .getFiles("file");
	    MultipartFile file = null;
	    BufferedOutputStream stream = null;
	    for (int i = 0; i < files.size(); ++i) {
	      file = files.get(i);
	      if (!file.isEmpty()) {
	        try {
	          byte[] bytes = file.getBytes();
	          stream = new BufferedOutputStream(new FileOutputStream(
	              new File(file.getOriginalFilename())));
	          stream.write(bytes);
	          stream.close();
	 
	        } catch (Exception e) {
	          stream = null;
	          return "You failed to upload " + i + " => "
	              + e.getMessage();
	        }
	      } else {
	        return "You failed to upload " + i
	            + " because the file was empty.";
	      }
	    }
	    return "upload successful";
	  }
	
	public static void uploadFile(MultipartFile file, String imgPath,String imgName) throws Exception {  
        File targetFile = new File(imgPath);  
        if (!targetFile.exists()) {
			boolean mkdirs = targetFile.mkdirs();
		}
        FileOutputStream out = null;
        try {
			out = new FileOutputStream(imgPath +"/"+ imgName);
			out.write(file.getBytes());
			out.flush();
		} catch (IOException e){
        	e.printStackTrace();
		}finally {
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }  
	
	//图片转化成base64字符串 
	  public static String getImageStr(String filePath)
	  {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理 
	    String imgFile = "d://test.jpg";//待处理的图片 
	    InputStream in = null; 
	    byte[] data = null; 
	    //读取图片字节数组 
	    try 
	    { 
	      in = new FileInputStream(imgFile);     
	      data = new byte[in.available()]; 
	      in.read(data); 
	      in.close(); 
	    }  
	    catch (IOException e)  
	    { 
	      e.printStackTrace(); 
	    } finally {
	    	if(null != in){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    //对字节数组Base64编码 
	    BASE64Encoder encoder = new BASE64Encoder(); 
	    return encoder.encode(data);//返回Base64编码过的字节数组字符串 
	  } 
	  //base64字符串转化成图片 
	  public static boolean generateImage(String imgStr)
	  {  //对字节数组字符串进行Base64解码并生成图片 
	    if (imgStr == null) //图像数据为空 
	      return false; 
	    BASE64Decoder decoder = new BASE64Decoder();
	    OutputStream out= null;
	    try 
	    { 
	      //Base64解码 
	      byte[] b = decoder.decodeBuffer(imgStr); 
	      for(int i=0;i<b.length;++i) 
	      { 
	        if(b[i]<0) 
	        {//调整异常数据 
	          b[i]+=256; 
	        } 
	      } 
	      //生成jpeg图片 
	      String imgFilePath = "d://222.jpg";//新生成的图片 
	      out = new FileOutputStream(imgFilePath);
	      out.write(b); 
	      out.flush(); 
	      out.close(); 
	      return true; 
	    }  
	    catch (IOException e) {
	      e.printStackTrace();
	      return false; 
	    } finally {
	    	if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	  }

	  public static byte[] copyFileToBytes(String path) {
		  byte[] bytes = null;
		  RandomAccessFile featureFile = null;
		  try {
			  featureFile = new RandomAccessFile(new File(path), "rw");
			  int length = (int) featureFile.length();
			  bytes = new byte[length];
			  featureFile.readFully(bytes);
		  } catch (IOException e) {
			  e.printStackTrace();
		  } finally {
		  	if(null != featureFile){
				try {
					featureFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		  }
		  return bytes;
	  }

}
