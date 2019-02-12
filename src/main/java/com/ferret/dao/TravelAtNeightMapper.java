package com.ferret.dao;

import java.util.List;

import com.ferret.bean.ClusterPass;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.ferret.bean.TravelAtNeight;
import com.ferret.dao.provider.TravelAtNeightMapperProvider;

/**
 * 夜行人mapper
 * @author y
 * 时间:0720
 *
 */
public interface TravelAtNeightMapper {

	/**
	 * 查询夜行人总条数
	 * @param travelAtNeight
	 * @return int
	 */
	@SelectProvider(type = TravelAtNeightMapperProvider.class, method = "findTravelAtNeightTotal")
	public int findTravelAtNeightTotal(TravelAtNeight travelAtNeight);
	
	/**
	 * 查询夜行人结果集
	 * @param travelAtNeight
	 * @return List<ClusterPass>
	 */
	@SelectProvider(type = TravelAtNeightMapperProvider.class, method = "findTravelAtNeightList")
	@Results({
		@Result(property="personId",column="person_id"),
		@Result(property="imagePath",column="image_path"),
		@Result(property="cameraName",column="name"),
		@Result(property="createTime",column="start_time"),
		@Result(property="longitude",column="longitude"),
		@Result(property="latitude",column="latitude"),
	})
	public List<ClusterPass> findTravelAtNeightList(@Param("travelAtNeight")TravelAtNeight travelAtNeight);

	/**
	 * 查询出夜行人以后,根据夜行人的person_id查询对应的出行记录
	 * 
	 * @param travelAtNeight
	 * @return
	 */
	@SelectProvider(type = TravelAtNeightMapperProvider.class, method = "findTravelAtNeightByPersonId")
	@Results({
		@Result(property="personId",column="person_id"),
		@Result(property="imagePath",column="image_path"),
		@Result(property="cameraName",column="name"),
		@Result(property="createTime",column="start_time"),
		@Result(property="longitude",column="longitude"),
		@Result(property="latitude",column="latitude"),
	})
	public List<ClusterPass> findTravelAtNeightByPersonId(@Param("travelAtNeight")TravelAtNeight travelAtNeight);
}
