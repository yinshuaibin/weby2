package com.ferret.dao.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator;
 * @since 2018/1/16;
 * 修改人:y
 * 修改内容:删除了一个不需要的方法,修改根据页面传递过来的相机选择数据,查询对应的ip
 */
public class CameraInfoMapperProvider {
	public String number2Id(Map map) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("Select id from tb_camera_info where  1=1 ");
		String numbers = (String) map.get("numbers");
		String like = (String) map.get("like");
		if (StringUtils.isNotBlank(numbers))
			sqlBuffer.append("and number in ( ").append(numbers).append(")");
		if (StringUtils.isNotBlank(like))
			sqlBuffer.append("or  ( ").append(like).append(")");
		return sqlBuffer.toString();
	}

	/**
	 * 根据相机number查询相机id, 动态拼接sql语句
	 * 
	 * @param map
	 * @return
	 */
	public String findIdByNumberSql(Map map) {
		String cameraBuilder = (String) map.get("param1");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id FROM tb_camera_info WHERE number IN ( ");
		sql.append(cameraBuilder);
		sql.append(") AND enabled = 1");
		return sql.toString();
	}

	/**
	 * 根据页面传递过来的相机id集合,如果是地区id或者相机分组,则加C,全部以like形式查询出对应的ip
	 *  修改人: y
	 *  修改时间:0720
	 * @param selectCamera
	 * @return
	 */
	public static String findCameraINfoBySelect(@Param("selectCamera") List<String> selectCamera) {
		StringBuffer sb = new StringBuffer("SELECT a.*, b. NAME placeName FROM(Select * from jh_camera where number");
		if(selectCamera !=null && selectCamera.size()>0){
            String number = selectCamera.get(0);
            if (number.startsWith("C")) {
                sb.append(" = '" + number + "' ");
            } else {
                sb.append(" like 'C" + number + "%' ");
            }
            if (selectCamera.size() > 1) {
                for (int x = 1; x < selectCamera.size(); x++) {
                    String cameraId = selectCamera.get(x);
                    if (cameraId.startsWith("C")) {
                        sb.append("or number = '" + cameraId + "'");
                    } else {
                        sb.append("or number like 'C" + cameraId + "%'");
                    }
                }
            }
            sb.append(" and ");
        }
		sb.append(" status = 1 )a LEFT JOIN tb_dictionary b ON a.mark_dictionary = b.number");
		return sb.toString();
	}
}
