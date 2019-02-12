package com.ferret.dao;

import com.ferret.bean.ClusterPass;
import com.ferret.bean.ClusterSearch;
import com.ferret.dao.provider.ClusterImgMapperProvider;
import com.ferret.dto.ClusterBrowseDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
/**
 * 修改人:y
 * 修改时见:0716
 * @author Administrator
 *
 */
@Mapper
@Repository
public interface ClusterDateMapper {
	/**
	 * 根据时间段查询出行人
	 * @param startTime 开始时间
	 * @param endTime	结束时间
	 * @param startNum  开始条数
	 * @return
	 */
	@Select("call cluster_date(#{startTime},#{endTime},#{startNum},#{minNum})")
	@Options(statementType = StatementType.CALLABLE)
	@Results({ 	@Result(property="personId",column="person_id"),
			@Result(property="createTime",column="create_time"),
			@Result(property="imagePath",column="image_path"),
			@Result(property="cameraName",column="name")
	})
	List<ClusterBrowseDTO> findClusterByTime(@Param("startTime") String startTime,
											 @Param("endTime") String endTime, @Param("startNum") Integer startNum, @Param("minNum") Integer minNum);

	/**
	 * 通过时间段,查询档案总条数
	 * 修改人:y
	 * 修改时间: 0116
	 * 修改原因:使用新表 tb_clusterid_count
	 * @param startTime
	 * @param endTime
	 * @param minNum
	 * @return
	 */
	//@Select("SELECT count(person_id) FROM tb_cluster_pass_count WHERE create_time>=#{startTime} AND create_time<=#{endTime} AND count>=#{minNum};")
	@Select("SELECT count(*) FROM ( SELECT sum(c.count) count FROM tb_clusterid_count c, ( SELECT a.clusterid, b.personid FROM jh_cluster a, jh_person b\n" +
			"WHERE b.createtime >= #{startTime} AND b.createtime <= #{endTime} AND a.personid = b.personid ) d WHERE c.clusterid = d.clusterid GROUP BY d.personid HAVING count >= #{minNum} ) a")
	Integer findTotalByDate(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("minNum")int minNum);


	/**
	 * 根据person_id查询tb_cluster_pass对应的所有数据  分页
	 * 修改人:y
	 * 修改时间:0716
	 *
	 * 修改人:y
	 * 修改时间:0719
	 * 修改内容:按照时间倒叙排列
	 * @param personId
	 * @param startNum
	 * @param pageSize
	 * @return
	 */
	@Select("SELECT tc.person_id,tc.image_path,tc.start_time,tb.cameraname,tb.x,tb.y,left(tb.groupid, 6) number "
			+ "from tb_cluster_pass tc LEFT JOIN jh_camera tb on tc.camera_ip=tb.cameraip WHERE tc.person_id=#{person_Id} order by tc.start_time desc limit #{startNum},#{pageSize}")
	@Results({
			@Result(property="personId",column="person_id"),
			@Result(property="imagePath",column="image_path"),
			@Result(property="cameraName",column="cameraname"),
			@Result(property="createTime",column="start_time"),
			@Result(property="longitude",column="x"),
			@Result(property="latitude",column="y"),
			@Result(property = "number", column = "number")})
	List<ClusterBrowseDTO> findClusterByPersonId(@Param("person_Id")String personId, @Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize);

	/**
	 * @Descriptions 根据person_id查询tb_cluster_pass最近一周的抓拍数据
	 * @author xyc
	 * @param personId
	 * @return
	 */
	@Select("SELECT tc.person_id,tc.image_path,tc.start_time,tb.`name`,tb.longitude,tb.latitude \n" +
			"from tb_cluster_pass tc LEFT JOIN tb_camera_info tb on tc.camera_ip=tb.ip \n" +
			"WHERE tc.person_id = #{person_Id} order by tc.start_time desc LIMIT 0,10")
	@Results({
			@Result(property="personId",column="person_id"),
			@Result(property="imagePath",column="image_path"),
			@Result(property="cameraName",column="name"),
			@Result(property="createTime",column="start_time"),
			@Result(property="longitude",column="longitude"),
			@Result(property="latitude",column="latitude"),
	})
	List<ClusterPass> findClusterByPersonIdAndTime(@Param("person_Id")String personId);

	/**
	 * 根据person_id查询tb_cluster_pass对应的所有数据总条数
	 * 修改人:y
	 * 修改时间:0716
	 * @return
	 */
	@Select("SELECT count(person_id) FROM tb_cluster_pass where person_id = #{personId}")
	Integer findTotalByPersonId(@Param("personId") String personId);

	//通过ip 查询cameraName,经纬度
	@Select("SELECT name,longitude,latitude FROM tb_camera_info where ip = #{ip}")
	@Results({
			@Result(property="cameraName",column="name"),
			@Result(property="longitude",column="longitude"),
			@Result(property="latitude",column="latitude")
	})
	ClusterPass getCameraName(@Param("ip")String ip);

