package com.ferret.dao;

import com.ferret.bean.BuKong;
import com.ferret.bean.InterfaceBean.BuKongJH;
import com.ferret.bean.bukong.NewBuKong;
import com.ferret.bean.pagebean.SelectBuKongPage;
import com.ferret.dao.provider.BuKongMapperProvider;
import com.ferret.dto.BuKongDTO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BuKongMapper {

	/**
	 * 根据业务id修改布控信息  y 1224
	 * @param buKongJH
	 * @return
	 */
	@Update("update jh_controlpeople set contorltype = #{contorltype},conage = #{conage},name=#{name},sex=#{sex},cardnumber=#{cardnumber},address=#{address},conreason =#{conreason},remark=#{remark} where businessid =#{businessid}")
	int uptadeBuKongByBusinessid(BuKongJH buKongJH);

	/**
	 * 根据布控分组id分页查询布控信息  y 1224
	 * @param contorType
	 * @return
	 */
	@Select("select * from jh_controlpeople where contorltype= #{contorlType} limit #{startNum},#{pageSize}")
	@Results({
			@Result(property = "imagePath",column = "picpath")
	})
	List<BuKongJH> findBuKongByContorlType(@Param("contorlType") String contorType,@Param("pageSize") Integer pageSize,@Param("startNum") Integer startNum);

	/**
	 * 根据布控分组id查询该分组下总布控条数  y 1224
	 * @param contorlType
	 * @return
	 */
	@Select("select count(id) from jh_controlpeople where contorltype=#{contorlType}")
	int findBuKongByContorlTypeCount(String contorlType);

	/**
	 * 根据布控分组id查询所有的布控人员的id  y 1225
	 * @param contorlType
	 * @return
	 */
	@Select("select id from jh_controlpeople where contorltype= #{contorlType} ")
	List<String> findBuKongIdsByContorlType(String contorlType);

	/**
	 * 条件模糊分页查询布控信息   y 1225
	 * @param controTypeIds  布控分组id
	 * @param reason         用来模糊匹配的条件
	 * @param startTime      布控开始时间
	 * @param endTime        布控结束时间
	 * @param pageSize       每页记录数
	 * @param startNum       分页开始条数
	 * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空   '' 或 null
	 * @return
	 */
	@SelectProvider(type=BuKongMapperProvider.class,method="findBuKongResultList")
	@Results({
			@Result(property = "imagePath",column = "picpath")
	})
	List<BuKongJH> findBuKongResultList(@Param("controTypeIds") List<String> controTypeIds, @Param("reason") String reason,
										@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("pageSize") Integer pageSize,
										@Param("startNum") Integer startNum,@Param("status")Integer status);

	/**
	 * 条件模糊分页查询布控信息   y 1225
	 * @param controTypeIds  布控分组id
	 * @param reason         用来模糊匹配的条件
	 * @param startTime      布控开始时间
	 * @param endTime        布控结束时间
	 * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空   '' 或 null
	 * @return
	 */
	@SelectProvider(type=BuKongMapperProvider.class,method="findBuKongListCount")
	Integer findBuKongListCount(@Param("controTypeIds") List<String> controTypeIds, @Param("reason") String reason,
								@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("status")Integer status);

	/**
	 * 根据唯一业务id修改对应的布控信息以及年龄  y 1226
	 * @param businessid 唯一业务id
	 * @param conreason  布控原因
	 * @param age        年龄
	 * @return
	 */
	@Update("update  jh_controlpeople set conreason = #{conreason},conage= #{age}  where businessid=#{businessid}")
	int updateBukongConreason(@Param("businessid") String businessid,@Param("conreason") String conreason ,@Param("age")int age);

}