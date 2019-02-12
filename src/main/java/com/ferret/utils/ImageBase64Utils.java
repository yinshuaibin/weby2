package com.ferret.utils;

import com.ferret.bean.Base64ImageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
public class ImageBase64Utils {
	 
	//图片转化成base64字符串  
	public static String getImageStr(String url)
	    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
	        String imgFile =url;//待处理的图片  
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
	        catch (IOException e) {
	            //e.printStackTrace();
	        }  finally {
	        	if( null != in){
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
	public static boolean generateImage(String imgStr, String imgPath)
	    {   //对字节数组字符串进行Base64解码并生成图片  
	        if (imgStr == null) //图像数据为空  
	            return false;  
	        BASE64Decoder decoder = new BASE64Decoder();  
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
	            OutputStream out = null;
				try {
					out = new FileOutputStream(imgPath );
					out.write(b);
					out.flush();
					out.close();
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

	            return true;  
	        }   
	        catch (IOException e)
	        {  
	            return false;  
	        }  
	    }
	    
	    
	 //获取布控图片路径/名称并创建相应文件夹
	public static List<String> createImgData(String bukongDir) {
		List list = new ArrayList<>();
		String imgName = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		File file = new File(bukongDir);
		if (!file.exists()) {
			boolean mkdirs = file.mkdirs();
			if(!mkdirs){
				log.error("名称为:"+file.getName()+" 的文件夹创建失败,请检查文件路径");
				return null;
			}
		}
		//bukongDir的路径为E:/ftpdata/BK/1
	    String[] filelist = file.list();
	    String imgPath = "";
	    if (filelist == null) {
	    	return null;
		}
	    if(filelist.length==0) {//如果子文件夹个数为零
	    	file = new File(bukongDir+"/image1");
	    	System.out.println("子文件夹个数为零新建图片路径:"+bukongDir+"/image1");
			if (!file.exists()) {//如果文件夹不存在
				boolean mkdirs = file.mkdirs();
				if(!mkdirs){
					log.error("名称为:"+file.getName()+" 的文件夹创建失败,请检查文件路径");
					return null;
				}
			}
	    	imgPath = bukongDir+"/image1/";    
	    }else {//子文件夹数量不为零
	    	String bukongDir2 = bukongDir+"/image"+filelist.length;//获取最后新建的个子文件夹名称
	    	file = new File(bukongDir2);
	    	if(file.length()>9999) {//如果图片数量达到10000就新建子文件夹
	    		file = new File(bukongDir+"/image"+(filelist.length+1)+"/");
				if (!file.exists()) {//如果文件夹不存在
					boolean mkdirs = file.mkdirs();
					if(!mkdirs){
						log.error("名称为:"+file.getName()+" 的文件夹创建失败,请检查文件路径");
					}
				}
		    	imgPath = bukongDir+"/image"+(filelist.length+1)+"/";
	    	}else {//图片数量未达到10000
	    		imgPath = bukongDir2+"/";
	    	}
	    }
	    list.add(imgName);
	    list.add(imgPath);
		return list;
	}

	/**
	 *
	 * 图片url地址转base64String编码
	 * @author cc
	 * @since 2018-07-04 12:27:00
	 * @param imgUrl 图片地址
	 * @return base64 字符串
	 */
	public static Base64ImageEntity generateBase64ImageEntity(String imgUrl){
		byte[] data =null;
		try {
			URL url = new URL(imgUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			//设置超时间为3秒
			conn.setConnectTimeout(5*1000);
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			InputStream inStream = conn.getInputStream();
			data = readInputStream(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		String base64 = new Base64().encodeToString(data);
		String imgType = imgUrl.substring(imgUrl.lastIndexOf("."));
		return new Base64ImageEntity(base64,imgType);
	}


	private static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		//创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		//每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		//使用一个输入流从buffer里把数据读取出来
		while( (len=inStream.read(buffer)) != -1 ){
			//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		//关闭输入流
		inStream.close();
		//把outStream里的数据写入内存
		return outStream.toByteArray();
	}

}
