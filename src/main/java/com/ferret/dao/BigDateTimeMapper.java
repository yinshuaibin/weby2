package com.ferret.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BigDateTimeMapper {
	
	@Select("SELECT ip FROM tb_camera_info")
	List<String> getIPs();

	@Select("SELECT DISTINCT COUNT(camera_id) "
			+ "FROM tb_cluster_image "
			+ "WHERE camera_ip = #{ip} "
			+ "AND create_time >= #{yesterdayTime} "
			+ "AND create_time <= #{time};" + 
			"AND person_id = ANY("
				+ "SELECT person_id "
				+ "FROM tb_cluster "
				+ "WHERE create_time >= #{yesterdayTime} "
				+ "AND create_time <= #{time}; )")
	Integer getNewNum(@Param("ip")String ip, @Param("time")String time, @Param("yesterdayTime")String yesterdayTime);

	@Insert("INSERT INTO tb_bigdata_cope VALUES (#{c_starttime},#{c_endtime},#{c_count},#{camera_ip},{c_count_name})")
	void setBigDataNew(@Param("c_starttime")String c_starttime, @Param("c_endtime")String c_endtime, @Param("c_count")Integer c_count, @Param("camera_ip")String camera_ip, @Param("c_count_name")String c_count_name);
	
}
