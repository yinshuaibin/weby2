package com.ferret.dao;

import com.ferret.bean.*;
import com.ferret.dao.provider.ClusterImgMapperProvider;
import com.ferret.dto.ClusterAdvanceDTO;
import com.ferret.dto.ClusterBrowseDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ClusterImgMapper {
	/*
	 * 根据personId查到同行人的id offset:分页起始记录 pageSize
	 *
	 */
	@Select("call tx_pass_query(#{personId},#{startTime},#{endTime},#{minNum})")
	@Options(statementType = StatementType.CALLABLE)
	@Results({ @Result(id = true, property = "personId", column = "txperson_id"),
			@Result(property = "represent_img", column = "tximage_path"),
			@Result(property = "txTime", column = "tximage_time"),
			@Result(property = "represent_img1", column = "image_path"),
			@Result(property = "startTime", column = "start_time"),
			@Result(property = "endTime", column = "end_time"),
			@Result(property = "cameraip", column = "camera_ip"),
			@Result(property = "txId", column = "person_id"),
			@Result(property = "name", column = "name"),
			@Result(property = "latitude", column = "latitude"),
			@Result(property = "longitude", column = "longitude"),
	})
	public List<Cluster> findTXCluster(@Param("personId") String personId, @Param("startTime") String startTime,
									   @Param("endTime") String endTime, @Param("minNum") int minNum);

	/**
	 * 根据两个personId查询出同行历史
	 *
	 * @param personId
	 * @param personId1
	 * @return
	 */
	@Select("SELECT	a.image_path image_path,a.camera_ip camera_ip,	a.image_time image_time,"
			+ "	b.image_path image_path1,b.image_time image_time1 FROM	tb_cluster_image AS a"
			+ " INNER JOIN tb_cluster_image AS b ON a.camera_ip = b.camera_ip  WHERE"
			+ "	a.person_id =#{personId} AND b.person_id =#{personId1} AND a.person_id <> b.person_id"
			+ "  AND abs(UNIX_TIMESTAMP(a.image_time) - UNIX_TIMESTAMP(b.image_time)) < 1800 GROUP BY"
			+ "	substr(a.image_path, - 41, 12),substr(b.image_path, - 41, 12)")
	@Results({ @Result(property = "imagePath", column = "image_path"),
			@Result(property = "imagePath2", column = "image_path1"), @Result(property = "dizhi", column = "camera_ip"),
			@Result(property = "p1d", column = "image_time"), @Result(property = "p2d", column = "image_time1") })
	public List<TongXingBack> Txhistory(@Param("personId") String personId, @Param("personId1") String personId1);

	@Select("select name from tb_camera_info where ip=#{camerIp}")
	String findCamerNameByIp(@Param("camerIp") String camerIp);

	@Select("select name,longitude,latitude from tb_camera_info where ip=#{camerIp}")
	public CameraInfo findCamerNameAndLocationByIp(@Param("camerIp") String camerIp);

	/**
	 * 根据personId查询tb_cluster_pass 获取第一条数据
	 * 修改人  y
	 * 修改时间  0717
	 * @param perSid
	 * @return
	 */
	@Select("select COUNT(person_id)count, tc.person_id, tc.image_path,tc.start_time image_time,tb.name,tb.longitude,tb.latitude from tb_cluster_pass tc LEFT JOIN tb_camera_info tb on tc.camera_ip=tb.ip where person_id=#{perSid} LIMIT 0,1")
	@Results({ @Result(property = "personId", column = "person_id"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "createTime", column = "image_time"),
			@Result(property = "cameraName", column = "name"), })
	ClusterSearch findClusterImageByPerId(@Param("perSid") String perSid);

	/**
	 * @Title getStrangerByCameraAndTime
	 * @Description 查找陌生人数据
	 * @author xyz
	 * @Date created in 2018/7/23 11:54
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getStrangerByCameraAndTimeList")
	@Options(statementType = StatementType.CALLABLE)
	@Results({ @Result(property = "personId", column = "person_id"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "createTime", column = "start_time"),
			@Result(property = "count", column = "count"),
			@Result(property = "cameraName", column = "camera_name"),
			@Result(property = "longitude", column = "longitude"),
			@Result(property = "latitude", column = "latitude")})
	List<Stranger> getStrangerByCameraAndTime(@Param("startTime") String startTime,
											  @Param("stime") String stime,
											  @Param("endTime") String endTime,
											  @Param("startNum") int startNum,
											  @Param("pageSize") int pageSize,
											  @Param("cameraInfos") List<CameraInfo> cameraInfos);

	/**
	 * @Title getStrangerByCameraAndTime_count
	 * @Description 获取总条数
	 * @author xieyingchao
	 * @Date created in 2018/7/20 16:42
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getStrangerByCameraAndTimeList_count")
	@Options(statementType = StatementType.CALLABLE)
	int getStrangerByCameraAndTime_count(@Param("startTime") String startTime,
										 @Param("stime") String stime,
										 @Param("endTime") String endTime,
										 @Param("cameraInfos") List<CameraInfo> cameraInfos);


	/**
	 * @Title getStrangerByPersonId
	 * @Description 获取某个用户在时间段内出现在一个或多个相机下的所有信息
	 * @author xyz
	 * @Date created in 2018/7/23 15:36
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getStrangerByPersonIdList")
	@Options(statementType = StatementType.CALLABLE)
	@Results({ @Result(property = "personId", column = "person_id"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "createTime", column = "start_time"),
			@Result(property = "cameraName", column = "camera_name"),
			@Result(property = "longitude", column = "longitude"),
			@Result(property = "latitude", column = "latitude")})
	public List<Stranger> getStrangerByPersonId(@Param("startTime") String startTime,
												@Param("stime") String stime,
												@Param("endTime") String endTime,
												@Param("startNum") int startNum,
												@Param("pageSize") int pageSize,
												@Param("cameraInfos") List<CameraInfo> cameraInfos,
												@Param("personId") String personId);


	/**
	 * 根据相机ip,当前时间减一秒,轮循数据库db_realtimeimage表,获取实时抓拍图
	 *
	 * @param cameraIp
	 *            相机ip
	 * @param imageDate
	 *            日期
	 * @param imageTime
	 *            时间
	 * @return
	 */
	@Select("SELECT ImagePath from db_realtimeimage where ImageDate=#{imageDate} and ImageTime=#{imageTime} and CamIP=#{cameraIp}")
	public List<RealTimeImage> findCameraImageByTime(@Param("cameraIp") String cameraIp,
													 @Param("imageDate") String imageDate, @Param("imageTime") String imageTime);

	/**
	 * 查询db_realtimeimage中最大的id值
	 * @return
	 */
	@Select("SELECT IFNULL(MAX(id),0) from jh_facepic")
    int getMaxID();

	/**
	 * 实时抓拍sql
	 * @param cameraIp
	 * @param maxID
	 * @return
	 */
	@Select("SELECT id ID,picpath ImagePath from jh_facepic where id>#{maxID} and cameraid=(SELECT cameraid FROM jh_camera WHERE cameraip = #{cameraIp})")
	List<RealTimeImage> findImageByIpAndMaxID(@Param("cameraIp") String cameraIp, @Param("maxID") int maxID);

	/**
	 * 根据条件查询tb_cluster中最新的聚类数据
	 * y 0828
	 * @return
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getStrangerOne")
	@Results({ @Result(property = "personId", column = "person_id"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "createTime", column = "create_time"),
			@Result(property = "cameraName", column = "name") })
	List<Stranger> getNewCluster(@Param("cameraList")List<CameraInfo> cameraList,@Param("pageSize")Integer pageSize);

	/**
	 * 根据时间段,查询tb_cluster新进数据
	 * 修改人:y
	 * 修改时间:0828
	 * 修改原因,实时陌生人 ,增加摄像头区域选择
	 * @return
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getStranger")
	@Results({ @Result(property = "personId", column = "person_id"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "createTime", column = "create_time"),
			@Result(property = "cameraName", column = "name") })
	List<Stranger> getStranger(@Param("cameraList")List<CameraInfo> cameraList);


	/**
	 * 根据时间段查询聚类中中对应的personId,imagePath
	 * y  0114 改
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("SELECT personId,picpath imagePath FROM jh_cluster_pic WHERE clusterid in ( SELECT clusterid FROM jh_cluster WHERE createtime>=#{startTime} AND createtime <=#{endTime} GROUP BY personid ) ORDER BY id LIMIT 1")
	List<Cluster> findClusterByCreateTime(@Param("startTime")String startTime,@Param("endTime")String endTime);

	/**
	 * 批量更新idCard name represent_img5 根据person_id
	 * y
	 * @param list
	 * @return
	 * 1107
	 */
	int perfectinformation(List<Cluster> list);

	/**
	 * 根据personId查询对应的聚类人员,为了方便使用,使用list接收
	 * y 0114 改
	 * @param personId
	 * @return
	 */
	@Select("SELECT picpath imagePath FROM jh_cluster_pic WHERE clusterid = ( SELECT clusterid FROM jh_cluster WHERE personid = #{personId} GROUP BY personid ) ORDER BY id LIMIT 1")
	List<Cluster> findClusterByPersonId(String personId);

	/**
	 * 将人员完整后的信息(市局)存入数据库中
	 * @param name 姓名
	 * @param imagePath 身份证照
	 * @param idCard 身份证号
	 * @param  personId 聚类号
	 * @return
	 */
	// @Insert("UPDATE tb_cluster set idcard=#{idcard}, represent_img5 =#{imagePath},name=#{name} WHERE person_id=#{personId}")
	@Insert("UPDATE jh_person SET cardnumberpicpath = #{imagePath},cardnumber = #{idcard},name = #{name} WHERE personid = #{personId}")
	int perfectinformation(@Param("name")String name,@Param("imagePath")String imagePath,@Param("idcard")String idCard,@Param("personId")String personId);

	/**
	 * 根据该人员id查询对应地区,对应时间出现的所有信息
	 * @param personId  人员id
	 * @param startTime  起始时间
	 * @param endTime   结束时间
	 * @param areaCodes 地区码集合
	 * @return
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getPersonTrajectory")
	@Results({
			@Result(property = "personId", column = "person_id"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "createTime", column = "image_time"),
			@Result(property = "cameraName", column = "name"),
			@Result(property = "longitude", column = "longitude"),
			@Result(property = "latitude", column = "latitude")
	})
	List<ClusterBrowseDTO> getPersonTrajectory(@Param("personId")String personId, @Param("startTime")String startTime,
											   @Param("endTime")String endTime, @Param("areaCodes") List<String> areaCodes);

	/**
	 * 分页查询某些地区在某个时间段内出现的所有人员
	 * @param startTime 起始时间
	 * @param endTime    结束时间
	 * @param areaCodes  地区码集合
	 * @return
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getPersonByTimeAndArea")
	@Results({
			@Result(property = "personId", column = "person_id"),
			@Result(property = "imagePath", column = "image_path"),
			@Result(property = "count", column = "count"),
			@Result(property = "cameraName", column = "name"),
			@Result(property = "createTime", column = "image_time"),
			@Result(property = "number", column = "number")

	})
	List<ClusterAdvanceDTO> getPersonByTimeAndArea(@Param("startTime")String startTime,
												   @Param("endTime")String endTime, @Param("areaCodes") List<String> areaCodes);

	/** 查询聚类总数 */
	@Select("select count(personid) from jh_person")
	Integer getAllClusterCount();
	/** 查询今日聚类数 */
	@Select("SELECT count(personid) FROM jh_person WHERE createtime >= DATE_FORMAT(CURDATE(),'%Y-%m-%d %H:%i:%s') AND createtime <= now();")
	Integer getDayCount();

	@Select("SELECT MAX(id) FROM jh_cluster_pic")
	BigInteger getMaxId();

	/**
	 * @Des 陌生人查询 （第一次获取cluster_pic表的最大id，一分钟后再获取该表的最大id，获取两个id之间的数据 并大于最小id的时间筛选数据）
	 * 修改人: zwc
	 * 修改时间: 2019/1/14
	 * @return
	 */
	@SelectProvider(type = ClusterImgMapperProvider.class, method = "realTimeStranger")
	@Results({ @Result(property = "personId", column = "clusterid"),
			@Result(property = "imagePath", column = "picpath"),
			@Result(property = "createTime", column = "createtime"),
			@Result(property = "longitude", column = "x"),
			@Result(property = "latitude", column = "y"),
			@Result(property = "cameraName", column = "cameraname") })
	List<Stranger> realTimeStranger(@Param("firstId")BigInteger firstId,@Param("lastId")BigInteger lastId,@Param("cameraList") List<CameraInfo> cameraList);
}
