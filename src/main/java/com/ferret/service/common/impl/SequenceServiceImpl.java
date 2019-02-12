package com.ferret.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferret.service.common.SequenceService;
import com.ferret.dao.SequenceMapper;

@Service
public class SequenceServiceImpl implements SequenceService{

	/**
	 * @return 返回指定序列的下一个值
	 * */
	@Autowired
	private  SequenceMapper sequenceMapper;

	public   String nextVal(String seqName) {
		return sequenceMapper.nextVal(seqName);
	}
	
	/**
	 * @param area：410101 郑州市
	 * @return 返回新生成的角色编号
	 * */
	public  String createRoleId(String area) {
		return roleIdMark + area + sequenceMapper.nextVal("roleid");
	}
	
	
	/**
	 * @param area 区域编号，placeId 4位 场所编号 场所指：旅馆，网吧，火车站，汽车站等
	 * @return String cameranumber
	 * */
	public  String createCameraId(String area,String placeId) {
		return cameraIdMark+area+placeId+ sequenceMapper.nextVal(cameraNumber);
	}
	public  String createCameraId(String groupNumber) {
		return cameraIdMark+groupNumber+ sequenceMapper.nextVal(cameraNumber);
	}
	/**
	 * @return 生成返回布控分组的编号
	 * */
	public  String createBKGroupId(String area) {
		return bkGroupIdMark + area + sequenceMapper.nextVal("bkgroup");
	}

	/**
	 * @return 生成相机分组id 分组是指街道、道路名称等
	 * */
	public  String createCameraGroup() {
		return sequenceMapper.nextVal(cameraGroup);
	}
}
