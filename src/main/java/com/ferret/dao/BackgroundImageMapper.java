package com.ferret.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.ferret.bean.Background;

@Mapper
@Repository
public interface BackgroundImageMapper {

	/**
	 * 根据特征id查询对应的背景图片地址<br>
	 * <b>其中tb_historypass.id = tb_bakimageinfo.id,小图和背景大图一一对应</b>
	 * @author huyunlong
	 * @param id 特征id
	 * @since 2018-07-13 17:27
	 * @return background对象
	 */
	@Select("SELECT ID,workflag,localtime_date,localtime_time,bakimagepath from tb_bakimageinfo where ID=#{ID}")
	Background findBgById(Integer ID);

	/**
	 * 根据特征id查询对应的背景图片地址<br>
	 * <b>其中tb_historypass.id = tb_bakimageinfo.id,小图和背景大图一一对应</b>
	 * @author cc
	 * @param featureId 特征id
	 * @since 2018-07-06 11:55
	 * @return 背景图片地址
	 */
	@Select("SELECT b.bakimagepath from tb_historypass h, tb_bakimageinfo b where h.id = b.id and h.globlefeatureid = #{featureId}")
	String getBackgroundImageByFeatureId(Long featureId);

}
