package com.ferret.dao.provider;

import com.ferret.bean.CameraInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xyc
 * @version : V1.0
 * @Description : 陌生人查询mapper sql语句复杂拼接
 * @date : 2018-07-23 11:15:03
 */
public class ClusterImgMapperProvider {

    public String getStrangerByCameraAndTimeList(@Param("startTime") String startTime,
                                                 @Param("stime") String stime,
                                                 @Param("endTime") String endTime,
                                                 @Param("startNum") int startNum,
                                                 @Param("pageSize") int pageSize,
                                                 @Param("cameraInfos") List<CameraInfo> cameraInfos){

        StringBuffer sql = new StringBuffer("SELECT x.person_id,x.image_path,x.start_time,x.count,y.name camera_name,y.longitude,y.latitude FROM(");
        sql.append("select person_id,image_path,camera_ip,start_time,count(*) as count from tb_cluster_pass A where ");
        sql.append("start_time >'"+startTime+"'");
        sql.append(" and end_time <'"+endTime+"'");
        sql.append(" and person_id NOT in(SELECT DISTINCT person_id from tb_cluster_pass B where ");
        sql.append("start_time >'"+stime+"'and end_time <'"+startTime+"') ");
        sql.append("and camera_ip in(");
        for (CameraInfo c : cameraInfos) {
            sql.append("'" + c.getIp() + "', ");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(") GROUP BY person_id order by start_time ");
        sql.append("LIMIT "+startNum+","+pageSize+") as x,tb_camera_info as y ");
        sql.append("WHERE x.camera_ip = y.ip");
        System.out.println(sql.toString());
        return  sql.toString();
    }
    public String getStrangerByCameraAndTimeList_count(@Param("startTime") String startTime,
                                                       @Param("stime") String stime,
                                                       @Param("endTime") String endTime,
                                                       @Param("cameraInfos") List<CameraInfo> cameraInfos){

        StringBuffer sql = new StringBuffer("select count(*) from (select count(*) as count from tb_cluster_pass A where ");
        sql.append("start_time >'"+startTime+"'");
        sql.append(" and end_time <'"+endTime+"'");
        sql.append(" and person_id NOT in(SELECT DISTINCT person_id from tb_cluster_pass B where ");
        sql.append("start_time >'"+stime+"'and end_time <'"+startTime+"') ");
        sql.append("and camera_ip in(");
        for (CameraInfo c : cameraInfos) {
            sql.append("'" + c.getIp() + "', ");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(") GROUP BY person_id order by start_time) as t");
        return  sql.toString();
    }
    public String getStrangerByPersonIdList(@Param("startTime") String startTime,
                                            @Param("stime") String stime,
                                            @Param("endTime") String endTime,
                                            @Param("startNum") int startNum,
                                            @Param("pageSize") int pageSize,
                                            @Param("cameraInfos") List<CameraInfo> cameraInfos,
                                            @Param("personId") String personId){

        StringBuffer sql = new StringBuffer("SELECT x.person_id,x.image_path,x.start_time,y.name camera_name,y.longitude,y.latitude,x.feature_id FROM(");
        sql.append("select person_id,image_path,camera_ip,start_time,feature_id from tb_cluster_pass A where ");
        sql.append("start_time >'"+startTime+"'");
        sql.append(" and end_time <'"+endTime+"'");
        sql.append(" and person_id NOT in(SELECT DISTINCT person_id from tb_cluster_pass B where ");
        sql.append("start_time >'"+stime+"' and end_time <'"+startTime+"') ");
        sql.append("and camera_ip in(");
        for (CameraInfo c : cameraInfos) {
            sql.append("'" + c.getIp() + "', ");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(") and person_id = '"+personId+"' order by start_time ");
        sql.append("LIMIT "+startNum+","+pageSize+") as x,tb_camera_info as y ");
        sql.append("WHERE x.camera_ip = y.ip ");
        sql.append("order by x.start_time");
        return  sql.toString();
    }

    /**
     * 实时陌生人sql拼接
     *   y  0828
     * @param cameraList
     * @return
     */
    public  String getStranger(@Param("cameraList")List<CameraInfo> cameraList){
        StringBuffer sql=new StringBuffer("SELECT c.*,d.longitude,d.latitude, d.name from " +
                "(SELECT a.person_id, a.image_path, a.camera_ip, b.create_time FROM tb_cluster_image a, " +
                    "( SELECT personid as person_id, createtime as create_time FROM jh_person " +
                        "WHERE createtime >= SUBDATE(now(),interval 1 MINUTE) AND createtime < now()) b " +
                     "WHERE a.person_id = b.person_id GROUP BY a.person_id )c, tb_camera_info d WHERE c.camera_ip = d.ip ");
        appendCameraIp(sql,cameraList);
        //sql.append("ORDER BY c.create_time desc");
        return sql.toString();
    }
    /**
     * 实时陌生人sql拼接
     *   zwc  2019/1/14
     * @param cameraList
     * @return
     */
    public  String realTimeStranger(@Param("firstId")BigInteger firstId, @Param("lastId")BigInteger lastId, @Param("cameraList")List<CameraInfo> cameraList){
        StringBuffer sql=new StringBuffer("SELECT e.clusterid,e.cameraip,e.picpath,e.createtime,f.cameraname,f.x,f.y from (SELECT a.clusterid,a.cameraip,a.picpath,b.createtime FROM (SELECT c.id,c.clusterid,c.cameraip,c.picpath from jh_cluster_pic c WHERE c.id > ");
        sql.append(firstId)
                .append(" AND c.id <= ")
                .append(lastId);
        if(cameraList !=null && cameraList.size()>0){
            sql.append(" AND c.cameraip in(");
            for (CameraInfo c:cameraList){
                sql.append("'"+c.getIp()+"',");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(") ");
        }
        sql.append(" GROUP BY clusterid) a,jh_cluster b WHERE a.clusterid = b.clusterid AND b.createtime >= (SELECT d.createtime FROM jh_cluster_pic d WHERE d.id = ")
                .append(firstId)
                .append(" )) e,jh_camera f WHERE e.cameraip = f.cameraip");
        return sql.toString();
    }

    /**
     * 根据条件查询最新的聚类人员sql拼接
     *   y  0828
     * @param cameraList
     * @return
     */
    public  String getStrangerOne(@Param("cameraList")List<CameraInfo> cameraList,@Param("pageSize") Integer pageSize){
        StringBuffer sql=new StringBuffer("SELECT c.*,d.longitude,d.latitude, d.name from (SELECT a.person_id, a.image_path, a.camera_ip, b.create_time FROM tb_cluster_image a, ( SELECT person_id, create_time FROM tb_cluster ");
        sql.append("order by create_time DESC limit 0,10000) b WHERE a.person_id = b.person_id GROUP BY a.person_id )c,tb_camera_info d WHERE c.camera_ip = d.ip ");
        appendCameraIp(sql,cameraList);
        sql.append("ORDER BY c.create_time desc limit 0, "+pageSize);
        return sql.toString();
    }


    /*
     * 拼接相机ip的方法
     */
    public void appendCameraIp(StringBuffer sql,List<CameraInfo> cameraList){
        if(cameraList !=null && cameraList.size()>0){
            sql.append("AND c.camera_ip in(");
            for (CameraInfo c:cameraList){
                sql.append("'"+c.getIp()+"',");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(") ");
        }
    }

    /**
     * 查询某些地区在某个时间段内出现的所有人员
     * @param startTime 起始时间
     * @param endTime    结束时间
     * @param areaCodes  相机ip集合
     * @return
     */
    public static String getPersonByTimeAndArea(@Param("startTime")String startTime,
                                                @Param("endTime")String endTime,@Param("areaCodes") List<String> areaCodes){
        StringBuffer sql = new StringBuffer("SELECT d.`name`, d.number, b.* FROM tb_camera_info d, (" +
                " SELECT person_id, image_path,start_time image_time, COUNT(person_id) count, camera_ip FROM tb_cluster_pass ");
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            sql.append("WHERE start_time >= '").append(startTime).append("' AND start_time <= '").append(endTime).append("'");
        }
        if(StringUtils.isBlank(endTime) && StringUtils.isNotBlank(startTime)){
            sql.append("WHERE image_time >= '").append(startTime).append("'");
        }
        if(StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime)){
            sql.append("WHERE image_time <= '").append(endTime).append("'");
        }
        sql.append(" GROUP BY person_id, camera_ip )b where ");
        appendAreaCode(areaCodes,sql);
        sql.append(" d.ip = b.camera_ip ORDER BY b.image_time desc;");
        return  sql.toString();
    }

    /**
     * 根据该人员id查询对应地区,对应时间出现的所有信息
     * @param personId  人员id
     * @param startTime  起始时间
     * @param endTime   结束时间
     * @param areaCodes 地区码集合
     * @return
     */
    public static String getPersonTrajectory(@Param("personId")String personId,@Param("startTime")String startTime,
                                             @Param("endTime")String endTime,@Param("areaCodes") List<String> areaCodes){
        StringBuffer sql = new StringBuffer("SELECT d.`name`,d.longitude,d.latitude,a.person_id, a.image_path, a.image_time FROM tb_camera_info d, " +
                "tb_cluster_image a WHERE person_id = '");
        sql.append(personId).append("'");
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            sql.append("AND image_time >= '").append(startTime).append("' AND image_time <= '").append(endTime).append("'");
        }
        if(StringUtils.isBlank(endTime) && StringUtils.isNotBlank(startTime)){
            sql.append("AND image_time >= '").append(startTime).append("'");
        }
        if(StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime)){
            sql.append("AND image_time <= '").append(endTime).append("'");
        }
        sql.append(" and ");
        appendAreaCode(areaCodes,sql);
        sql.append(" d.ip = a.camera_ip ORDER BY a.image_time");
        return sql.toString();
    }


    private static void appendAreaCode(List<String> areaCodes,StringBuffer sql){
        if(areaCodes!=null && areaCodes.size()>0){
            sql.append("( ");
            for (String areaCode:areaCodes){
                sql.append("d.number LIKE 'C").append(areaCode).append("%'").append("or ");
            }
            sql.delete(sql.length()-3,sql.length());
            sql.append(" ) AND");
        }
    }

    public String getPersonByClusterids(@Param("clusterids") ArrayList<String> arrayList){
        StringBuffer sb = new StringBuffer("SELECT b.clusterid,a.count,b.picpath,b.pictime,c.personid FROM (SELECT clusterid,sum(count) count FROM tb_clusterid_count WHERE clusterid in");
        StringBuffer ids = new StringBuffer("(");
        for (String id : arrayList) {
            ids.append("'").append(id).append("',");
        }
        String string = ids.deleteCharAt(ids.length() - 1).append(")").toString();
        sb.append(string).append(") a,jh_cluster_pic b,jh_cluster c WHERE a.clusterid = b.clusterid AND a.clusterid = c.clusterid  GROUP BY b.clusterid");
        return sb.toString();
    }

}
