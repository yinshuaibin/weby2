package com.ferret.dao.provider;

import com.ferret.bean.BigDateCondition;
import com.ferret.bean.CameraInfo;
import org.apache.ibatis.annotations.Param;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : xyc
 * @version : V1.0
 * @Description : 大数据下mapper sql语句复杂拼接
 * @date : 2018-08-31
 */
public class BigDateConditionalMapperProvider {

    /**
     * 根据时间段查找数据
     * @author xyc
     * @param strangerSearch
     * @return
     */
    public String getBigDataConditional(@Param("bigDateCondition") BigDateCondition strangerSearch){
        StringBuffer sql = new StringBuffer("SELECT e.ip,e.name,IFNULL(e.count,0) count,IFNULL(e.yesterday_num,0) yesterday_num,IFNULL(f.today_num,0) today_num FROM ");
        sql.append("(SELECT a.ip,a.name,a.count,b.yesterday_num FROM ");
        sql.append("(SELECT c.ip,c.name,d.count FROM (SELECT ip,name FROM tb_camera_info WHERE ip in(");
        List<CameraInfo> cameraList = strangerSearch.getCameraInfos();
        for (CameraInfo c : cameraList) {
            sql.append("'" + c.getIp() + "', ");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(")) c LEFT JOIN (SELECT count,camera_ip FROM tb_bigdata_camera ");
        sql.append("where create_time >= (select DATE_SUB(curdate(),INTERVAL 0 DAY)) ");
        sql.append("AND c_count_name = \"相机抓拍总数\") d on c.ip = d.camera_ip) a LEFT JOIN ");
        sql.append("(select CamIP,COUNT(CamIP) yesterday_num from db_realtimeimage ");
        sql.append("where ImageDate >='"+strangerSearch.getOnTime()+"' ");
        sql.append("AND ImageDate <'"+strangerSearch.getOffTime()+"' GROUP BY CamIP) b on a.ip = b.CamIP) e ");
        sql.append("LEFT JOIN (SELECT CamIP,COUNT(CamIP) today_num FROM db_realtimeimage ");
        sql.append("where ImageDate >= (select DATE_SUB(curdate(),INTERVAL 0 DAY)) GROUP BY CamIP) f ");
        sql.append("on e.ip = f.CamIP");
        return  sql.toString();
    }

    /**
     * 按月获统计时间区间内的人数 String 月份yyyy-mm，Integer数量
     * @param months 月份列表 yyyy-mm
     * @author zwc
     * @return
     */
    public String getMonthPersonCount(@Param("months") List<String> months) throws Exception{
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-mm 00:00:00");
        StringBuffer sql = new StringBuffer("SELECT a.everyMonth,SUM(a.c_count) personNum,c_count_name  FROM ( SELECT SUBSTRING(c_starttime,1,7) everyMonth,c_count,c_count_name from tb_bigdata WHERE c_count_name = '日新进人数' AND ( ");
        for (int i = 0; i < months.size(); i++) {
            Date startTime = sdf.parse(months.get(i));
            calendar.setTime(startTime);
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();
            // 处理日期
            if (i < months.size()-1){
                sql.append("(c_starttime >= '")
                        .append(sdFormat.format(startTime))
                        .append("' AND c_starttime < '")
                        .append(sdFormat.format(endTime))
                        .append("') OR ");
            } else {
                //参数的最后一个月份
                sql.append("( c_starttime >= '")
                        .append(sdFormat.format(startTime))
                        .append("' AND c_starttime < '")
                        .append(sdFormat.format(endTime))
                        .append("') )) a GROUP BY a.everyMonth ORDER BY a.everyMonth");
            }
        }
        return  sql.toString();
    }
}
