package com.ferret.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DBUtiles {
	private static Map<String,DBUtiles> dbUtils=new HashMap();
	public static DruidDataSource oraDataSource=null;
	public static DruidDataSource mysqlDataSource=null;
	private static Connection mysqlConn;
	private static Statement mysqlStam;
	private static RowSetFactory factory;
	public DBUtiles(String utilName,String className,String dbUrl,String user,String password,
			int initSize,int minSize,int maxActive) {
		try {
			mysqlDataSource=new DruidDataSource();
			mysqlDataSource.setDriverClassName(className);
			//"jdbc:mysql://127.0.0.1:3306/db_checkface?characterEncoding=utf8&useSSL=false"
			mysqlDataSource.setUrl(dbUrl);
			mysqlDataSource.setUsername(user);
			mysqlDataSource.setPassword(password);
			mysqlDataSource.setInitialSize(initSize);
			mysqlDataSource.setMinIdle(minSize);
			mysqlDataSource.setMaxActive(maxActive);
			factory= RowSetProvider.newFactory();
			dbUtils.put(utilName, this);
			log.info("mysql 链接池启动成功");
		}catch(Exception e) {
			log.error("mysql 链接池启动失败");
			e.printStackTrace();
		}
	}
	public static DBUtiles getUtil(String utilName) {
		return dbUtils.get(utilName);
	}
	//执行一条sql
	public  int executeSql(String sql) {
		System.out.println("sql:"+sql);
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
	//执行sql列表
	public  int[] executeSqlArray(ArrayList<String> sqls) {
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
	
	public  CachedRowSet mysqlQuery(String sql) {
		try {
			mysqlConn=mysqlDataSource.getConnection();
			mysqlStam=mysqlConn.createStatement();
			ResultSet rs=mysqlStam.executeQuery(sql);
			CachedRowSet crs = factory.createCachedRowSet();
			crs.populate(rs);
			mysqlStam.close();
			mysqlConn.close();
			return crs;
		} catch (SQLException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public  ResultSet mysqlQuery1(String sql) {
		try {
			mysqlConn=mysqlDataSource.getConnection();
			mysqlStam=mysqlConn.createStatement();
			ResultSet rs=mysqlStam.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	public  CachedRowSet mysqlCall(String sql) {
		try {
			mysqlConn=mysqlDataSource.getConnection();
			mysqlStam=mysqlConn.prepareCall(sql);
			ResultSet rs=mysqlStam.executeQuery(sql);
			CachedRowSet crs = factory.createCachedRowSet();
			crs.populate(rs);
			mysqlStam.close();
			mysqlConn.close();
			return crs;
		} catch (SQLException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	public  void close() {
			try {
				if(mysqlStam!=null)
					mysqlStam.close();
				if(mysqlConn!=null) {
					mysqlConn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
