package com.ferret.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.TravelAtNeight;

/**
 * 夜行人mapper sql语句复杂拼接
 * 
 * @author y 时间:0723
 *
 */
public class TravelAtNeightMapperProvider {

	/**
	 * 查询夜行人结果集sql拼接
	 * 
	 * @param travelAtNeight
	 * @return
	 */
	public String findTravelAtNeightList(@Param("travelAtNeight") TravelAtNeight travelAtNeight) {
		StringBuffer sql = new StringBuffer(
				"SELECT tc.person_id,tc.start_time,tc.image_path,count(tc.person_id) count,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where (TIME(tc.start_time) >='");
		sql.append(travelAtNeight.getStartTime() + "' OR TIME(tc.start_time) <= '" + travelAtNeight.getEndTime()
				+ "')");
		sql.append("AND tc.start_time>= '" + travelAtNeight.getStartDate() + "'");
		sql.append("AND tc.start_time<= '" + travelAtNeight.getEndDate() + "'");
		sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
		List<CameraInfo> cameraList = travelAtNeight.getCameraList();
		for (CameraInfo c : cameraList) {
			sql.append("'" + c.getIp() + "', ");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") GROUP BY tc.person_id HAVING count >=" + travelAtNeight.getMinNum());
		sql.append(" LIMIT "+travelAtNeight.getStartNum()+","+travelAtNeight.getPageSize());
		return sql.toString();
	}

	/**
	 * 查询夜行人总条数sql拼接
	 * npm
	 * @param travelAtNeight
	 * @return
	 */
	public String findTravelAtNeightTotal(@Param("travelAtNeight") TravelAtNeight travelAtNeight) {
		StringBuffer sql = new StringBuffer(
				"SELECT count(*) from (SELECT tc.person_id,tc.start_time,tc.image_path,count(tc.person_id) count,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where (TIME(tc.start_time) >='");
		sql.append(travelAtNeight.getStartTime() + "' OR TIME(tc.start_time) <= '" + travelAtNeight.getEndTime()
				+ "')");
		sql.append("AND tc.start_time>= '" + travelAtNeight.getStartDate() + "'");
		sql.append("AND tc.start_time<= '" + travelAtNeight.getEndDate() + "'");
		sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
		List<CameraInfo> cameraList = travelAtNeight.getCameraList();
		for (CameraInfo c : cameraList) {
			sql.append("'" + c.getIp() + "', ");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") GROUP BY tc.person_id HAVING count >=" + travelAtNeight.getMinNum()+") AS a");
		return sql.toString();
	}
	
	/**
	 * 查询夜行人,根据person_id获取夜行人出行信息结果集,分页
	 * 
	 * @param travelAtNeight
	 * @return
	 */
	public String findTravelAtNeightByPersonId(@Param("travelAtNeight") TravelAtNeight travelAtNeight) {
		StringBuffer sql = new StringBuffer(
				"SELECT tc.person_id,tc.start_time,tc.image_path,tb.longitude,tb.latitude,tb.name FROM tb_cluster_pass tc, tb_camera_info tb where (TIME(tc.start_time) >='");
		sql.append(travelAtNeight.getStartTime() + "' OR TIME(tc.start_time) <= '" + travelAtNeight.getEndTime()
				+ "')");
		sql.append("AND tc.start_time>= '" + travelAtNeight.getStartDate() + "'");
		sql.append("AND tc.start_time<= '" + travelAtNeight.getEndDate() + "'");
		sql.append("AND tc.person_id='"+travelAtNeight.getPersonId()+"' ");
		sql.append("AND tc.camera_ip=tb.ip AND tc.camera_ip in (");
		List<CameraInfo> cameraList = travelAtNeight.getCameraList();
		for (CameraInfo c : cameraList) {
			sql.append("'" + c.getIp() + "', ");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") limit "+travelAtNeight.getStartNum()+" , "+travelAtNeight.getPageSize());
		return sql.toString();
	}
	
}
