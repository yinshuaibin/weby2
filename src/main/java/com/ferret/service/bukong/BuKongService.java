package com.ferret.service.bukong;

import java.util.List;
import com.ferret.bean.BuKong;
import com.ferret.bean.ImageFeature;
import com.ferret.bean.InterfaceBean.BuKongJH;
import com.ferret.bean.User;
import com.ferret.bean.bukong.NewBuKong;
import com.ferret.bean.bukong.RevokeBuKong;
import com.ferret.bean.pagebean.BuKongPage;
import com.ferret.bean.pagebean.SelectBuKongPage;
import com.ferret.dto.PageDTO;

public interface BuKongService {

	/**
	 * 单个添加布控信息   y 1224
	 * 使用接口实时添加
	 * @param buKongJH  实体类
	 */
	String addOneBuKong(BuKongJH buKongJH);

	/**
	 * 添加多个布控人员   y 1224
	 * 使用接口实时添加
	 * @param buKongJHS 实体类集合
	 */
	String addBuKongList(List<BuKongJH> buKongJHS);


	/**
	 * 单人布控删除  y 1225
	 * @param businessid  唯一业务id
	 * @return
	 */
	public String delBuKongByBusinessid(String businessid);

	/**
	 * 批量布控删除   y 1225
	 * @param ids 布控id集合
	 * @return
	 */
	public String delBuKongByIds(List<String> ids);

	/**
	 * 删除布控分组下的所有布控信息
	 * @param contorlType 布控分组id  y 1225
	 * @return
	 */
	public String delBuKongByContorlType(String contorlType);

	/**
	 * 修改布控状态,达到删除/恢复布控的效果 y 1224
	 * 使用接口实时修改
	 * @param businessid 唯一业务id
	 * @param status 状态码: 0:不生效,1:生效
	 */
	String updateBukongStatus(String businessid,String status);

	/**
	 * 修改布控状态,达到删除/恢复布控的效果   y 1225
	 * 使用接口实时修改
	 * @param businessids  唯一业务id集合
	 * @param status 状态码: 0:不生效, 1:生效
	 * @return
	 */
	String updateBukongStatus(List<String> businessids, String status);

	/**
	 * 修改布控信息   y 1224
	 * @param buKongJH
	 */
	String uptadeBuKong(BuKongJH buKongJH);

	/**
	 * 根据布控分组id分页查询布控信息  y1224
	 * @param contorType
	 * @return
	 */
	List<BuKongJH> findBuKongByContorlType(String contorType,Integer pageSize,Integer pageNum);

	/**
	 * 根据布控分组id查询该分组下总布控条数  y1224
	 * @param contorType
	 * @return
	 */
	Integer findBuKongByContorlTypeCount(String contorType);

	/**
	 * 条件模糊分页查询布控信息   y 1225
	 * @param controTypeIds  布控分组id
	 * @param reason         用来模糊匹配的条件
	 * @param startTime      布控开始时间
	 * @param endTime        布控结束时间
	 * @param pageSize       每页记录数
	 * @param pageNum        当前页数
	 * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空 '' 或 null
	 * @return
	 */
	List<BuKongJH> findBuKongResultList(List<String> controTypeIds, String reason,String startTime,String endTime,Integer pageSize,Integer pageNum,Integer status);

	/**
	 * 条件模糊分页查询布控信息   y 1225
	 * @param controTypeIds  布控分组id
	 * @param reason         用来模糊匹配的条件
	 * @param startTime      布控开始时间
	 * @param endTime        布控结束时间
	 * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空  '' 或 null
	 * @return
	 */
	Integer findBuKongListCount(List<String> controTypeIds, String reason,String startTime,String endTime,Integer status);
}