	@Select("SELECT\r\n" +
			"	tc.person_id,tc.create_time,tc.update_time,\r\n" +
			"	tc.idcard,tc. NAME,tc.represent_img1,tc.represent_img2,\r\n" +
			"	tc.represent_img3,tc.represent_img4,tc.represent_img5\r\n" +
			"FROM tb_cluster tc where person_id = #{perSid}")
	@Results({
			@Result(property="personId",column="person_id"),
			@Result(property="create_time",column="create_time"),
			@Result(property="updatetime",column="update_time"),
			@Result(property="idcard",column="idcard"),
			@Result(property="name",column="name"),
			@Result(property="imagePath",column="represent_img1"),
			@Result(property="represent_img2",column="represent_img2"),
			@Result(property="represent_img3",column="represent_img3"),
			@Result(property="represent_img4",column="represent_img4"),
			@Result(property="represent_img5",column="represent_img5")
	})
	ClusterSearch findClusterByPerId(@Param("perSid")String perSid);

	@Select("SELECT cardnumber,name,representimgpath,cardnumberpicpath from jh_person WHERE personid = #{personId} ")
	@Results({
			@Result(property="imagePath",column="representimgpath")
	})
	ClusterBrowseDTO getPhotoByPersonId(String personId);

	/**
	 * @Description 根据personId 查询身份证信息
	 * @param PersonId 
	 * @date 2019-01-14 14:17:25
	 * @author xieyingchao
	 */
    @Select("SELECT personid, cardnumber,name,cardnumberpicpath from jh_person WHERE personid = #{personId} ")
    @Results({
			@Result(property="personId",column="personid"),
            @Result(property="idcard",column="cardnumber"),
            @Result(property="name",column="name"),
            @Result(property="imagePath",column="cardnumberpicpath")})
	ClusterBrowseDTO getPersonInfoByPersonId(String PersonId);

	@Select("SELECT image_path from tb_cluster_image WHERE person_id = #{personId} LIMIT 1")
	@Results({
			@Result(property="represent_img1",column="image_path"),
	})
	ClusterPass getPhoto_clusterImg(String personId);

	// 查询jh_person表获取 代表图
	@Select("SELECT a.personid,a.representimgpath,a.createtime,b.count from jh_person a LEFT JOIN tb_cluster_pass_count b ON a.personid = b.person_id WHERE a.personid = #{personId}")
	@Results({
			@Result(property="personId",column="personid"),
			@Result(property="imagePath",column="representimgpath"),
			@Result(property="createTime",column="createtime"),
			@Result(property="count",column="count")
	})
	List<ClusterBrowseDTO> findPersonById(@Param("personId") String personId);

	// 通过clusterid查询对应的jh_person信息表的数据
	/*@Select("SELECT a.clusterid,a.personid,a.representimgpath,a.cardnumberpicpath,a.cardnumber,a.name,a.createtime,count(a.personid) count " +
			"from (SELECT c.clusterid,c.personid,p.representimgpath,p.cardnumberpicpath,p.cardnumber,p.name,p.createtime from jh_cluster c,jh_person p " +
			"WHERE c.clusterid = #{clusterid} AND c.personid = p.personid) a,tb_cluster_pass b WHERE a.personid = b.person_id GROUP BY a.personid")*/
	// 因jh_person表中无代表图，修改为取jh_cluster_pic表第一张代表图
	@Select("SELECT a.clusterid,a.personid,cp.picpath representimgpath,a.cardnumberpicpath,a.cardnumber,a.name,a.createtime,count(a.personid) count " +
			"from (SELECT c.clusterid,c.personid,p.representimgpath,p.cardnumberpicpath,p.cardnumber,p.name,p.createtime from jh_cluster c,jh_person p " +
			"WHERE c.clusterid = '9860' AND c.personid = p.personid) a,tb_cluster_pass b,jh_cluster_pic cp WHERE a.personid = b.person_id AND a.clusterid = cp.clusterid GROUP BY a.personid")
	@Results({
			@Result(property="personId",column="personid"),
			@Result(property="imagePath",column="representimgpath"),
			@Result(property="createTime",column="createtime"),
			@Result(property="count",column="count"),
            @Result(property="idcard",column="cardnumber")
	})
	List<ClusterBrowseDTO> findPersonByClusterId(@Param("clusterid") String personId);

	/**
	 * 通过clusterid查询对应的personid，在通过personid查询对应的所有clusterid
	 * @param clusterid
	 * @return
	 */
	@Select("SELECT p.clusterids from jh_cluster c,jh_person p WHERE c.clusterid = #{clusterid} AND c.personid = p.personid")
	String findClusterids(@Param("clusterid") String clusterid);

	@SelectProvider(type = ClusterImgMapperProvider.class, method = "getPersonByClusterids")
	@Results({
			@Result(property="personId",column="personid"),
			@Result(property="imagePath",column="picpath"),
			@Result(property="count",column="count"),
	})
	List<ClusterBrowseDTO> getPersonByClusterids(@Param("clusterids") ArrayList<String> arrayList);
}
