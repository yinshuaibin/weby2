package com.ferret.dao.provider;

import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 *  历史查询mapper sql语句复杂拼接
 * @author hyl  时间:0115
 */
public class HistoryPassMapperProvider {
    //通过时间相机分组查询历史纪录
    public String findHistoryPassByTimeAndCameraId(@Param("cameraId") List cameraId,
                                                   @Param("startDateTime") String startDateTime,
                                                   @Param("endDateTime") String endDateTime,
                                                   @Param("pageNum") int pageNum,
                                                   @Param("pageSize") int pageSize) {
        StringBuffer sql = new StringBuffer("SELECT jcp.id,jcp.clusterid,jcp.featureid,jca.cameraid,jcp.cameraip,jcp.picpath,jcp.pictime,jcp.featurefilename,jcp.featuresubscript," +
			"jca.cameraname,jcp.projectid,jcp.batchid,jcp.isrepeat,jcp.createtime FROM jh_cluster_pic jcp LEFT JOIN jh_camera jca ON jcp.cameraip = jca.cameraip" +
			" WHERE jcp.createtime > '");
        sql.append(startDateTime +"'AND jcp.createtime <'"+endDateTime+"'");
        if(cameraId.size()>0){
            sql.append("AND jca.id in ( ");
            for (int i = 0 ;i<cameraId.size();i++){
                sql.append("'"+cameraId.get(i)+"',");
            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(")");
        }
        sql.append("  ORDER BY jcp.createtime desc  LIMIT "+pageNum+","+pageSize);
        return sql.toString();
    }
    //查询当前条件下的历史查询的总数
    public String findHistoryPassCountByTimeAndCameraId(@Param("cameraId") List cameraId,
                                                        @Param("startDateTime") String startDateTime,
                                                        @Param("endDateTime") String endDateTime){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM jh_cluster_pic jcp LEFT JOIN jh_camera jca ON jcp.cameraip = jca.cameraip " +
        "WHERE jcp.createtime >' ");
        sql.append(startDateTime +"'AND jcp.createtime <'"+endDateTime+"'");
        if(cameraId.size()>0) {
            sql.append("AND jca.id in (");
            for (int i = 0; i < cameraId.size(); i++) {
                sql.append("'" + cameraId.get(i) + "',");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }
        return  sql.toString();
    }

}
