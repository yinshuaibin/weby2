
package com.ferret.dao;

import com.ferret.bean.AlarmCount;
import com.ferret.bean.RealTimeAlarm;
import com.ferret.dao.provider.RealTimeAlarmMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmCountMapper {

    /**
     * 统计时间段内各个布控分组的报警数量
     * @return string
     * @author zwc 2018/10/12
     */
    @Select("SELECT bgp.id,bgp.typename,c.sumCount from \n" +
            "jh_controlpeople_type bgp LEFT JOIN \n" +
            "(SELECT bk.contorltype,SUM(ra.count) sumCount FROM jh_controlpeople bk,\n" +
            " (SELECT rel.controlpeopleid,COUNT(rel.controlpeopleid) count FROM jh_alarm rel \n" +
            " WHERE rel.createtime >= #{startTime} AND rel.createtime <= #{endTime} GROUP BY rel.controlpeopleid) ra \n" +
            " WHERE bk.id = ra.controlpeopleid GROUP BY bk.contorltype)\n" +
            " c ON bgp.id = c.contorltype WHERE bgp.iscontrol = 1 ORDER BY c.sumCount desc")
    @Results({
            @Result(property = "groupID",column = "id"),
            @Result(property = "groupName",column = "typename"),
            @Result(property = "count",column = "sumCount"),
    })
    List<AlarmCount> getAlarmNumByBuKongGroup(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 统计时间段内各个布控分组的报警信息
     *
     * @return
     */
    @SelectProvider(type = RealTimeAlarmMapperProvider.class, method = "findAlarmByGroupId")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "alarmId",column = "alarmid"),
            @Result(property = "bkId",column = "controlpeopleid"),
            @Result(property = "name",column = "name"),
            @Result(property = "sex",column = "sex"),
            @Result(property = "cardNumber",column = "cardnumber"),
            @Result(property = "alarmPicId",column = "alarmpicid"),
            @Result(property = "status",column = "status"),
            @Result(property = "handleType",column = "handletype"),
            @Result(property = "handleremark",column = "handleremark"),
            @Result(property = "similar",column = "score"),
            @Result(property = "alarmTime",column = "createtime"),
            @Result(property = "managerId",column = "managerid"),
            @Result(property = "managerName",column = "managername"),
            @Result(property = "manageTime",column = "managetime"),
            @Result(property = "cameraId",column = "cameraid"),
            @Result(property = "alarmImagePath",column = "snappicpath"),
            @Result(property = "arcscore",column = "arcscore"),
            @Result(property = "projectId",column = "projectid"),
            @Result(property = "remark",column = "remark"),
            @Result(property = "longitude",column = "x"),
            @Result(property = "latitude",column = "y"),
            @Result(property = "cameraName",column = "cameraname"),
            @Result(property = "bkGroupId",column = "controltypeid")})
    List<RealTimeAlarm> findAlarmByGroup(@Param("groupId") List<String> groupId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);

    /**
     * @Descriptions 统计抓拍总数量
     * @author xyc 2018-12-10
     * @return
     */
    @Select("SELECT COUNT(id) FROM jh_alarm")
    int findAlarmTotalCount();

    /**
     * @Descriptions 统计今日抓拍总数量
     * @author xyc 2018-12-10
     * @return
     */
    @Select("SELECT count(id) FROM jh_alarm " +
            "WHERE createtime >= DATE_FORMAT(CURDATE(),'%Y-%m-%d %H:%i:%s') AND createtime <= now()")
    int findAlarmTodayCount();
}

