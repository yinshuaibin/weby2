package com.ferret.service.statistics.imp;

import com.ferret.bean.Bigdata;
import com.ferret.dao.AlarmCountMapper;
import com.ferret.dao.BigDateMapper;
import com.ferret.dao.ClusterImgMapper;
import com.ferret.service.statistics.BigDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName BigDataServiceImpl
 * @Description TODO 大数据页面查询逻辑接口实现类
 * @Author xieyingchao
 * @Date 2019-01-11 11:50
 * @Version 1.0
 **/
@Service
public class BigDataServiceImpl implements BigDataService {

    @Autowired
    private BigDateMapper bigDateMapper;

    @Autowired
    private AlarmCountMapper alarmCountMapper;

    @Autowired
    private ClusterImgMapper clusterImgMapper;

    /**
     * @Description 查询在线相机数量
     * @param areaId 
     * @date 2019-01-11 11:52:18
     * @author xieyingchao
     */
    @Override
    public int getOnLineCameraCount(String areaId) {
        return bigDateMapper.getCameraNumOnline(areaId);
    }
    /**
     * @Description 查询地区下相机总数量
     * @param areaId
     * @date 2019-01-11 11:55:27
     * @author xieyingchao
     */
    @Override
    public int getTotalCameraCount(String areaId) {
        return bigDateMapper.getTotalCameraCount(areaId);
    }
    /**
     * @Description 查询地区下异常相机数量
     * @param areaId
     * @date 2019-01-11 11:55:53
     * @author xieyingchao
     */
    @Override
    public int getOffLineCameraCount(String areaId) {
        return bigDateMapper.getOffLineCameraCount(areaId);
    }
    /**
     * @Description 查询地区下异常相机列表
     * @param areaId
     * @date 2019-01-11 11:56:58
     * @author xieyingchao
     * @return List<String> 相机名称集合
     */
    @Override
    public List<String> getOffLineCameraName(String areaId) {
        return bigDateMapper.getOffLineCamera(areaId);
    }

    /**
     * @Description 获取总抓拍数
     * @date 2019-01-11 12:39:16
     * @author xieyingchao
     */
    @Override
    public int getTotalSnapshotCount() {
        return bigDateMapper.getDayImgNumber();
    }
    /**
     * @Description 获取今日抓拍数
     * @date 2019-01-11 12:39:41
     * @author xieyingchao
     */
    @Override
    public int getTodaySnapshotCount() {
        return bigDateMapper.getTodayImgNumber();
    }
    /**
     * @Description 获取用户数量
     * @date 2019-01-11 12:46:16
     * @author xieyingchao
     */
    @Override
    public int getTotalUserCount() {
        return bigDateMapper.getUserNum();
    }
    /**
     * @Description 获取所有报警数量
     * @date 2019-01-11 12:46:58
     * @author xieyingchao
     */
    @Override
    public int getTotalAlarmCount() {
        return alarmCountMapper.findAlarmTotalCount();
    }
    /**
     * @Description 获取今日报警数量
     * @date 2019-01-11 12:47:22
     * @author xieyingchao
     */
    @Override
    public int getTodayAlarmCount() {
        return alarmCountMapper.findAlarmTodayCount();
    }
    /**
     * @Description 获取所有聚类总数
     * @date 2019-01-11 12:53:43
     * @author xieyingchao
     */
    @Override
    public int getTotalClusterCount() {
        return clusterImgMapper.getAllClusterCount();
    }
    /**
     * @Description 获取今日聚类数量
     * @date 2019-01-11 12:53:49
     * @author xieyingchao
     */
    @Override
    public int getTodayClusterCount() {
        return clusterImgMapper.getDayCount();
    }

