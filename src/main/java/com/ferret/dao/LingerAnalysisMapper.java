package com.ferret.dao;

import java.util.List;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.ClusterPass;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.ferret.bean.TravelAtNeight;
import com.ferret.dao.provider.LingerAnalysisMapperProvide;
import org.springframework.stereotype.Repository;

/**
 * 频繁出行mapper
 * @author hyl
 * 时间:0723
 *
 */
@Repository
public interface LingerAnalysisMapper {
	/**
	 * 根据相机ip、时间段和次数查询对象<br>
	 * @return Cluster对象
	 */
	@SelectProvider(type = LingerAnalysisMapperProvide.class, method = "findAnalysisByTravelAtNeight")
	@Results({
		@Result(property="personId",column="person_id"),
		@Result(property="imagePath",column="image_path"),
		@Result(property="cameraName",column="name"),
		@Result(property="createTime",column="start_time"),
		@Result(property="longitude",column="longitude"),
		@Result(property="latitude",column="latitude"),
	})
	List<ClusterPass> seachPersonIdByCount(@Param("startNum") int startNum,
                                           @Param("pageSize") int pageSize,
                                           @Param("startTime") String startTime,
                                           @Param("endTime") String endTime,
                                           @Param("minNum") int minNum,
                                           @Param("cameraInfos") List<CameraInfo> cameraInfos);

	/**
	 * 查询频次出行人的总条数
	 * @return int 总条数
	 */
	@SelectProvider(type = LingerAnalysisMapperProvide.class, method = "findAnalysisTotalByTravelAtNeight")
	public int seachTotalByAnalysis(@Param("startTime") String startTime,
									@Param("endTime") String endTime,
									@Param("minNum") int minNum,
									@Param("cameraInfos") List<CameraInfo> cameraInfos);
	
	/**
	 * 查询出夜行人以后,根据夜行人的person_id查询对应的出行记录
	 * 
	 * @param travelAtNeight
	 * @return
	 */
	@SelectProvider(type = LingerAnalysisMapperProvide.class, method = "findAnalysisResultByPersonId")
	@Results({
		@Result(property="personId",column="person_id"),
		@Result(property="imagePath",column="image_path"),
		@Result(property="cameraName",column="name"),
		@Result(property="createTime",column="start_time"),
		@Result(property="longitude",column="longitude"),
		@Result(property="latitude",column="latitude"),
	})
	public List<ClusterPass> SeachAnalysisResultByPersonId(@Param("travelAtNeight")TravelAtNeight travelAtNeight);

	/**
	 * @Descriptions 查询出夜行人以后,根据夜行人的person_id查询对应的出行记录
	 * @author xyc 2018/12/17
	 * @return
	 */
	@SelectProvider(type = LingerAnalysisMapperProvide.class, method = "findAnalysisResultByPersonId")
	@Results({
			@Result(property="personId",column="person_id"),
			@Result(property="imagePath",column="image_path"),
			@Result(property="cameraName",column="name"),
			@Result(property="createTime",column="start_time"),
			@Result(property="longitude",column="longitude"),
			@Result(property="latitude",column="latitude"),
	})
	public List<ClusterPass> seachAnalysisResultByPersonId(@Param("startTime") String startTime,
                                                           @Param("endTime") String endTime,
                                                           @Param("startNum") int startNum,
                                                           @Param("pageSize") int pageSize,
                                                           @Param("personId") String personId,
                                                           @Param("cameraInfos") List<CameraInfo> cameraInfos);
}
