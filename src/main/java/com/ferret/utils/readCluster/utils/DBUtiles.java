package com.ferret.utils.readCluster.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.alibaba.druid.pool.DruidDataSource;
import com.ferret.utils.readCluster.base.ConfBase;

public class DBUtiles extends ConfBase{
	public static DruidDataSource oraDataSource=null;
	public static DruidDataSource mysqlDataSource=null;
	private static Connection mysqlConn;
	private static Statement mysqlStam;
	static {
		try {
			oraDataSource = new DruidDataSource();
			
			oraDataSource.setDriverClassName(config.getDataDbDriver());
			oraDataSource.setUrl(config.getDataDbUrl());
			oraDataSource.setUsername(config.getDataDbUser());
			oraDataSource.setPassword(config.getDataDbPassword());
			oraDataSource.setInitialSize(10);
			oraDataSource.setMinIdle(5);
			oraDataSource.setMaxActive(config.getThreadMax());
			System.out.println("oracle连接池启动成功");
		}catch(Exception e) {
			System.out.println("oracle链接池 启动失败");
		}
		
		try {
			mysqlDataSource=new DruidDataSource();
			mysqlDataSource.setDriverClassName(config.getTargetDbDriver());
			mysqlDataSource.setUrl(config.getTargetDbUrl());
			mysqlDataSource.setUsername(config.getTargetDbUser());
			mysqlDataSource.setPassword(config.getTargetDbPassword());
			mysqlDataSource.setInitialSize(10);
			mysqlDataSource.setMinIdle(5);
			mysqlDataSource.setMaxActive(config.getThreadMax());
			System.out.println("mysql 连接池启动成功");
		}catch(Exception e) {
			System.out.println("mysql  启动失败");
		}
	}
	
	//可执行单个执行的插入，更新，删除等语句
	public static int executeSql(String sql) {
		System.out.println("原始sql:"+sql);
		int rtn=-1;
		try {
			mysqlConn=mysqlDataSource.getConnection();
			mysqlStam=mysqlConn.createStatement();
			if(mysqlStam.execute(sql)) {
				rtn=0;
			}
			mysqlStam.close();
			mysqlConn.close();
			mysqlConn=null;
			mysqlStam=null;
		} catch (SQLException e) {
			System.out.println("executeSql error!");	
			e.printStackTrace();
		}
		return rtn;
	}
	//执行批量提交的插入，更新，删除等语句
	public static int[] executeSqlArray(ArrayList<String> sqls) {
		int rtn[] = null;
		try {
			mysqlConn=mysqlDataSource.getConnection();
			mysqlConn.setAutoCommit(false);
			mysqlStam=mysqlConn.createStatement();
			for(String sql:sqls) {
				mysqlStam.addBatch(sql) ;
			}
			rtn=mysqlStam.executeBatch();
			mysqlConn.commit();
			mysqlConn.setAutoCommit(true);
			mysqlStam.close();
			mysqlConn.close();
		} catch (SQLException e) {
			System.out.println("executeSqlArray error!");	
			e.printStackTrace();
		}
		return rtn;
	}
	public static void main(String[] args) {
		DBUtiles.executeSql("insert into test (id,name) values (1,'张三')");
	}
}
