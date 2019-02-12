package com.ferret.utils.readCluster.bean;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.ferret.utils.readCluster.utils.FileUtils;


public class Sysconf {
	//数据源配置信息
	private String dataDbDriver;
	private String dataDbUrl;
	private String dataDbUser;
	private String dataDbPassword;
	
	private String sourceSql;
	private String updateFields;
	//String,Date,Integer,dateString 参数类型
	private String paramType;
	//步长,对照paramType的数据类型设置
	private String skip;
	
	private String getPersonInforSQL;
	private String inParamType;
	private String imageFieldName;
	private String sfzhFieldName;
	private String xmFieldName;
	private String idFieldName;
	private String targetTableName;
	
	//图片存储路径
	String saveImagePath;
	
	//数据总量
	private int recordTotal;
	private int startRownum;
	

	//线程数
	private int threadMax;
	


	//单线程下载量
	private int threadReadNum;
	
	//是否往tb_persons写数据
	private boolean isInsert;
	private boolean md5;
	//目标库配置信息
	private String targetDbDriver;
	private String targetDbUrl;
	private String targetDbUser;
	private String targetDbPassword;
	private String tableInsertSQL;
	private String qhFilePath;
	private String personQh;
	public Sysconf(){
		try {
			File appFile=new File(".");
			String configFilePath=appFile.getCanonicalPath()+"\\config.ini";
			qhFilePath=appFile.getCanonicalPath()+"\\qh.txt";
			System.out.println(configFilePath);
			Map config=FileUtils.getProperties(configFilePath);
			this.dataDbDriver=config.get("dataSource.db.driver").toString();
			this.dataDbUrl=config.get("dataSource.db.url").toString();
			this.dataDbUser=config.get("dataSource.db.user").toString();
			this.dataDbPassword=config.get("dataSource.db.password").toString();
			this.sourceSql=config.get("sourceSQL").toString();
			this.updateFields=config.get("updateFields").toString();
			this.paramType=config.get("inParamType").toString();
			//this.skip=config.get("skip").toString();
			
			
			this.getPersonInforSQL=config.get("getPersonInforSQL").toString();
			this.inParamType=config.get("inParamType").toString();
			this.imageFieldName=config.get("imageFieldName").toString();
			this.sfzhFieldName=config.get("sfzhFieldName").toString();
			this.xmFieldName=config.get("xmFieldName").toString();
			this.idFieldName=config.get("idFieldName").toString();
			
			this.isInsert=Boolean.parseBoolean(config.get("isInsert").toString());
			this.md5=Boolean.parseBoolean(config.get("md5").toString());
			System.out.println("是否更新表"+this.isInsert);
			this.targetDbDriver=config.get("target.db.driver").toString();
			this.targetDbUrl=config.get("target.db.url").toString();
			this.targetDbUser=config.get("target.db.user").toString();
			this.targetDbPassword=config.get("target.db.password").toString();
			this.tableInsertSQL=config.get("table.person.insertSQL").toString();
			this.targetTableName=config.get("table.target.name").toString();
			this.saveImagePath=config.get("saveImagePath").toString();
			this.personQh=config.get("personQH").toString();
			this.recordTotal=Integer.parseInt(config.get("recordTotal").toString().trim());
			this.startRownum=Integer.parseInt(config.get("startRownum").toString().trim());
			this.threadMax=Integer.parseInt(config.get("threadMax").toString().trim());
			this.threadReadNum=Integer.parseInt(config.get("threadReadNum").toString().trim());
			
			System.out.println("配置文件读取成功");		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	public String getInParamType() {
		return inParamType;
	}



	public void setInParamType(String inParamType) {
		this.inParamType = inParamType;
	}



	public String getDataDbDriver() {
		return dataDbDriver;
	}

	public void setDataDbDriver(String dataDbDriver) {
		this.dataDbDriver = dataDbDriver;
	}

	public String getDataDbUrl() {
		return dataDbUrl;
	}

	public void setDataDbUrl(String dataDbUrl) {
		this.dataDbUrl = dataDbUrl;
	}

	public String getDataDbUser() {
		return dataDbUser;
	}

	public void setDataDbUser(String dataDbUser) {
		this.dataDbUser = dataDbUser;
	}

	public String getDataDbPassword() {
		return dataDbPassword;
	}

	public void setDataDbPassword(String dataDbPassword) {
		this.dataDbPassword = dataDbPassword;
	}

	public String getTargetDbDriver() {
		return targetDbDriver;
	}

	public String getSourceSql() {
		return sourceSql;
	}

	public void setSourceSql(String sourceSql) {
		this.sourceSql = sourceSql;
	}

	public void setTargetDbDriver(String targetDbDriver) {
		this.targetDbDriver = targetDbDriver;
	}

	public String getTargetDbUrl() {
		return targetDbUrl;
	}

	public void setTargetDbUrl(String targetDbUrl) {
		this.targetDbUrl = targetDbUrl;
	}

	public String getTargetDbUser() {
		return targetDbUser;
	}

	public void setTargetDbUser(String targetDbUser) {
		this.targetDbUser = targetDbUser;
	}

	public String getTargetDbPassword() {
		return targetDbPassword;
	}

	public void setTargetDbPassword(String targetDbPassword) {
		this.targetDbPassword = targetDbPassword;
	}

	
	public String getTableInsertSQL() {
		return tableInsertSQL;
	}



	public void setTableInsertSQL(String tableInsertSQL) {
		this.tableInsertSQL = tableInsertSQL;
	}



	public String getSaveImagePath() {
		return saveImagePath;
	}

	public void setSaveImagePath(String saveImagePath) {
		this.saveImagePath = saveImagePath;
	}

	public String getImageFieldName() {
		return imageFieldName;
	}

	public void setImageFieldName(String imageFieldName) {
		this.imageFieldName = imageFieldName;
	}

	public int getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(int total) {
		this.recordTotal = total;
	}

	public int getThreadMax() {
		return threadMax;
	}

	public void setThreadMax(int threadMax) {
		this.threadMax = threadMax;
	}

	public int getThreadReadNum() {
		return threadReadNum;
	}

	public void setThreadReadNum(int threadReadNum) {
		this.threadReadNum = threadReadNum;
	}

	public boolean isInsert() {
		return isInsert;
	}

	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}
	public int getStartRownum() {
		return startRownum;
	}

	public void setStartRownum(int startRownum) {
		this.startRownum = startRownum;
	}


	public String getQhFilePath() {
		return qhFilePath;
	}



	public void setQhFilePath(String qhFilePath) {
		this.qhFilePath = qhFilePath;
	}



	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public String getSfzhFieldName() {
		return sfzhFieldName;
	}

	public void setSfzhFieldName(String sfzhFieldName) {
		this.sfzhFieldName = sfzhFieldName;
	}

	public String getXmFieldName() {
		return xmFieldName;
	}

	public void setXmFieldName(String xmFieldName) {
		this.xmFieldName = xmFieldName;
	}

	public String getIdFieldName() {
		return idFieldName;
	}

	public void setIdFieldName(String idFieldName) {
		this.idFieldName = idFieldName;
	}
	public String getParamType() {
		return paramType;
	}



	public void setParamType(String paramType) {
		this.paramType = paramType;
	}



	public String getSkip() {
		return skip;
	}



	public void setSkip(String skip) {
		this.skip = skip;
	}



	public String getGetPersonInforSQL() {
		return getPersonInforSQL;
	}



	public void setGetPersonInforSQL(String getPersonInforSQL) {
		this.getPersonInforSQL = getPersonInforSQL;
	}



	public boolean isMd5() {
		return md5;
	}



	public void setMd5(boolean md5) {
		this.md5 = md5;
	}



	public String getUpdateFields() {
		return updateFields;
	}



	public void setUpdateFields(String updateFields) {
		this.updateFields = updateFields;
	}



	public String getPersonQh() {
		return personQh;
	}



	public void setPersonQh(String personQh) {
		this.personQh = personQh;
	}
	
}
