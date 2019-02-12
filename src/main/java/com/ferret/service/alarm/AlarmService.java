package com.ferret.service.alarm;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ferret.bean.AlarmCount;
import com.ferret.bean.AlarmInterface;
import com.ferret.bean.ClusterPass;
import com.ferret.dto.AlarmPersonDTO;
import com.ferret.dto.AlarmQueryDTO;
import com.ferret.dto.PageDTO;
import org.apache.ibatis.annotations.Param;

import com.ferret.bean.RealTimeAlarm;
import com.ferret.bean.bukong.NewBuKong;
import com.ferret.bean.pagebean.RealTimeAlarmPage;

/**
 * @pack: com.ferret.service.alarm;
 * @auth: cc;
 * @since: 2017/12/15 0015;
 * @desc:
 */
public interface AlarmService {
    /**
     * 查询历史报警信息
     *
     * @return
     */
    List<RealTimeAlarm> findAllAlarm();

    @Deprecated
    RealTimeAlarmPage findByBk(@Param("alarmPage") RealTimeAlarmPage alarmPage);

    /**
     * 通过相机和时间段分页查询报警信息 替代findByBk
     * @param cameraIds 相机id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    List<RealTimeAlarm> findAlarmByCameras(List<String> cameraIds, Date startTime, Date endTime,Integer pageNum,Integer pageSize);

    /**
     * 通过相机和时间段查询报警总条数 替代findByBk
     * @param cameraIds 相机id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    Integer findAlarmByCamerasCount(List<String> cameraIds, Date startTime, Date endTime);

    /**
     * 更新数据sampleFlag
     *
     * @param sampleFlag
     */
    void updateBySampleFlag(Integer alarmId, Integer sampleFlag);

    /**
     * 更新数据页面
     *
     * @param status
     * @param confirmTime
     * @param userId
     * @param sampleFlag
     */
    void updateByStatus(@Param("status") Integer status,
                        @Param("confirm_time") Date confirmTime,
                        @Param("userId") Integer userId,
                        @Param("sample_flag") Integer sampleFlag);

    /**
     * 根据布控id 查询 报警信息,并分页
     * @param bkId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Deprecated
    PageDTO listAlarmsByBkId(Integer bkId, Integer pageNum, Integer pageSize);

    /**
     * 通过布控id分页查询报警信息 替代 listAlarmsByBkId
     * @param bukongId
     * @return
     */
    List<RealTimeAlarm> findAlarmByBukongId(int bukongId, Date startTime, Date endTime,Integer pageNum, Integer pageSize);


    /**
     * 通过布控id查询报警信息总条数 替代 listAlarmsByBkId
     * @param bukongId
     * @return
     */
    Integer findAlarmByBukongIdCount(int bukongId, Date startTime, Date endTime);


    PageDTO<RealTimeAlarm> listAlarmsByQueryDTO(AlarmQueryDTO alarmQueryDTO);

    List<Map<Integer,Integer>> listBKIdsByQueryDTO(AlarmQueryDTO alarmQueryDTO);
    Integer changeStatusById(BigInteger AlarmId);

    /**
     * 根据报警信息表的uuid查询布控对象的所有信息以及一分钟内出现的报警信息
     * @param 
     * @return
     */
	NewBuKong findBKbyRealTimeAlarmId(BigInteger id, String alarmTime);

    /**
     * 根据前台传递过来的websocket接受的报警信息中的报警id查询bk信息,并返回最近的alarmSize条报警信息
     * (websocket有报警信息返回时,调用,调用页面:main.vue)
     *  此处使用了报警表中的布控图片,因为不需要关联查询布控表,所以在service层对于图片路径的处理会和getReamTimeAlarmByAlarmId不同
     *  y  0108
     * @param id  报警表中的自增id, 不是报警id
     * @param alarmSize 要返回几条报警信息
     * @return
     */
	List<RealTimeAlarm> findAlarmById(String id,Integer alarmSize);

    /**
     * 查询数据库中最近报警的perpleSize个人的最新alarmSize条报警记录
     * @param peopleSize 最近报警的人数
     * @param alarmSize  每个报警的人的最近几条报警记录
     * @return
     * y 1220
     */
    List<List<RealTimeAlarm>> findPeopleRealTimeAlarm(Integer peopleSize,Integer alarmSize);

    /**
     * 根据bkid查询布控详细信息
     */
    AlarmInterface findAlarmBk(Integer bkId);

    @Deprecated
    List<AlarmCount> getAlarmNumByBuKongGroup(ClusterPass clusterPass);

    /**
     * @Descriptions 统计时间段内各个布控分组的报警数量
     * @Author xyc 2018/12/17
     */
    List<AlarmCount> getAlarmNumByBuKongGroup(String startTime, String endTime);

    /**
     * @Descriptions 统计时间段内各个布控分组的报警信息
     * @Author xyc 2018/12/17
     */
    @Deprecated
    List<RealTimeAlarm> getAlarmInfoByBuKongGroup(String startTime, String endTime, List<AlarmCount> alarmCounts);
    @Deprecated
    List<RealTimeAlarm> getAlarmInfoByBuKongGroup(AlarmPersonDTO alarmPersonDTO);

    /**
     * 通过布控分组ID查询某时间段内的报警信息
     * @param startTime
     * @param endTime
     * @param groupIds
     * @return
     */
    Map<String,List<RealTimeAlarm>> getAlarmInfoByBuKongGroup(Date startTime,Date endTime, List<String> groupIds);

    @Deprecated
    Map getAlarmCount();

    /**
     * 获取全部报警数量 替代getAlarmCount
     * @return
     */
    int getTotalAlarmCount();

    /**
     * 获取时间段内的报警数量 替代getAlarmCount
     * @param startDate
     * @param endDate
     * @return
     */
    int getAlarmCount(Date startDate, Date endDate);

    /**
     * 获取布控的分组报警数量
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     */
    int getAlarmCount(String groupID, Date startDate, Date endDate);

    /**
     * 根据报警id查询对应的报警信息  y 0105
     * @param alarmId 报警id
     * @return
     */
    RealTimeAlarm getRealTimeAlarmByAlarmId(BigInteger alarmId);
}
