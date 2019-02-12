package com.ferret.controller;

import com.ferret.bean.staticBean.SeachStatic;
import com.ferret.bean.staticBean.StaticPorts;
import com.ferret.service.common.StaticService;
import com.ferret.utils.staticUtils.FTPUtils;
import com.ferret.utils.staticUtils.ReadTXTFileUtils;
import com.ferret.utils.staticUtils.ZipFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class StaticController {
	
	@Autowired
	private StaticService staticService;
	
	private FTPUtils ftpUtils = new FTPUtils();
	
	private static ZipFileUtils zipFileUtils = new ZipFileUtils();
	
	private ReadTXTFileUtils readTXTFileUtils = new ReadTXTFileUtils();
	
	private static String IP = "10.56.12.169";
	private static int port = 22;
	private static String username = "ztry";
	private static String password = "ztry";
	private static String remotePath = "";
	private static String localPath = "E:/FTP/ZIP/";
	private static String localPathZP = "E:/FTP/ZP/";
	private static String localPathTXT = "E:/FTP/TXT/";
	private static String fileName = null;
//	private static String[] file = new String[] {".zip","_ZL.zip","_ZP.zip"};
//	private static String[] txtFile = new String[] {".txt","_ZL.txt","_SC.txt","_CX.txt"};
	private static String[] file = new String[] {".zip","_ZP.zip"};
	private static String[] txtFile = new String[] {".txt"};
	
//  #全国逃犯库FTP连接
//  FTP:
//    IP: 10.56.12.169
//    port: 22
//    un: ztry
//    psw: ztry
//    storagePath: 
//    storagePath: 
	public static void main(String[] args) {
		System.err.println("开始运行---------------------------");
		//test();
	}
	
	@RequestMapping(value = "/face/static/search", method = RequestMethod.POST)
	public List<List<?>> searchStaticData(@RequestBody SeachStatic data) {
		//源代码
		System.err.println("------------------------");
		List<List<?>> datas = staticService.searchStaticData(data);
		System.err.println(datas.size());
		return datas;
	}
	
	@RequestMapping(value = "/face/static/queryPorts", method = RequestMethod.POST)
	public List<StaticPorts> queryPorts() {
		System.err.println("------------------------");
		return staticService.queryPorts();
	}
	
	//@Scheduled(cron = "0 0 1 * * ?")//每天1点执行一次
	public void timerToNow() {
		File filstr = new File(localPath + fileName);
		if (!filstr.exists()) {
			boolean mkdirs = filstr.mkdirs();
			if(!mkdirs){
				log.error("名称为:"+filstr.getName()+" 的文件夹创建失败,请检查文件路径");
				return;
			}
		}
		//创建指定格式的日期对象
		Date d = new Date();  
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfMouth = new SimpleDateFormat("MM");
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String newdate = sdf.format(d);
        System.err.println("时间格式调整完成------------------------------");
        System.out.println(newdate);
        //测试用
        newdate = "20180504";
        System.out.println(newdate);
		//获取ftp上的压缩文件,下载至指定路径
        //将文件解压至本地
        for (int i = 0; i < file.length; i++) {
			fileName = newdate + file[i];
			System.err.println("要解压的压缩文件名称为------"+fileName);
			//ftpUtils.downFile(IP, port, username, password, remotePath, fileName, localPath);
			//解压文件到对应路径
			if(file[i] == "_ZP.zip") {
				//创建压缩文件至指定路径
				File zipFile = new File(localPath + fileName);
				System.err.println("要解压的压缩文件路径为------"+localPath+ sdfYear + "/" + sdfMouth + "/" + sdfDay + "/" + fileName);
				localPathZP = localPathZP + sdfYear + "/" + sdfMouth + "/" + sdfDay + "/";
				try {
					zipFileUtils.unZipFiles(zipFile, localPathZP);
					System.err.println("解压到------"+localPathZP);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				File zipFile = new File(localPath + fileName);
				System.err.println("要解压的压缩文件路径为------"+localPath + fileName);
				try {
					zipFileUtils.unZipFiles(zipFile, localPathTXT);
					System.err.println("解压到------"+localPathTXT);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
        System.err.println("解压完成------------------------------------------");
        handleTXTData(newdate);
	}
	
	
	//将全国逃犯库存入数据库
	public static void test() {
		//若指定路径不存在则创建文件夹
		File filestr = new File(localPath + fileName);
		if (!filestr.exists()) {
			boolean mkdirs = filestr.mkdirs();
			if(!mkdirs){
				log.error("名称为:"+filestr.getName()+" 的文件夹创建失败,请检查文件路径");
				return;
			}
		}
		//创建指定格式的日期对象
		Date d = new Date();  
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfMouth = new SimpleDateFormat("MM");
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String newdate = sdf.format(d);
        System.err.println("时间格式调整完成------------------------------");
        System.out.println(newdate);
        //测试用
        newdate = "20180504";
        System.out.println(newdate);
		//获取ftp上的压缩文件,下载至指定路径
        //将文件解压至本地
        for (int i = 0; i < file.length; i++) {
			fileName = newdate + file[i];
			System.err.println("要解压的压缩文件名称为------"+fileName);
			//ftpUtils.downFile(IP, port, username, password, remotePath, fileName, localPath);
			//解压文件到对应路径
			if(file[i] == "_ZP.zip") {
				//创建压缩文件至指定路径
				File zipFile = new File(localPath + fileName);
				System.err.println("要解压的压缩文件路径为------"+localPath + fileName);
				try {
					localPathZP = localPathZP + sdfYear + "/" + sdfMouth + "/" + sdfDay + "/";
					zipFileUtils.unZipFiles(zipFile, localPathZP);
					System.err.println("解压到------"+localPathZP);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				File zipFile = new File(localPath + fileName);
				System.err.println("要解压的压缩文件路径为------"+localPath + fileName);
				try {
					zipFileUtils.unZipFiles(zipFile, localPathTXT);
					System.err.println("解压到------"+localPathTXT);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
        System.err.println("解压完成------------------------------------------");
        //handleTXTData(newdate);
        handleJPGData();
	}
	
	public static void handleTXTData(String newdate) {
		System.err.println("开始读取数据--------------------------------------");
		//处理解压后文件中的数据
			//读取数据
        for (int i = 0; i < txtFile.length; i++) {
        	String txtNmae = newdate + txtFile[i];
        	//取对应路径下的文件并处理
        	System.err.println(txtNmae);
			List<String> readFile = ReadTXTFileUtils.readFile(localPathTXT + txtNmae);
			readFile.remove(0);
			//对比数据库并进行对应操作
			String sql = "";
	        switch(txtFile[i]){
	        case ".txt":
//	        	//处理逃犯库总数据
//	            System.err.println("0");
//	            sql = "INSERT INTO QGTFK VALUES (";
//	            try {
//					ReadTXTFileUtils.read(readFile, sql);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	            break;
	        case "_ZL.txt":
	        	//当天上网数据
	            System.err.println("1");
	            break;
	        case "_SC.txt":
	        	//当天上传数据
	            System.err.println("2");
	            break;
	        case "_CX.txt":
	        	//当天撤销数据
	            System.err.println("3");
	            break;
	        default:
	            System.err.println("不是指定的文件！！！！！！");
	            break;
	        }
        }
        System.err.println("数据处理完成--------------------------------");
	}
	
	public static void handleJPGData() {
//		staticService.handleJPGData(localPathZP);
	}
}
