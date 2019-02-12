package com.ferret.dao;

import com.ferret.bean.AlarmInterface;
import com.ferret.bean.RealTimeAlarm;
import com.ferret.bean.bukong.NewBuKong;
import com.ferret.dao.provider.RealTimeAlarmMapperProvider;
import com.ferret.dto.AlarmQueryDTO;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RealTimeAlarmMapper {

	int deleteByPrimaryKey(Integer alarmId);


	RealTimeAlarm selectByPrimaryKey(Integer alarmId);

	int updateByPrimaryKeySelective(RealTimeAlarm record);

	int updateByPrimaryKey(RealTimeAlarm record);

	/**
	 * 查询历史报警信息
	 * 
	 * @return
	 */
	@Select("Select * from tb_realtime_alarm")
	List<RealTimeAlarm> findAllAlarm();

	/**
	 * 根据报警时间、布控groupID、布控reason,摄像ID信息查询
	 * 
	 * @return //
	 */

	@SelectProvider(type = RealTimeAlarmMapperProvider.class, method = "findByBk")
	List<RealTimeAlarm> findByBk(@Param("nums") List<String> nums, @Param("bkId") Integer bkId,
			@Param("alarmTime") Date begintime, @Param("alarmTime") Date endtime, @Param("groupId") String groupId,
			@Param("reason") String reason);
	@SelectProvider(type = RealTimeAlarmMapperProvider.class, method = "alarmQuery")
	List<RealTimeAlarm> alarmQuery(Map map);

	@SelectProvider(type = RealTimeAlarmMapperProvider.class, method = "alarmQueryCount")
	@Results({ @Result(property = "dataCount", column = "dataCount") })
	Integer alarmQueryCount(Map map);

	/**
	 * 更新数据sampleFlag
	 *
	 * @param sampleFlag
	 *            标志
	 * @return
	 */

	@Update("UPDATE tb_realtime_alarm SET sample_flag = #{sampleFlag} WHERE alarm_id=#{alarmId}")
	int updateBySampleFlag(@Param("alarmId") Integer alarmId, @Param("sampleFlag") Integer sampleFlag);

	@Update("UPDATE jh_alarm SET status = 1 WHERE id=#{id}")
	int updateByAlarmId(@Param("id") BigInteger id);

	/**
	 * 更新数据status
	 *
	 * @param status
	 * @param confirmTime
	 * @param userId
	 * @return
	 */
	@Update("update tb_realtime_alarm " + "set alarm_id=#{alarmId},bk_id=#{bkId},"
			+ "bk_image_path=#{bkImagePath},alarm_image_path=#{alarmImagePath},"
			+ "alarm_time=#{alarmTime},camera_id=#{cameraId},similar=#{similar},"
			+ "background_image_id=#{backgroundImageId},video_id=#{videoId},spare=#{spare},"
			+ "status=#{status},confirm_time=#{confirmTime},user_id=#{userId},sample_flag=#{sampleFlag}"
			+ "where status=#{status},confirm_time=#{confirmTime}," + "user_id=#{userId},sample_flag=#{sampleFlag}")

	int updateByStatus(@Param("status") Integer status, @Param("confirmTime") Date confirmTime,
			@Param("userId") Integer userId, @Param("sampleFlag") Integer sampleFlag);

	/**
	 * 根据报警id查询报警相关的所有信息
	 *
	 * @param id
	 *            报警id
	 * @return
	 */
	@Select("SELECT ra.alarm_id as alarm_id,ra.bk_id,ra.bk_image_path,ra.alarm_image_path, ra.alarm_time,ra.camera_id, ra.similar, ra.status"
			+ ", ra.sample_flag, ra.background_image_id, bk.reason,ci.name, ra.fid_dy, ra.fid_bk "
			+ " FROM tb_realtime_alarm ra " + " LEFT JOIN tb_bukong bk ON ra.bk_id = bk.bk_id "
			+ " LEFT JOIN tb_camera_info ci ON ra.camera_id = ci.id " + " WHERE ra.alarm_id = #{alarm_id}")
	@Results({ @Result(column = "alarm_id", property = "alarmId"), @Result(column = "bk_id", property = "bkId"),
			@Result(column = "bk_image_path", property = "bkImagePath"),
			@Result(column = "alarm_image_path", property = "alarmImagePath"),
			@Result(column = "alarm_time", property = "alarmTime"),
			@Result(column = "camera_id", property = "cameraId"),
			@Result(column = "similar", property = "similar"),
			@Result(column = "status", property = "status"),
			@Result(column = "sample_flag", property = "sampleFlag"),
			@Result(column = "background_image_id", property = "backgroundImageId"),
			@Result(column = "reason", property = "reason"),
			@Result(column = "name", property = "cameraName"),
			@Result(column = "fid_dy", property = "fidDY"),
			@Result(column = "fid_bk", property = "fidBK")
	})
	RealTimeAlarm getAlarmById(@Param("alarm_id") Integer id);

	List<RealTimeAlarm> listAlarmsByBkId(Integer bkId);

	List<RealTimeAlarm> listByQueryDTO(AlarmQueryDTO alarmQueryDTO);

	List<Map<Integer, Integer>> listBKIdsByQueryDTO(AlarmQueryDTO alarmQueryDTO);

	/**
	 * @Description 根据报警信息表的id查询报警人的所有信息
	 * @param id    jh_alarm中的主键 id
	 * @date 2019-01-04 13:25:29
	 * @author xieyingchao
	 */
	@Select("SELECT a.controlpeopleid,a.snappicbgpath, b.name,b.conreason,b.cardnumber,b.createtime, c.typename bkGroupName \n" +
			"FROM jh_alarm a,jh_controlpeople b,jh_controlpeople_type c \n" +
			"WHERE a.id = #{id} AND a.controlpeopleid = b.id AND c.id = b.contorltype")
	@Results({ @Result(property = "idcard", column = "cardnumber"),
            @Result(property = "name", column = "name"),
            @Result(property = "reason", column = "conreason"),
            @Result(property = "onTime", column = "createtime"),
			@Result(property = "imagePath", column = "controlpeopleid", many = @Many(select = "com.ferret.dao.BuKongImageMapper.findBkImgByBkId")) })
	NewBuKong findBKbyRealTimeAlarmId(BigInteger id);
	
	/**
	 * 根据报警id,报警时间(分钟)查询该布控对象这一分钟内的所有报警信息
	 * @param id
	 * @param alarmTime
	 * @return
	 */
	@Select("SELECT peoplepicpath,snappicpath,createtime,score from jh_alarm  where controlpeopleid = (SELECT controlpeopleid FROM jh_alarm WHERE id = #{id}) AND createtime LIKE #{alarmTime}")
    @Results({ @Result(column = "peoplepicpath", property = "peoplepicpath"),
            @Result(column = "snappicpath", property = "alarmImagePath"),
            @Result(column = "createtime", property = "alarmTime"),
            @Result(column = "score", property = "similar")
            })
	List<RealTimeAlarm> alarmByMinuteTime(@Param("id")BigInteger id,@Param("alarmTime")String alarmTime);

	/**
	 * 根据前台传递过来的websocket接受的报警信息中的报警id查询bk信息,并返回最近的alarmSize条报警信息
	 * (websocket有报警信息返回时,调用,调用页面:main.vue)
	 *  此处使用了报警表中的布控图片,因为不需要关联查询布控表,所以在service层对于图片路径的处理会和getReamTimeAlarmByAlarmId不同
	 *  y  0108
	 * @param id  报警表中的自增id, 不是报警id
     * @param alarmSize 要返回几条报警信息
	 * @return
	 */
	@Select("SELECT a.*, b.cameraname, b.y latitude, b.x longitude,c.conreason reason FROM ( SELECT id, cameraid, controlpeopleid, NAME, sex, cardnumber idCard, createtime alarm_time, score similar, `status`," +
			"snappicpath alarm_image_path, peoplepicpath bk_image_path FROM jh_alarm WHERE controlpeopleid = ( SELECT controlpeopleid FROM jh_alarm WHERE alarmid = #{alarm_id} ) " +
			"ORDER BY alarm_time DESC LIMIT #{alarmSize} ) a LEFT JOIN jh_camera b ON a.cameraid = b.cameraid LEFT JOIN jh_controlpeople c ON a.controlpeopleid = c.id ORDER BY a.alarm_time DESC")
	List<RealTimeAlarm> findAlarmById(@Param("alarm_id") String id,@Param("alarmSize") Integer alarmSize);

	/**
	 * 查询数据库中最近报警的5个人的最新5条报警记录  y 0808
	 * @param alarmIds 报警id的集合
	 * @param alarmSize 最近的几条报警信息
	 * @return
	 */
	@SelectProvider(type = RealTimeAlarmMapperProvider.class,method = "findPeopleRealTimeAlarm")
	List<RealTimeAlarm> findPeopleRealTimeAlarm(@Param("alarmIds") List<String> alarmIds,@Param("alarmSize") Integer alarmSize);

	/**
	 * 查询数据库中最近报警的size个人的最新报警的alarm_id y 0108
	 * @param  size 查询的人数
	 * @return
	 */
	@Select("SELECT GROUP_CONCAT( id ORDER BY createtime DESC ) str, substring_index( GROUP_CONCAT( id ORDER BY createtime DESC), ',', 1 ) + 0 str1 FROM jh_alarm GROUP BY controlpeopleid ORDER BY str1 DESC limit 0,#{size}")
	List<String> findAlarmId(Integer size);


	@Select("SELECT a.bk_group_name type,b.name,b.idcard FROM " +
            "(SELECT bk_group_name FROM tb_bukong_group WHERE " +
            "bk_group_id =  (SELECT bk_group_id FROM tb_bukong WHERE bk_id = #{bkId})) a," +
            "(SELECT name,idcard FROM tb_bukong WHERE bk_id = #{bkId}) b")
    @Results({ @Result(column = "type", property = "type"),
            @Result(column = "name", property = "name"),
            @Result(column = "idcard", property = "idcard")
    })
	AlarmInterface findAlarmBk(@Param("bkId") Integer bkId);

	/**
	 * 根据时间段查询报警总数   y 1220
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@Select("SELECT count(alarm_id) FROM tb_realtime_alarm WHERE alarm_time >= #{startTime} AND alarm_time <= #{endTime}")
	int getAlarmCountByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

	/**
	 * 根据时间段和布控分组id查询报警总数   y 1220
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@Select("SELECT count(alarm_id) FROM tb_realtime_alarm WHERE alarm_time >= #{startTime} AND alarm_time <= #{endTime} AND bk_id IN ( SELECT bk_id FROM tb_bukong WHERE bk_group_id = #{bkGroupId})")
	int getAlarmCountByTimeAndBkGroupId(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("bkGroupId") String bkGroupId);

	/**
	 * 分页查询某个布控人的某段时间的报警信息 y1220
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param bkId      布控id
	 * @return
	 */
	@Select("SELECT * FROM tb_realtime_alarm WHERE alarm_time >= #{startTime} AND alarm_time <= #{endTime} AND bk_id =#{bkId} limit #{startNum},#{pageSize}")
	List<RealTimeAlarm> findAlarmByBukongId(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("bkId") Integer bkId,@Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize);

	/**
	 * 查询某个布控人的某段时间的报警信息总条数 y1221
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param bkId      布控id
	 * @return
	 */
	@Select("SELECT count(*) FROM tb_realtime_alarm WHERE alarm_time >= #{startTime} AND alarm_time <= #{endTime} AND bk_id =#{bkId}")
	Integer findAlarmByBukongIdCount(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("bkId") Integer bkId);

	/**
	 * 查询某些相机某个时间段下所有的报警记录 y 1220
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param cameraIds  相机id集合
	 * @param startNum 开始条数
	 * @param pageSize 每页记录数
	 * @return
	 */
	@SelectProvider(type = RealTimeAlarmMapperProvider.class,method = "findAlarmByCameraIds")
	List<RealTimeAlarm> findAlarmByCameraIds(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("cameraIds") List<String> cameraIds,@Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize);

	/**
	 * 查询某些相机某个时间段下所有的报警记录总条数 y 1221
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param cameraIds  相机id集合
	 * @return
	 */
	@SelectProvider(type = RealTimeAlarmMapperProvider.class,method = "findAlarmByCameraIdsCount")
	Integer findAlarmByCameraIdsCount(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("cameraIds") List<String> cameraIds);

	/**
	 * 根据报警id查询对应的报警信息  y 0105
	 * @param alarmId 报警id
	 *         在此处使用了关联查询出来的布控表中的图片路径作为布控图片路径,没有使用报警表中的图片路径
	 * @return
	 */
	@Select("SELECT ra.id, ra.alarmid, ra.controlpeopleid, ra.controltypeid, ra. NAME, ra.sex, ra.cardnumber, ra.alarmpicid, ra. STATUS, ra.handletype, ra.handleremark, ra.score, " +
			"ra.createtime AS alarmTime, ra.managerid,ra.managername,  managetime, ra.snappicbgpath, ra.snappicpath, ra.cameraid, " +
			"ra.peoplepicpath, ra.arcscore, ra.projectid, ra.remark, ci.cameraname, ci.x longitude, ci.y latitude, bk.picpath, bk.conreason reason, bk. NAME AS bkName, bk.cardnumber AS idCard " +
			"FROM ( SELECT id, alarmid, controlpeopleid, controltypeid, NAME, sex, cardnumber, alarmpicid, cameraid, STATUS, handletype, handleremark, score, createtime, managerid, " +
			"managername, managetime, snappicpath, snappicbgpath, peoplepicpath, arcscore, projectid, remark FROM jh_alarm WHERE alarmid = #{alarmId}" +
			") ra LEFT JOIN jh_camera ci ON ra.cameraid = ci.cameraid LEFT JOIN jh_controlpeople bk ON ra.controlpeopleid = bk.id")
	@Results({ @Result(column = "picpath", property = "bkImagePath"),
			@Result(column = "snappicpath", property = "alarmImagePath"),
			@Result(column = "snappicbgpath", property = "backgroundImagePath"),
			@Result(column = "score", property = "similar")
	})
	RealTimeAlarm getReamTimeAlarmByAlarmId(BigInteger alarmId);

	/**
	 * 根据布控的businessid查询该布控人员有多少条报警信息
	 * @param businessid  y  0119
	 * @return
	 */
	@Select("select count(a.id) from jh_alarm a, jh_controlpeople b where a.controlpeopleid = b.id and b.businessid = #{businessid}")
	int findAlarmCountByBukongBusinessid(String businessid);

	/**
	 * 根据给定的布控id, 查询报警表,筛选出来报警过的布控id
	 * @param ids   y 0119
	 * @return
	 */
	@SelectProvider(type = RealTimeAlarmMapperProvider.class,method = "findAlarmBuKongIds")
	List<String> findAlarmBuKongIds(List<String> ids);
}