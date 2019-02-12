package com.ferret.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Select;

/**
 * 布控的id对应的布控表的dao
 * 每个布控的人对应多张布控图片
 * @author y
 *
 */
public interface BuKongImageMapper {

	/**
	 * 根据布控id查询对应的多张布控图片
	 * @param bkId
	 * @return
	 */
	@Select("select picpath from jh_controlpeople where id = #{bkId}")
	List<String> findBkImgByBkId(BigInteger bkId);
}
