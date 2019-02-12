package com.ferret.dao;

import com.ferret.bean.Bigdata;
import com.ferret.bean.PersonNumByCameraIp;
import com.ferret.dao.provider.BigDateConditionalMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BigDateMapper {

	//查询今年当月（本月） 总人数    select *  from tb_cluster where create_time >= '2018-04-01';(每月1号 ,时间都设为00:00:00)
	 @Select("select sum(c_count)  from tb_bigdata where c_starttime >= #{currentMonthFirstDay} and c_count_name = '日新进人数' ")
	Integer findCurrentMonthTotal(@Param("currentMonthFirstDay")String currentMonthFirstDay);
	//查询昨天新进人数    (数据库 tb_bigdata 只存放到昨天的数据,每天按时增加昨天新进人数)
	 @Select("select c_count  from tb_bigdata where c_starttime >= #{lastDay} and c_endtime = #{thisDay} and c_count_name = '日新进人数'")
	Integer findLastDayPeople(@Param("lastDay")String lastDay,@Param("thisDay")String thisDay);
	
	 //查询今天新进人数    select *  from tb_cluster where create_time >= '2018-04-07';(今天日期，时间都设为00:00:00)
	 @Select("select count(*)  from jh_person where createtime >= #{currentDay}")
	Integer findthisDayPeople(@Param("currentDay")String currentDay);
	
	 //查询今年上月 总人数    
	@Select("select sum(c_count) from tb_bigdata where c_starttime >= #{lastMonthFirstDay} and c_endtime < #{thisMonthFirstDay} and c_count_name = '日新进人数'")
	Integer findlastMonthTotal(@Param("lastMonthFirstDay")String lastMonthFirstDay,@Param("thisMonthFirstDay")String thisMonthFirstDay);
	//查询今年上周 总人数    
	@Select("select sum(c_count) from tb_bigdata where c_starttime >= #{lastweekFirstDay} and c_endtime < #{thisweekFirstDay} and c_count_name = '日新进人数'")
	Integer findlastweekTotal(@Param("lastweekFirstDay")String lastweekFirstDay,@Param("thisweekFirstDay")String thisweekFirstDay);
	//查询今年本周 总人数   
	@Select("select sum(c_count) from tb_bigdata where c_starttime >= #{thisweekFirstDay} and c_count_name = '日新进人数'")
	Integer findthisweekTotal(@Param("thisweekFirstDay")String thisweekFirstDay);
	
	
	/**
	 * 获取所有（历史所有）日人数(以及当天的日期)
	 * @return
	 */
    @Select("SELECT c_starttime,c_count FROM tb_bigdata where c_count_name = '日新进人数' ORDER by c_starttime")
	@Results({ 
		@Result(property = "c_starttime", column = "c_starttime"),
		@Result(property = "count", column = "c_count"),
	})
	List<Bigdata> findAllPer();
    
    //查询当月(截止到昨天)总人数
    @Select("SELECT SUM(c_count) FROM tb_bigdata where c_starttime >= #{currentMonthFirstDay} and c_count_name = '日新进人数'")
	Integer getCurrentMonthTotal(@Param("currentMonthFirstDay") String currentMonthFirstDay);
    //查询当月以前   每一月的总人数
    @Select("SELECT SUM(c_count) FROM tb_bigdata where c_starttime >= #{lasttMonth} and c_starttime < #{currentMonth} and c_count_name = '日新进人数'")
    Integer geteveryMonthTotal(@Param("lasttMonth") String lasttMonth, @Param("currentMonth") String currentMonth);
	
    //查询今年 以前 所有日统计数据
    @Select("SELECT SUM(c_count) FROM tb_bigdata where c_starttime < #{year} and c_count_name = '日新进人数'")
    Integer getlastAllYearTotal(@Param("year") String year);
    
    /**
	 * 获取所有（历史所有）报警人数
	 * @return
	 */
    @Select("SELECT c_starttime,c_count FROM tb_bigdata where c_count_name = '日报警人数' ORDER by c_starttime")
	@Results({ 
		@Result(property = "c_starttime", column = "c_starttime"),
		@Result(property = "count", column = "c_count"),
	})
	List<Bigdata> findAlarmPerson();
    /**
     * 获取总抓拍人数（今天之前）
     * @return
     * */
    @Select("SELECT IFNULL((SELECT count FROM tb_bigdata_camera where create_time >= (select DATE_SUB(curdate(),INTERVAL 0 DAY)) " +
			"AND c_count_name = '抓拍总数'),0)")
    Integer getDayImgNumber();

	/**
	 * 获取截至目前的总抓拍人数
	 * @return
	 */
	@Select("select COUNT(id) from jh_facepic where snaptime >= (select DATE_SUB(curdate(),INTERVAL 0 DAY))")
	Integer getTodayImgNumber();

	@Select("SELECT e.ip,e.name,IFNULL(e.count,0) count,IFNULL(e.yesterday_num,0) yesterday_num,IFNULL(f.today_num,0) today_num FROM \n" +
            "(SELECT a.ip,a.name,a.count,b.yesterday_num from \n" +
            "(SELECT c.ip,c.name,d.count FROM (SELECT ip,name FROM tb_camera_info WHERE group_id REGEXP #{areaId}) c \n" +
            "LEFT JOIN (SELECT count,camera_ip FROM tb_bigdata_camera \n" +
            "where create_time >= (select DATE_SUB(curdate(),INTERVAL 0 DAY)) \n" +
            "AND c_count_name = '相机抓拍总数') d on c.ip = d.camera_ip) a LEFT JOIN\n" +
            "(select CamIP,COUNT(CamIP) yesterday_num from db_realtimeimage \n" +
            "where ImageDate >= (select DATE_SUB(curdate(),INTERVAL 1 DAY)) \n" +
            "AND ImageDate < (select DATE_SUB(curdate(),INTERVAL 0 DAY)) GROUP BY CamIP) b on a.ip = b.CamIP) e \n" +
            "LEFT JOIN (SELECT CamIP,COUNT(CamIP) today_num FROM db_realtimeimage \n" +
            "where ImageDate >= (select DATE_SUB(curdate(),INTERVAL 0 DAY)) GROUP BY CamIP) f\n" +
            "on e.ip = f.CamIP")
    @Results({ 
		@Result(property = "cameraName", column = "name"),
		@Result(property = "countToday", column = "today_num"),
		@Result(property = "count", column = "count"),
		@Result(property = "yesterdayPerNum", column = "yesterday_num")})
	List<PersonNumByCameraIp> getPersonNumByCameraIp(@Param("areaId") String areaId);
    @Select("SELECT count(*) from tb_user where enabled = 1")
	int getUserNum();
    /**
     * 返回正常运行的相机个数
     * 昨天到此刻 有抓拍的为正常运行的相机
     * @return
     */
    @Select("SELECT COUNT(cameraid) FROM jh_camera WHERE groupid REGEXP #{areaId} AND cameraid IN \n" +
            "(select cameraid from jh_facepic \n" +
            "where snaptime >= (select DATE_SUB(curdate(),INTERVAL 1 DAY)) \n" +
            "AND snaptime < (select DATE_SUB(curdate(),INTERVAL -1 DAY)) \n" +
            "GROUP BY cameraid)")
    int getCameraNumOnline(@Param("areaId") String areaId);
	/**
	 * 获取全部相机数量
	 * @return
	 */
	@Select("SELECT count(id) FROM jh_camera WHERE groupid REGEXP #{areaId}")
	int getTotalCameraCount(@Param("areaId") String areaId);

	/**
	 * 获取异常相机数量
	 * 从昨天截止到现在 没有抓拍的为异常相机
	 * @return
	 */
	@Select("SELECT count(ti.id) from jh_camera ti WHERE ti.groupid REGEXP #{areaId} AND ti.cameraid NOT in \n" +
            "(select cameraid from jh_facepic where snaptime >= (select DATE_SUB(curdate(),INTERVAL 1 DAY)) \n" +
            "AND snaptime < (select DATE_SUB(curdate(),INTERVAL -1 DAY)) GROUP BY cameraid)")
	int getOffLineCameraCount(@Param("areaId") String areaId);

	/**
	 * @Description 查询异常相机列表名字
	 * @param areaId
	 * @date 2019-01-11 10:47:45
	 * @author xieyingchao
	 */
	@Select("SELECT cameraname FROM jh_camera WHERE groupid REGEXP #{areaId} AND cameraid NOT IN(\n" +
			"SELECT cameraid FROM jh_facepic WHERE snaptime > \n" +
			"(select DATE_SUB(curdate(),INTERVAL 8 DAY)) GROUP BY cameraid)")
	List<String> getOffLineCamera(@Param("areaId") String areaId);
    /**
     * 获取日期区间内的人数
     * @return
     */
    @Select("SELECT sum(c_count) FROM tb_bigdata WHERE c_count_name = '日新进人数' AND c_starttime >= #{startDate} AND c_starttime < #{endDate}")
    Integer getPersonCount(@Param("startDate")Date startDate,@Param("endDate") Date endDate);

    /**
     *  获取日期区间内每日人数
     * @return
     */
    @Select("SELECT SUBSTRING(c_starttime,1,10) myTime,c_count from tb_bigdata WHERE c_count_name = '日新进人数' AND c_starttime >= #{startDate} AND c_starttime < #{endDate} GROUP BY myTime")
    @Results({
            @Result(property = "c_starttime", column = "myTime"),
            @Result(property = "count", column = "c_count"),
    })
    List<Bigdata> getDayPersonCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 按月获统计时间区间内的人数 String 月份yyyy-mm，Integer数量
     * @param months 月份列表 yyyy-mm
     * @return
     */
    @SelectProvider(type = BigDateConditionalMapperProvider.class, method = "getMonthPersonCount")
    @Results({
            @Result(property = "startTime", column = "everyMonth"),
            @Result(property = "count", column = "personNum"),
    })
    List<Bigdata> getMonthPersonCount(@Param("months") List<String> months);
}
