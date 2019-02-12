package com.ferret.utils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ferret.utils.readCluster.base.ConfBase;
import com.ferret.utils.readCluster.bean.InsertPersonThread;
import com.ferret.utils.readCluster.tools.CalendTools;

public class ReadClusterUtils extends  ConfBase{
	@Autowired
	private ExecutorService threadPool;

	public void doInsertPerson() {
		//创建线程池,定长，超出的长度会在任务队列等待
		threadPool=Executors.newFixedThreadPool(config.getThreadMax());
		try {
			//读取config.ini文件中的配置,获取指定目录的file对象
			File rootFile=new File(config.getSaveImagePath());
			//获取指定文件夹下的文件数组
			File[] packageFile=rootFile.listFiles();//D:\faceProgram\img_doc\0
			int m=0;
			//判断非空后遍历
			if(rootFile.exists()&&packageFile.length>0) {
				System.out.println("文件夹目录数量："+packageFile.length);
				for(File personDir:packageFile) {
					threadPool.execute(new InsertPersonThread(personDir,m++));
				}
			}else {
				System.out.println("img_doc目录不存在,程序退出");
			}
			//关闭线程池
			threadPool.shutdown();
			this.speedMontior();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void speedMontior() {
		while(!threadPool.isTerminated()){
			int speed=(customTotal-lastTotal)/10;
			System.out.println("开始时间："+stimeStr+",当前时间"+CalendTools.getTimeString("yyyy-MM-dd HH:mm:ss")+",共处理记录："
			+customTotal+"条，速度："+speed+"条/秒,照片");
			lastTotal=customTotal;
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("共处理："+customTotal+"人");
		//System.exit(0);
	}
}