    /**
     * @Description 查询历史人数统计（今天，昨天，本周，上周，本月，上月 新进人数)
     * @date 2019-01-11 14:36:14
     * @author xieyingchao
     */
    @Override
    public ArrayList<Integer> getHistoryBigdata() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Integer> arrayList = new ArrayList<>();
        // 今天
        Calendar calendar = Calendar.getInstance();
        String thisDay = dateFormat.format(calendar.getTime());
        Integer thisDayPeople = bigDateMapper.findthisDayPeople(thisDay);
        // 昨天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String lastDay = dateFormat.format(calendar.getTime());
        Integer lastDayPeople = bigDateMapper.findLastDayPeople(lastDay, thisDay);
        if (lastDayPeople == null) {
            lastDayPeople = 0;
        }
        // 本周
        Calendar calendarThisWeek = Calendar.getInstance();
        calendarThisWeek.set(Calendar.DAY_OF_WEEK, 1);
        String thisweek = dateFormat.format(calendarThisWeek.getTime());
        Integer thisweekTotal = bigDateMapper.findthisweekTotal(thisweek);
        if (thisweekTotal == null) {
            thisweekTotal = 0;
        }
        // 上周
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.WEEK_OF_YEAR, -2);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String lastweek = dateFormat.format(calendar.getTime());
        Integer lastweekTotal = bigDateMapper.findlastweekTotal(lastweek, thisweek);
        if (lastweekTotal == null) {
            lastweekTotal = 0;
        }
        // 本月
        Calendar thisMonth = Calendar.getInstance();
        thisMonth.set(Calendar.DAY_OF_MONTH, 1);// 当月第一天
        String thisMonthFirstDay = dateFormat.format(thisMonth.getTime());
        Integer thisMonthTotal = bigDateMapper.findCurrentMonthTotal(thisMonthFirstDay);
        if (thisMonthTotal == null) {
            thisMonthTotal = 0;
        }
        // 上月
        Calendar lastMonth = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);// 上一月
        lastMonth.set(Calendar.DAY_OF_MONTH, 1);// 上月第一天
        String lastMonthFirstDay = dateFormat.format(lastMonth.getTime());
        Integer lastMonthTotal = bigDateMapper.findlastMonthTotal(lastMonthFirstDay, thisMonthFirstDay);
        if (lastMonthTotal == null) {
            lastMonthTotal = 0;
        }
        arrayList.add(thisDayPeople);
        arrayList.add(lastDayPeople);
        arrayList.add(thisweekTotal);
        arrayList.add(lastweekTotal);
        arrayList.add(thisMonthTotal);
        arrayList.add(lastMonthTotal);
        return arrayList;
    }

    /**
     * @Description 查询今年截至目前每个月（包括本月），月新进人数
     * @date 2019-01-11 16:15:05
     * @author xieyingchao
     */
    @Override
    public List<Integer> getPerMonthBigdata() {
        ArrayList<Integer> everyMonthTotal = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        // 通过日期查询今年每月总人数 获取当前月 查询之前的每月
        int month = calendar.get(Calendar.MONTH) + 1;// 当前月份
        // 查询当前月份(截止到今天0点 的人数)
        Integer currentMonthTotal = bigDateMapper.getCurrentMonthTotal(year + "-" + month + "-" + "1");
        if (currentMonthTotal == null) {
            currentMonthTotal = 0;
        }

        for (int myMonth = 1; myMonth < month; myMonth++) { // j---> 1
            // 查询以前每月的 ---月总人数
            Integer geteveryMonthTotal = bigDateMapper.geteveryMonthTotal(year + "-" + myMonth + "-" + "1",
                    year + "-" + (myMonth + 1) + "-" + "1");
            if (geteveryMonthTotal == null) {
                geteveryMonthTotal = 0;
            }
            everyMonthTotal.add(geteveryMonthTotal);
        }
        everyMonthTotal.add(currentMonthTotal);
        return everyMonthTotal;
    }

    /**
     * @Description 查询今年每月总人数
     * @date 2019-01-11 16:25:31
     * @author xieyingchao
     */
    @Override
    public List<Integer> getPerMonthTotal() {
        ArrayList<Integer> everyMonthTotal = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        // 通过日期查询今年每月总人数 获取当前月 查询之前的每月
        int month = calendar.get(Calendar.MONTH) + 1;// 当前月份

        // 查询今年 以前 所有日统计数据
        int sum = 0;
        for (int myMonth = 1; myMonth < month; myMonth++) { // j---> 1
            // 查询以前每月的 ---月总人数
            Integer geteveryMonthTotal = bigDateMapper.geteveryMonthTotal(year + "-" + myMonth + "-" + "1",
                    year + "-" + (myMonth + 1) + "-" + "1");
            if (geteveryMonthTotal == null) {
                geteveryMonthTotal = 0;
            }
            everyMonthTotal.add(geteveryMonthTotal + sum);
            sum += geteveryMonthTotal;
        }
        // 查询当前月份(截止到今天0点 的人数)
        Integer currentMonthTotal = bigDateMapper.getCurrentMonthTotal(year + "-" + month + "-" + "1");
        if (currentMonthTotal == null) {
            currentMonthTotal = 0;
        }
        everyMonthTotal.add(currentMonthTotal + sum);
        return everyMonthTotal;
    }

    /**
     * @Description 获取所有（历史所有）日人数(以及当天的日期)
     * @return 返回值 map {time:xxx,  peopleNum:xxx}
     * @date 2019-01-11 16:42:21
     * @author xieyingchao
     */
    @Override
    public Map<String, Object> getAllPersonCount() {
        ArrayList<Integer> total = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        List<Bigdata> allPer = bigDateMapper.findAllPer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //把Date类型转换成String
        for (Bigdata bigdata : allPer) {
            if (bigdata == null) {
                continue;
            }
            Date c_starttime = bigdata.getC_starttime();
            if (c_starttime != null) {
                // bigdata.setStartTime(dateFormat.format(bigdata.getC_starttime()));
                date.add(dateFormat.format(bigdata.getC_starttime()));
            }else {
                continue;
            }
            Integer count = bigdata.getCount();
            if (count != null) {
                total.add(count);
            }else {
                total.add(0);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("time", date);
        map.put("peopleNum", total);
        return map;
    }
    
    /**
     * @Description 获取所有报警（历史所有）日人数(以及当天的日期)
     * @return 返回值 map {time:xxx,  peopleNum:xxx}
     * @date 2019-01-11 17:00:21
     * @author xieyingchao
     */
    @Override
    public Map<String, Object> getAlarmPerson() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Integer> total = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        List<Bigdata> alarmPer = bigDateMapper.findAlarmPerson();
        //把Date类型转换成String
        for (Bigdata bigdata : alarmPer) {
            if (bigdata == null) {
                continue;
            }
            Date c_starttime = bigdata.getC_starttime();
            if (c_starttime != null) {
                date.add(dateFormat.format(bigdata.getC_starttime()));
            }else {
                continue;
            }
            Integer count = bigdata.getCount();
            if (count != null) {
                total.add(count);
            }else {
                total.add(0);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("time", date);
        map.put("peopleNum", total);
        return map;
    }

}
