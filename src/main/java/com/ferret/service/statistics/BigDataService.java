package com.ferret.service.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BigDataService
 * @Description TODO 大数据页面查询逻辑接口
 * @Author xieyingchao
 * @Date 2019-01-11 11:47
 * @Version 1.0
 **/
public interface BigDataService {

    /** 获取在线相机数量 */
    int getOnLineCameraCount(String areaId);
    /** 获取全部相机数量 */
    int getTotalCameraCount(String areaId);
    /** 获取异常相机数量 */
    int getOffLineCameraCount(String areaId);
    /** 获取异常相机列表名字 */
    List<String> getOffLineCameraName(String areaId);

    /** 获取总抓拍数 */
    int getTotalSnapshotCount();
    /** 获取今日抓拍数 */
    int getTodaySnapshotCount();

    /** 获取总用户数 */
    int getTotalUserCount();

    /** 获取总报警数 */
    int getTotalAlarmCount();
    /** 获取今日报警数 */
    int getTodayAlarmCount();

    /** 获取所有聚类数量 */
    int getTotalClusterCount();
    /** 获取今日聚类数量 */
    int getTodayClusterCount();

    /** 查询历史人数统计（今天，昨天，本周，上周，本月，上月 新进人数）*/
    ArrayList<Integer> getHistoryBigdata();

    /** 查询今年截至目前每个月（包括本月），月新进人数 */
    List<Integer> getPerMonthBigdata();
    /** 查询今年每月总人数 */
    List<Integer> getPerMonthTotal();

    /** 获取所有（历史所有）日人数(以及当天的日期) */
    Map<String, Object> getAllPersonCount();
    /** 获取所有报警（历史所有）日人数(以及当天的日期) */
    Map<String, Object> getAlarmPerson();


}
