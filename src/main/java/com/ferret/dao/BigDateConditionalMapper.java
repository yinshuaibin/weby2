package com.ferret.dao;

import com.ferret.bean.BigDateCondition;
import com.ferret.bean.PersonnelStatistics;
import com.ferret.dao.provider.BigDateConditionalMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BigDateConditionalMapper {
	
	//-----------------------------------------------------------------
	//报表所需的sql
	
	
	//获取bigData表的数据
	@Select("SELECT SUM(c_count) FROM tb_bigdata")
	Integer getPermonthTotal();
	 //获取指定条件的bigData表的数据
	@Select("SELECT COUNT(*) FROM tb_cluster WHERE create_time >= #{onTime} AND create_time <= #{offTime}")
	Integer getCamdata(@Param("onTime")String onTime, @Param("offTime")String offTime);
	
	@Select("SELECT COUNT(*) FROM tb_cluster_image WHERE camera_ip = #{ip} AND create_time >= #{onTime} AND create_time <= #{offTime}")
	Integer getCameradata(@Param("ip")String ip, @Param("onTime")String onTime, @Param("offTime")String offTime);

	@Select("SELECT ip FROM tb_camera_info WHERE number = #{cameraNumber}")
	String getIp(@Param("cameraNumber")String cameraNumber);
	
	@Select("SELECT COUNT(*) FROM tb_cluster_image WHERE camera_ip = #{ip} AND create_time <= #{offTime}")
	Integer getCameradata1(@Param("ip")String ip, @Param("offTime")String offTime);

	@Select("SELECT COUNT(*) FROM tb_cluster_image WHERE camera_ip = #{ip} AND create_time >= #{onTime}")
	Integer getCameradata2(@Param("ip")String ip, @Param("onTime")String onTime);

	@Select("SELECT COUNT(*) FROM tb_cluster_image WHERE camera_ip = #{ip}")
	Integer getCameradata3(@Param("ip")String ip);

	@Select("SELECT name FROM tb_camera_info WHERE number = #{cameraNumber}")
	String getName(@Param("cameraNumber")String cameraNumber);
	
	@Select("SELECT name FROM tb_camera_info WHERE number = #{cameraNumber}")
	String getCameraInfo(@Param("cameraNumber")String cameraNumber);
	
	@Select("select name from tb_camera_info where number like #{area}")
	List<String> getCameraInfoByArea(@Param("area")String area);
	
	
	//-----------------------------------------------------------------
	//表格索取的sql
	
	//根据IP查找tb_cluster_image表里出现过的person_id----总人数
	@Select("select DISTINCT person_id from tb_cluster_image WHERE camera_ip = #{ip}")
	List<String> getCamSum(@Param("ip")String ip);
	
	//根据IP查找对应时间内tb_cluster_image表里出现过的person_id----对应时间内总人数
	@Select("select DISTINCT person_id from tb_cluster_image WHERE camera_ip = #{ip} and create_time >= #{onTime} AND create_time <= #{offTime}")
	List<String> getCamTimeSum(@Param("ip")String ip, @Param("onTime")String onTime, @Param("offTime")String offTime);
	
	@Select("select DISTINCT person_id from tb_cluster_image WHERE camera_ip = #{ip} and create_time >= #{todayS}")
	List<String> getCamTodaySum(@Param("ip")String ip, @Param("todayS")String todayS);

	@SelectProvider(type = BigDateConditionalMapperProvider.class, method = "getBigDataConditional")
	@Results({
			@Result(property = "cameraName", column = "name"),
			@Result(property = "countToday", column = "today_num"),
			@Result(property = "count", column = "count"),
			@Result(property = "yesterdayPerNum", column = "yesterday_num"),
	})
	List<PersonnelStatistics> seachData(@Param("bigDateCondition") BigDateCondition strangerSearch);


	
}
