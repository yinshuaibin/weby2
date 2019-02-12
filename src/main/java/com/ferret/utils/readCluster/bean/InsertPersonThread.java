package com.ferret.utils.readCluster.bean;

import com.ferret.utils.readCluster.base.ConfBase;
import com.ferret.utils.readCluster.tools.CalTools;
import com.ferret.utils.readCluster.utils.DBUtiles;
import com.ferret.utils.readCluster.utils.NameUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertPersonThread extends ConfBase implements  Runnable{
	
	File personDir;
	int num;
	public InsertPersonThread(File personDir,int num) {
		this.personDir=personDir;
		this.num=num;
	}
	
	@Override
	public void run() {
		this.insertPerson(personDir);
	}
	
	
	public  void insertPerson(File personDir) {
		//System.out.println("线程：" +num+"执行开始");
		String tempPath=null;
		//tb_cluster插入语句
		String insertCluster="insert into tb_cluster (person_id,create_time,represent_img1,represent_img2,represent_img3,represent_img4,represent_img5)"
				+ " values (?,'?','?','?','?','?','?')";
		
		//tb_cluster_image 聚类通行记录插入语句
		String insertClusterImage="insert into tb_cluster_image (person_id,camera_ip,image_path,image_time,create_time"
				+ ") values (?,'?','?','?','?')";
		String fmt="yyyy-MM-dd HH:mm:ss.SSS";
		int total=0;
		try {
			//建立数据库链接
			Connection mysqlConn=DBUtiles.mysqlDataSource.getConnection();
			Statement mysqlStam=mysqlConn.createStatement();
			mysqlConn.setAutoCommit(false);
			File[] personFile=personDir.listFiles();//D:\faceProgram\img_doc\0\4
			//人的文件夹
			if (personFile != null && personFile.length > 0) {
				for(File personImage:personFile) {
					File[] images=personImage.listFiles();
					long personTime=personImage.lastModified();
					String[] representImgs=new String[5];
					int i=0;
					//人的图
					if (images != null) {
						for(File image:images) {
							if(!image.isDirectory()) {
								if(personTime<image.lastModified()) {
									personTime=image.lastModified();
								}
								//提取路径，去除路径前的斜杠
								tempPath=image.toURI().getPath();
								tempPath=tempPath.replaceFirst("[/]","");
								if(!StringUtils.equals("ini", tempPath.substring(tempPath.length()-3))){
									if(i<5) {
										representImgs[i++]=tempPath;
									}
									String clusterImageSql=insertClusterImage;
									//父文件夹的目录就是personId
									clusterImageSql=clusterImageSql.replaceFirst("[?]", NameUtils.addZero(config.getPersonQh(), personImage.getName(), 18));
									//图片ip
									clusterImageSql=clusterImageSql.replaceFirst("[?]", NameUtils.getCameraInfo(image.getName(), 0));
									//图片路径
									clusterImageSql=clusterImageSql.replaceFirst("[?]", tempPath);
									//图片时间
									clusterImageSql=clusterImageSql.replaceFirst("[?]", NameUtils.getCameraInfo(image.getName(), 2));
									//聚类完成时间
									clusterImageSql=clusterImageSql.replaceFirst("[?]", CalTools.date2str(image.lastModified(), fmt));
									//System.out.println("tb_cluster_image:  "+clusterImageSql);
									mysqlStam.addBatch(clusterImageSql);
								}
							}
						}
					}
					//cluster 记录写入
					String clusterSql=insertCluster;
					//父文件夹的目录就是personId
					clusterSql=clusterSql.replaceFirst("[?]", NameUtils.addZero(config.getPersonQh(), personImage.getName(), 18));
					//图片时间
					clusterSql=clusterSql.replaceFirst("[?]",CalTools.date2str(personTime, fmt));
					//图片路径
					for(String path:representImgs) {
						if(StringUtils.isNotBlank(path)) {
							clusterSql=clusterSql.replaceFirst("[?]", path);
						}else {
							clusterSql=clusterSql.replaceFirst("[?]", "");
						}
					}
					//System.out.println("tb_cluster:  "+clusterSql);
					//System.out.println("tb_cluster:  "+personImage.getName());
					total++;
					//处理记录的总数
					customTotal++;
					mysqlStam.addBatch(clusterSql);
				}
			}
			//每1000条记录提交一次
			if(total%1000==0){
				mysqlStam.executeBatch();
				mysqlConn.commit();
			}
			//关闭数据库链接
			mysqlStam.executeBatch();
			mysqlConn.commit();
			System.out.println("线程：" +num+"执行结束,写入："+total+" 条记录，person目录名称:"+personDir.getName()+"人物数量："+personFile.length );
			mysqlStam.close();
			mysqlConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
