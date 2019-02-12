package com.ferret.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.TravelAtNeight;

/**
 * 频繁出行mapper sql语句复杂拼接
 * 
 * @author hyl  时间:0723
 *
 */
public class LingerAnalysisMapperProvide {

//	/**
//	 * 查询频繁出行人结果集sql拼接
//	 *
//	 * @param travelAtNeight
//	 * @return List<clusterdate>
//	 */
//	public String findAnalysisByTravelAtNeight(@Param("travelAtNeight") TravelAtNeight travelAtNeight) {
//		StringBuffer sql = new StringBuffer(
//				"SELECT tc.person_id,tc.start_time,tc.image_path,count(tc.person_id) count,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where tc.start_time >='");
//		sql.append(travelAtNeight.getStartTime() + "' AND tc.end_time <= '" + travelAtNeight.getEndTime()
//				+ "'");
//		sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
//		List<CameraInfo> cameraList = travelAtNeight.getCameraList();
//		for (CameraInfo c : cameraList) {
//			sql.append("'" + c.getIp() + "', ");
//		}
//		sql.deleteCharAt(sql.lastIndexOf(","));
//		sql.append(") GROUP BY tc.person_id HAVING count >=" + travelAtNeight.getMinNum());
//		sql.append(" LIMIT "+travelAtNeight.getStartNum()+","+travelAtNeight.getPageSize());
//		return sql.toString();
//	}
	/**
	 * 查询频繁出行人结果集sql拼接
	 *
	 */
	public String findAnalysisByTravelAtNeight(@Param("startNum") int startNum,
                                               @Param("pageSize") int pageSize,
                                               @Param("startTime") String startTime,
                                               @Param("endTime") String endTime,
                                               @Param("minNum") int minNum,
                                               @Param("cameraInfos") List<CameraInfo> cameraInfos) {
		StringBuffer sql = new StringBuffer(
				"SELECT tc.person_id,tc.start_time,tc.image_path,count(tc.person_id) count,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where tc.start_time >='");
		sql.append(startTime + "' AND tc.end_time <= '" + endTime
				+ "'");
		sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
		for (CameraInfo c : cameraInfos) {
			sql.append("'" + c.getIp() + "', ");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") GROUP BY tc.person_id HAVING count >=" + minNum);
		sql.append(" LIMIT "+startNum+","+pageSize);
		return sql.toString();
	}

//	/**
//	 * 查询夜行人总条数sql拼接
//	 *
//	 * @param travelAtNeight
//	 * @return int total
//	 */
//	public String findAnalysisTotalByTravelAtNeight(@Param("travelAtNeight") TravelAtNeight travelAtNeight) {
//		StringBuffer sql = new StringBuffer(
//				"SELECT count(*) from (SELECT tc.person_id,tc.start_time,tc.image_path,count(tc.person_id) count,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where tc.start_time >='");
//		sql.append(travelAtNeight.getStartTime() + "' AND tc.start_time <= '" + travelAtNeight.getEndTime()
//				+ "'");
//		sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
//		List<CameraInfo> cameraList = travelAtNeight.getCameraList();
//		for (CameraInfo c : cameraList) {
//			sql.append("'" + c.getIp() + "', ");
//		}
//		sql.deleteCharAt(sql.lastIndexOf(","));
//		sql.append(") GROUP BY tc.person_id HAVING count >=" + travelAtNeight.getMinNum()+") AS a");
//		return sql.toString();
//	}
    /**
     * 查询夜行人总条数sql拼接
     *
     * @return int total
     */
    public String findAnalysisTotalByTravelAtNeight(@Param("startTime") String startTime,
                                                    @Param("endTime") String endTime,
                                                    @Param("minNum") int minNum,
                                                    @Param("cameraInfos") List<CameraInfo> cameraInfos) {
        StringBuffer sql = new StringBuffer(
                "SELECT count(*) from (SELECT tc.person_id,tc.start_time,tc.image_path,count(tc.person_id) count,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where tc.start_time >='");
        sql.append(startTime + "' AND tc.start_time <= '" + endTime
                + "'");
        sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
        for (CameraInfo c : cameraInfos) {
            sql.append("'" + c.getIp() + "', ");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(") GROUP BY tc.person_id HAVING count >=" + minNum+") AS a");
        return sql.toString();
    }
	
//	/**
//	 * 根据person_id查看频繁出行人的结果集,分页
//	 *
//	 * @param travelAtNeight
//	 * @return
//	 */
//	public String findAnalysisResultByPersonId(@Param("travelAtNeight") TravelAtNeight travelAtNeight) {
//		StringBuffer sql = new StringBuffer(
//				"SELECT tc.person_id,tc.start_time,tc.image_path,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where tc.start_time >='");
//		sql.append(travelAtNeight.getStartTime() + "' AND tc.start_time <= '" + travelAtNeight.getEndTime()
//				+ "'");
//		sql.append("AND tc.person_id='"+travelAtNeight.getPersonId()+"' ");
//		sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
//		List<CameraInfo> cameraList = travelAtNeight.getCameraList();
//		for (CameraInfo c : cameraList) {
//			sql.append("'" + c.getIp() + "', ");
//		}
//		sql.deleteCharAt(sql.lastIndexOf(","));
//		sql.append(") limit "+travelAtNeight.getStartNum()+" , "+travelAtNeight.getPageSize());
//		return sql.toString();
//	}
    /**
     * @Descriptions 根据person_id查看频繁出行人的结果集,分页
     * @return
     */
    public String findAnalysisResultByPersonId(@Param("startTime") String startTime,
                                               @Param("endTime") String endTime,
                                               @Param("startNum") int startNum,
                                               @Param("pageSize") int pageSize,
                                               @Param("personId") String personId,
                                               @Param("cameraInfos") List<CameraInfo> cameraInfos) {
        StringBuffer sql = new StringBuffer(
                "SELECT tc.person_id,tc.start_time,tc.image_path,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where tc.start_time >='");
        sql.append(startTime + "' AND tc.start_time <= '" + endTime
                + "'");
        sql.append("AND tc.person_id='"+personId+"' ");
        sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
        for (CameraInfo c : cameraInfos) {
            sql.append("'" + c.getIp() + "', ");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(") limit "+startNum+" , "+pageSize);
        return sql.toString();
    }
}
