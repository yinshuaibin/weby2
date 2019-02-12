package com.ferret.dao;

import com.ferret.bean.CameraInfo;
import com.ferret.dao.provider.CameraInfoMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CameraInfoMapper {
	/**
	 * 查询摄像所对应的摄像分组
	 * 0124  y   增加场所关联查询
	 * 
	 * @param groupId
	 *            对应摄像头分组id
	 * @return
	 */
	@Select("SELECT a.*, b. NAME placeName FROM ( SELECT * FROM jh_camera WHERE groupid = #{groupId} AND `status` = 1 ) a LEFT JOIN tb_dictionary b ON a.mark_dictionary = b.number ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "cameraId", column = "cameraid"),
            @Result(property = "name", column = "cameraname"),
            @Result(property = "ip", column = "cameraip"),
            @Result(property = "rtspUrl", column = "rtspurl"),
            @Result(property = "manufacturer", column = "type"),
            @Result(property = "enabled", column = "status"),
            @Result(property = "address", column = "address"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "pwd"),
            @Result(property = "devicePort", column = "port"),
            @Result(property = "placeId", column = "mark_dictionary"),
            @Result(property = "longitude", column = "x"),
            @Result(property = "latitude", column = "y"),
            @Result(property = "netPort", column = "frontgatewayid"),
            @Result(property = "isVirtual", column = "isvirtual"),
            @Result(property = "createTime", column = "createtime"),
            @Result(property = "description", column = "remark"),
            @Result(property = "groupId", column = "groupid"),
            @Result(property = "direction", column = "direction"),
            @Result(property = "number", column = "number")})
	List<CameraInfo> findByCameraInfoByNumber(@Param("groupId") String groupId);

	/**
	 * 增加摄像数据,默认可用
	 * y  0124 清除了多余的符号
	 * @param cameraInfo
	 * @return
	 */
	@Insert("INSERT INTO jh_camera(cameraid, cameraname,cameraip,rtspurl,type,status,"
			+ "address,username, pwd,port,areaid, x,y, frontgatewayid,isvirtual,createtime,remark,groupid,direction,mark_dictionary,number)"
            + "VALUES(#{cameraId},#{name}, #{ip},#{rtspUrl},#{manufacturer},#{enabled}, #{address},#{username},#{password},"
			+ "#{devicePort},#{id},#{longitude},#{latitude}, #{netPort},#{isVirtual},#{addTime},#{description}, #{groupId},#{direction},#{placeId},#{number})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertCameraInfo(CameraInfo cameraInfo);

	/**
	 * 根据name查看相机
	 * @param name
	 * @return
	 * 0124  y   增加场所关联查询
	 */
    @Select(" SELECT a.*, b. NAME placeName FROM( SELECT * FROM jh_camera WHERE cameraname = #{name})a LEFT JOIN tb_dictionary b ON a.mark_dictionary = b.number ")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "cameraId", column = "cameraid"),
			@Result(property = "name", column = "cameraname"),
			@Result(property = "ip", column = "cameraip"),
			@Result(property = "rtspUrl", column = "rtspurl"),
			@Result(property = "manufacturer", column = "type"),
			@Result(property = "enabled", column = "status"),
			@Result(property = "address", column = "address"),
			@Result(property = "username", column = "username"),
			@Result(property = "password", column = "pwd"),
			@Result(property = "devicePort", column = "port"),
			@Result(property = "placeId", column = "mark_dictionary"),
			@Result(property = "longitude", column = "x"),
			@Result(property = "latitude", column = "y"),
			@Result(property = "netPort", column = "frontgatewayid"),
			@Result(property = "isVirtual", column = "isvirtual"),
			@Result(property = "createTime", column = "createtime"),
			@Result(property = "description", column = "remark"),
			@Result(property = "groupId", column = "groupid"),
			@Result(property = "direction", column = "direction"),
			@Result(property = "number", column = "number")})
    List<CameraInfo> selectCameraName(@Param("name") String name);

	/**
	 * 根据ip查看相机
	 * @param ip
	 * @return
	 * 0124  y   增加场所关联查询
	 */
	@Select("SELECT a.*, b. NAME placeName FROM( SELECT * FROM jh_camera WHERE cameraip=#{ip})a LEFT JOIN tb_dictionary b ON a.mark_dictionary = b.number")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "cameraId", column = "cameraid"),
            @Result(property = "name", column = "cameraname"),
            @Result(property = "ip", column = "cameraip"),
            @Result(property = "rtspUrl", column = "rtspurl"),
            @Result(property = "manufacturer", column = "type"),
            @Result(property = "enabled", column = "status"),
            @Result(property = "address", column = "address"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "pwd"),
            @Result(property = "devicePort", column = "port"),
            @Result(property = "placeId", column = "mark_dictionary"),
            @Result(property = "longitude", column = "x"),
            @Result(property = "latitude", column = "y"),
            @Result(property = "netPort", column = "frontgatewayid"),
            @Result(property = "isVirtual", column = "isvirtual"),
            @Result(property = "createTime", column = "createtime"),
            @Result(property = "description", column = "remark"),
            @Result(property = "groupId", column = "groupid"),
            @Result(property = "direction", column = "direction"),
            @Result(property = "number", column = "number")})
    List<CameraInfo> selectCameraIp(@Param("ip") String ip);

	/**
	 * 查看db_realtimeimage中相机ip的条数
	 * @param ip
	 * @return
	 */
	@Select("SELECT COUNT(cameraip) from jh_cluster_pic WHERE cameraip=#{ip}")
	int selectCameraIpFrom(@Param("ip") String ip);

	/**
	 * 修改摄像数据s
	 * 
	 * @param cameraInfo
	 * @return
	 */
	@Update("Update jh_camera SET cameraid=#{cameraId},cameraname=#{name},"
			+ "cameraip=#{ip},rtspurl=#{rtspUrl},type=#{manufacturer},status=#{enabled},"
			+ "address=#{address},username=#{username},pwd=#{password},"
			+ "port=#{devicePort},mark_dictionary=#{placeId},x=#{longitude},y=#{latitude},"
			+ "frontgatewayid=#{netPort},isvirtual=#{isVirtual},createtime=#{createTime},"
			+ "remark=#{description},groupid=#{groupId},direction=#{direction},number=#{number} "
            + "where id=#{id}")
	int updateCameraInfo(CameraInfo cameraInfo);

	/**
	 * 删除摄像数据
	 * 
	 * @param id
	 * @return
	 */
	@Delete("DELETE FROM jh_camera WHERE id=#{id}")
	int deleteCameraInfo(@Param("id") Integer id);
	/**
	 * 根据id查询
	 *0124  y   增加场所关联查询
	 * @param id
	 * @return
	 */
    @Select("SELECT a.*, b. NAME placeName FROM( SELECT * FROM jh_camera WHERE id = #{id})a LEFT JOIN tb_dictionary b ON a.mark_dictionary = b.number")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "cameraId", column = "cameraid"),
            @Result(property = "name", column = "cameraname"),
            @Result(property = "ip", column = "cameraip"),
            @Result(property = "rtspUrl", column = "rtspurl"),
            @Result(property = "manufacturer", column = "type"),
            @Result(property = "enabled", column = "status"),
            @Result(property = "address", column = "address"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "pwd"),
            @Result(property = "devicePort", column = "port"),
            @Result(property = "placeId", column = "mark_dictionary"),
            @Result(property = "longitude", column = "x"),
            @Result(property = "latitude", column = "y"),
            @Result(property = "netPort", column = "frontgatewayid"),
            @Result(property = "isVirtual", column = "isvirtual"),
            @Result(property = "createTime", column = "createtime"),
            @Result(property = "description", column = "remark"),
            @Result(property = "groupId", column = "groupid"),
            @Result(property = "direction", column = "direction"),
            @Result(property = "number", column = "number")})
	CameraInfo findById(@Param("id") Integer id);

	/**
	 * 查询总条数
	 * 
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM jh_camera")
	int findCount();

	/**
	 * 根据相机number查id
	 */
	@SelectProvider(type = CameraInfoMapperProvider.class, method = "number2Id")
	List<Integer> number2Id(@Param("numbers") String numbers, @Param("like") String like);


	/**
	 * 根据角色id查询对应的相机
	 * 0124  y   增加场所关联查询
	 * @param roleId
	 * @return
	 */
	List<CameraInfo> listCamerasByRoleId(List<String> roleId);


	/**
	 * 以下为将旧的camerainfo数据查出并修改的操作
	 */

	@Select("SELECT current_value FROM sequence WHERE name='carmeraOld'")
	int selectSquences();


	/**
	 * 根据页面传递过来的相机列表数据,查询对应的相机信息
	 * @param selectCamera 勾选以后,页面传递过来的相机选择后的集合
	 * @return List<String>
	 *     0124  y   增加场所关联查询
	 */
	@SelectProvider(type = CameraInfoMapperProvider.class, method = "findCameraINfoBySelect")
	@Results({
			@Result(property = "ip",column = "cameraip"),
			@Result(property = "name",column = "cameraname")
	})
	List<CameraInfo> findCameraINfoBySelect(@Param("selectCamera") List<String> selectCamera);
	/**
	 * @Description 根据cameraId查询相机信息
	 * @param cameraId
	 * @date 2019-01-02 15:10:37
	 * @author xieyingchao
	 * 0124  y   增加场所关联查询
	 */
	@Select("SELECT a.*, b. NAME placeName FROM(SELECT * FROM jh_camera WHERE cameraid = #{cameraId} )a LEFT JOIN tb_dictionary b ON a.mark_dictionary = b.number")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "cameraId", column = "cameraid"),
			@Result(property = "name", column = "cameraname"),
			@Result(property = "ip", column = "cameraip"),
			@Result(property = "rtspUrl", column = "rtspurl"),
			@Result(property = "manufacturer", column = "type"),
			@Result(property = "enabled", column = "status"),
			@Result(property = "address", column = "address"),
			@Result(property = "username", column = "username"),
			@Result(property = "password", column = "pwd"),
			@Result(property = "devicePort", column = "port"),
			@Result(property = "placeId", column = "mark_dictionary"),
			@Result(property = "longitude", column = "x"),
			@Result(property = "latitude", column = "y"),
			@Result(property = "netPort", column = "frontgatewayid"),
			@Result(property = "isVirtual", column = "isvirtual"),
			@Result(property = "createTime", column = "createtime"),
			@Result(property = "description", column = "remark"),
			@Result(property = "groupId", column = "groupid"),
			@Result(property = "direction", column = "direction"),
			@Result(property = "number", column = "number")})
	CameraInfo getCameraInfo (@Param("cameraId") String cameraId);

	@Select("SELECT nextval('cameraMaxNum')")
    int addCameraMaxNum();

	@Select("SELECT count(id) from jh_camera")
	int addedCameraNum();


	@Select("SELECT count(id) FROM jh_camera WHERE mark_dictionary = ( SELECT number FROM tb_dictionary WHERE id = #{id} )")
	int findCountByCameraPlaceId(int id);
}