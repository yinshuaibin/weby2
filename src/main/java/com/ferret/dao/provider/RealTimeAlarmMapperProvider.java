package com.ferret.dao.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;

import com.ferret.bean.pagebean.RealTimeAlarmPage;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.dao.provider;
 * @auth: Administrator;
 * @since: 2018/1/13 0013;
 * @desc:
 */
@Slf4j
public class RealTimeAlarmMapperProvider{

	public String findByBk(Map map){
		List<String> list = (List<String>)map.get("param1");
		Date begintime = (Date)map.get("param3");
		Date endtime = (Date)map.get("param4");
		String groupId = (String) map.get("param5");
		String reason = (String)map.get("param6");

		StringBuffer sql =
				new StringBuffer("select ra.alarm_time,ra.similar," +
						"       ra.status,ra.sample_flag,ra.background_image_id," +
						"       ra.alarm_image_path,ra.bk_image_path,bk.reason,ca.name,bg.background_image_path" +
						"       from ((tb_realtime_alarm ra LEFT JOIN tb_bukong bk" +
						"       on ra.bk_id = bk.bk_id)" +
						"       LEFT JOIN tb_camera_info ca" +
						"       ON ra.camera_id = ca.id )" +
						"       LEFT JOIN tb_background bg" +
						"       ON ra.background_image_id = bg.historypass_id");
		sql.append("       where 1=1 ");
		if(null != list && list.size()>0){
			sql.append(" and (");
			for (int i=0;i<list.size();i++) {

				String number = list.get(i);

				if(i>0){
					sql.append(" or ");
				}
				sql.append("ca.number REGEXP '").append(number).append("'");
			}
			sql.append(") ");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (begintime != null){
			sql.append("and ra.alarm_time >= '").append(format.format(begintime)).append("' ");
		}
		if(endtime != null){
			sql.append("and ra.alarm_time <= '").append(format.format(endtime)).append("' ");
		}
		if(null !=groupId){
			sql.append("and bk.bk_group_id = '").append(groupId).append("'");
		}
		if(null != reason){
			sql.append("and contains(reason,'").append(reason).append("') ");
		}
		return sql.toString();
	}


	public String alarmQuery(Map map) {
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("select c.*,d.name cameraName,d.longitude longitude,d.latitude latitude from (select a.*,b.reason from  "
				+ "(select alarm_id,bk_id,bk_image_path,alarm_image_path,DATE_FORMAT(alarm_time,'%Y-%m-%d %H:%i:%s') alarm_time,SUBSTRING(alarm_time, 1, 16) minuteByalarm,camera_id,similar,"
				+ "background_image_id,video_id,sample_flag,status,confirm_time,user_id,spare from tb_realtime_alarm where 1=1 ");
		String ids=(String)map.get("ids");
		RealTimeAlarmPage alarmPage=(RealTimeAlarmPage)map.get("alarmPage");
		if(StringUtils.isNotBlank(ids))
			sqlBuf.append( "and camera_id in (").append(ids).append(")");
		if(StringUtils.isNotBlank(alarmPage.getBeginTime()))
			sqlBuf.append(" and alarm_time>='").append(alarmPage.getBeginTime()).append("'");
		if(StringUtils.isNotBlank(alarmPage.getEndTime()))
			sqlBuf.append(" and alarm_time< '").append(alarmPage.getEndTime()).append("'");
		if(StringUtils.isNotBlank(alarmPage.getReason()))
			sqlBuf.append(" and bk_image_path like '%").append(alarmPage.getReason()).append("%'");
		sqlBuf.append("GROUP BY bk_id,minuteByalarm)  a,tb_bukong b where a.bk_id=b.bk_id  order by a.alarm_time desc ");
		sqlBuf.append(" limit ").append((alarmPage.getPageNo()-1)*alarmPage.getPageSize()).append(",").append(alarmPage.getPageSize());

		if(StringUtils.isNotBlank(alarmPage.getGroupId()))
			sqlBuf.append(" and b.bk_group_id ='").append(alarmPage.getGroupId()).append("'");

		sqlBuf.append(") c,tb_camera_info d where c.camera_id=d.id order by c.alarm_time desc");
		log.debug(sqlBuf.toString());
		return sqlBuf.toString();
	}

	public String alarmQueryCount(Map map) {
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("select count(*) dataCount  from (select a.*,b.reason from  "
				+ "(select alarm_id,bk_id,bk_image_path,alarm_image_path,DATE_FORMAT(alarm_time,'%Y-%m-%d %H:%i:%s') alarm_time,SUBSTRING(alarm_time, 1, 16) minuteByalarm,camera_id,similar,"
				+ "background_image_id,video_id,sample_flag,status,confirm_time,user_id,spare from tb_realtime_alarm where 1=1 ");
		String ids=(String)map.get("ids");
		RealTimeAlarmPage alarmPage=(RealTimeAlarmPage)map.get("alarmPage");
		if(StringUtils.isNotBlank(ids))
			sqlBuf.append( "and camera_id in (").append(ids).append(")");
		if(StringUtils.isNotBlank(alarmPage.getBeginTime()))
			sqlBuf.append(" and alarm_time>='").append(alarmPage.getBeginTime()).append("'");
		if(StringUtils.isNotBlank(alarmPage.getEndTime()))
			sqlBuf.append(" and alarm_time< '").append(alarmPage.getEndTime()).append("'");
		if(StringUtils.isNotBlank(alarmPage.getReason()))
			sqlBuf.append(" and bk_image_path like '%").append(alarmPage.getReason()).append("%'");
		sqlBuf.append("GROUP BY bk_id,minuteByalarm)  a,tb_bukong b where a.bk_id=b.bk_id ");

		if(StringUtils.isNotBlank(alarmPage.getGroupId()))
			sqlBuf.append(" and b.bk_group_id ='").append(alarmPage.getGroupId()).append("'");

		sqlBuf.append(") c,tb_camera_info d where c.camera_id=d.id");
		log.debug(sqlBuf.toString());
		return sqlBuf.toString();
	}

	/**
	 * 查询数据库中最近报警的人的最新 alarmSize 条报警记录  y 1220
	 * @param alarmIds 查询的报警的人的报警id集合
	 * @param alarmSize 查询每个人的多少条记录
	 * @return
	 */
	public String findPeopleRealTimeAlarm(@Param("alarmIds") List<String> alarmIds,@Param("alarmSize") Integer alarmSize){
		StringBuffer sql=new StringBuffer("SELECT a.*, b.cameraname,b.y latitude,b.x longitude,c.conreason reason ,c.id bk_id FROM(SELECT id, cameraid, " +
				"controlpeopleid, NAME, sex, cardnumber idCard, createtime alarm_time, score similar, `status`, snappicpath alarm_image_path," +
				" peoplepicpath bk_image_path, snappicbgpath backgroundImagePath FROM jh_alarm WHERE id in ( 0, ");
		for(String s:alarmIds){
			if(s.contains(",")){
				String[] strings = s.split(",");
				if(strings.length>=alarmSize){
					for(int x=0;x<alarmSize;x++){
						sql.append(strings[x]+",");
					}
				}else {
					for(int x=0;x<strings.length;x++){
						sql.append(strings[x]+",");
					}
				}
			}else {
				sql.append(s+",");
			}
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" ) ) a LEFT JOIN jh_camera b ON a.cameraid = b.cameraid LEFT JOIN jh_controlpeople c ON a.controlpeopleid = c.id ORDER BY a.alarm_time DESC ");
        log.debug(sql.toString());
		return sql.toString();
	}
	/**
	 * 查询一段时间内某些分组下的报警信息 hyl
	 * @param
	 * @return
	 */
	public String findAlarmByGroupId(Map map){
		List<String> list = (List<String>)map.get("param1");
		String begintime = (String)map.get("param2");
		String endtime = (String)map.get("param3");
		StringBuffer sql=new StringBuffer("SELECT  ra.id,ra.alarmid," +
				"  ra.controlpeopleid," +
				"  ra.name," +
				"  ra.sex," +
				"  ra.cardnumber," +
				"  ra.alarmpicid," +
                "  ra.status,"+
				"  ra.handletype," +
				"  ra.handleremark," +
				"  ra.score," +
				"  ra.`createtime`," +
				"  ra.managerid," +
				"  ra.managername," +
                "  ra.managetime," +
                "  ra.cameraid," +
                "  ra.snappicpath," +
                "  ra.cameraid," +
                "  ra.arcscore," +
                "  ra.projectid," +
				"  ra.remark," +
				"  ra.x," +
				"  ra.y," +
				"  ra.cameraname," +
				"  tbg.id as controltypeid" +
				"  FROM" +
				"  jh_controlpeople bk" +
				"  LEFT JOIN (select jh_alarm.*,jh_camera.cameraname,jh_camera.cameraip,jh_camera.x,jh_camera.y from jh_alarm,jh_camera WHERE jh_alarm.cameraid=jh_camera.cameraid) ra ON ra.controlpeopleid = bk.id" +
				"  LEFT JOIN jh_controlpeople_type tbg ON bk.contorltype=tbg.id" +
				"  WHERE" +
				"  1 = 1" +
				"  AND");
		if (begintime != null){
			sql.append("  ra.createtime >= '").append(begintime).append("' ");
		}
		if(endtime != null){
			sql.append("  AND ra.createtime <= '").append(endtime).append("'  AND tbg.id in(");
		}
		if(null != list && list.size()>0){
			for (int i=0;i<list.size();i++) {
				String number = list.get(i);
				if(i<list.size()-1 ){
					sql.append("'");
					sql.append(number).append("',");
				}else{
					sql.append("'");
					sql.append(number).append("'");
				}
			}
			sql.append(") ");
		}
        log.debug(sql.toString());
		return sql.toString();
	}

	/**
	 * 查询某些相机某个时间段下所有的报警记录
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param cameraIds  相机id集合
	 * @param startNum 开始条数
	 * @param pageSize 每页记录数
	 * @return
	 */
	public  String findAlarmByCameraIds(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("cameraIds") List<String> cameraIds,@Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize){
		StringBuffer sql = new StringBuffer("SELECT * FROM tb_realtime_alarm WHERE alarm_time >=");
		sql.append("'").append(startTime).append("' AND alarm_time <= ").append("'").append(endTime).append("'");
		if( null != cameraIds){
			sql.append(" and camera_id in (");
			for(String cameraId :cameraIds){
				sql.append(cameraId).append(",");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		sql.append( "limit ").append(startNum).append(",").append(pageSize);
		return  sql.toString();
	}

	/**
	 * 查询某些相机某个时间段下所有的报警记录总条数
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param cameraIds  相机id集合
	 * @return
	 */
	public  String findAlarmByCameraIdsCount(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("cameraIds") List<String> cameraIds){
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM tb_realtime_alarm WHERE alarm_time >=");
		sql.append("'").append(startTime).append("' AND alarm_time <= ").append("'").append(endTime).append("'");
		if( null != cameraIds){
			sql.append(" and camera_id in (");
			for(String cameraId :cameraIds){
				sql.append(cameraId).append(",");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		return  sql.toString();
	}

    /**
     * 根据给定的布控id, 查询报警表,筛选出来报警过的布控id
     * @param ids   y 0119
     * @return
     */
	public String findAlarmBuKongIds(List<String> ids){
		if( null != ids && ids.size()>0){
            StringBuffer sql = new StringBuffer("SELECT controlpeopleid FROM jh_alarm WHERE controlpeopleid  ");
			sql.append("in (");
			for(String id: ids){
				sql.append(id).append(", ");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
            sql.append(" GROUP BY controlpeopleid");
            return  sql.toString();
		}
		StringBuffer sql = new StringBuffer("SELECT controlpeopleid FROM jh_alarm WHERE id = -1");
		return sql.toString();
	}
}
