package com.ferret.dao.provider;

import com.ferret.bean.InterfaceBean.BuKongJH;
import com.ferret.bean.pagebean.SelectBuKongPage;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public class BuKongMapperProvider {

	/**
	 * 条件模糊分页查询布控信息sql拼接   y 1225
	 * @param controTypeIds  布控分组id
	 * @param reason         用来模糊匹配的条件
	 * @param startTime      布控开始时间
	 * @param endTime        布控结束时间
	 * @param pageSize       每页记录数
	 * @param startNum       分页开始条数
     * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空   '' 或 null
     * @return
	 */
	public String findBuKongResultList(@Param("controTypeIds") List<String> controTypeIds, @Param("reason") String reason,
										@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pageSize") Integer pageSize,
										@Param("startNum") Integer startNum,@Param("status")Integer status){
		StringBuffer sql = new StringBuffer("SELECT a.*,b.typename groupName from (select * from jh_controlpeople");
		findBuKongListSqlAppend(controTypeIds,reason,startTime,endTime,status,sql);
		sql.append(" limit ").append(startNum).append(" , ").append(pageSize);
		sql.append(") a LEFT JOIN jh_controlpeople_type b on a.contorltype = b.id");
		return  sql.toString();
	}

	/**
	 * 条件模糊分页查询布控信息sql拼接   y 1225
	 * @param controTypeIds  布控分组id
	 * @param reason         用来模糊匹配的条件
	 * @param startTime      布控开始时间
	 * @param endTime        布控结束时间
     * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空   '' 或 null
     * @return
	 */
	public String findBuKongListCount(@Param("controTypeIds") List<String> controTypeIds, @Param("reason") String reason,
								@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("status")Integer status){
		StringBuffer sql = new StringBuffer("select count(id) from jh_controlpeople ");
		findBuKongListSqlAppend(controTypeIds,reason,startTime,endTime,status,sql);
		return  sql.toString();
	}

	private void findBuKongListSqlAppend(List<String> controTypeIds, String reason,
												 String startTime, String endTime,Integer status,StringBuffer sql){
		if(null != controTypeIds && controTypeIds.size()>0){
			sql.append(" where contorltype in (");
			for (String controTypeId: controTypeIds){
				sql.append(controTypeId).append(",");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
			if(StringUtils.isNotBlank(startTime)) sql.append(" and createtime >= '").append(startTime).append("' ");
			if(StringUtils.isNotBlank(endTime)) sql.append(" and createtime <= '").append(endTime).append("' ");
			if(StringUtils.isNotBlank(reason)) {
				sql.append(" AND ( name like '%").append(reason).
						append("%' OR cardnumber like '%").append(reason).
						   append("%' OR conage like '%").append(reason).
						   append("%' OR contel like '%").append(reason).
						   append("%' OR conreason like '%").append(reason).
						   append("%' OR address like '%").append(reason).
						   append("%' OR remark like '%").append(reason).append("%' ) ");
			}
			if(null !=status && !status.equals("")){
			    sql.append(" and status = ").append(status);
            }
		}
	}
}
