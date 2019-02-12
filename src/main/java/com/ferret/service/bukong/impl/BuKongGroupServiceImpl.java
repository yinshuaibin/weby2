package com.ferret.service.bukong.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ferret.bean.InterfaceBean.BuKongContorlType;
import com.ferret.dao.*;
import com.ferret.service.dynamic.ImageDynamicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ferret.service.bukong.BuKongGroupService;

@Service("buKongGroupService")
public class BuKongGroupServiceImpl implements BuKongGroupService{
	
	@Resource
	private BuKongGroupMapper bukongGroupMapper;

	@Resource
	private BuKongMapper buKongMapper;

	@Resource
	private ImageDynamicService imageDynamicService;

	/**
	 * 分页查询所有布控分组   y 1225
	 * @return
	 */
	@Override
	public List<BuKongContorlType> findAllBuKongGroup(int pageNum, int pageSize){
		return bukongGroupMapper.findAllBuKongGroup((pageNum -1)*pageSize,pageSize);
	}

	/**
	 * @Description 查询所有布控分组总数
	 * @date 2019-01-22 15:47:07
	 * @author xieyingchao
	 */
    @Override
    public int findAllBuKongGroupCount() {
        return bukongGroupMapper.findAllBuKongGroupCount();
    }

    @Override
    public List<BuKongContorlType> findAllBuKongGroup() {
        return bukongGroupMapper.findAllBuKongGroupList();
    }

    /**
	 * 校验分组名称  分组名称不能重复   y 1226
	 * @param name
	 * @return
	 */
	@Override
	public String checkBuKongGroupName(String name){
		if(!StringUtils.isNotBlank(name)){
			return "请传入正确的参数";
		}
        int groupByGroupName = bukongGroupMapper.findGroupByGroupName(name);
        if(groupByGroupName < 1){
            return "success";
        }
        return "该名称已被占用,请换个试试";
	}

	/**
	 * 添加布控分组  y 1226
	 * @param buKongGroup
	 * @return
	 */
	@Override
	public String addBuKongGroup(BuKongContorlType buKongGroup){
        int i = bukongGroupMapper.addBuKongGroup(buKongGroup);
        if( i == 1){
            return "success";
        }
        return "添加失败,请重试";
	}

	/**
	 * 根据布控分组id删除该布控分组(如果该分组下有布控数据,则不允许删除)  y 1226
	 * @param buKongGroupId
	 * @return
	 */
	@Override
	public String delBuKongGroup(String buKongGroupId){
		List<String> ids = buKongMapper.findBuKongIdsByContorlType(buKongGroupId);
		if( null != ids && ids.size()>0){
			return "禁止删除有布控数据的布控分组!";
		}
        // 如果没有布控数据,则删除该分组
        bukongGroupMapper.delBuKongGroup(buKongGroupId);
        return "success";
	}

	/**
	 * 修改布控分组信息   y 1226
	 * @param buKongGroup
	 * @return
	 */
	@Override
	public String updateBuKongGroup(BuKongContorlType buKongGroup){
        int i = bukongGroupMapper.updateBuKongGroup(buKongGroup);
        if(i == 1){
            return "success";
        }
        return "删除失败,请重试";
	}

	/**
	 * @Description  根据布控分组Id修改布控分组布控状态
	 * @param isControl 0：否 1：是
	 * @param id 布控分组Id
	 * @date 2019-01-22 09:40:28
	 * @author xieyingchao
	 */
    @Override
    public String updateBuKongIscontrol(int isControl, int id) {
        int status = bukongGroupMapper.updateBuKongIscontrol(isControl,id);
        boolean reloadFeature = imageDynamicService.reloadFaceSearch();
        if(status == 1 && reloadFeature){
            return "success";
        }
        return "修改状态失败,请重试";
    }
}
