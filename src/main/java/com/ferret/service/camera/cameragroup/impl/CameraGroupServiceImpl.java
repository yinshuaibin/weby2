package com.ferret.service.camera.cameragroup.impl;

import com.ferret.bean.CameraGroup;
import com.ferret.exception.BusinessException;
import com.ferret.service.camera.cameragroup.CameraGroupService;
import com.ferret.service.common.SequenceService;
import com.ferret.dao.CameraGroupMapper;
import com.ferret.dao.CameraInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.service.cameragroup.impl;
 * @auth: Administrator;
 * @since: 2017/12/21 0021;
 * @desc:
 */
@Service
public class CameraGroupServiceImpl implements CameraGroupService {

	@Autowired
	private CameraGroupMapper cameraGroupMapper;
	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private CameraInfoMapper cameraInfoMapper;

	/** 增加相机分组信息 */
	@Override
	public CameraGroup saveCameraGroup(CameraGroup cameraGroup) {

		Assert.notNull(cameraGroup, "保存数据,cameraGroup不能为空");

		// 获取number唯一序列值
		System.out.println("添加相机分组时需要的参数:   " + cameraGroup);
		String seq = sequenceService.createCameraGroup();

		// 相机分组number为6位行政区域码 + 4位唯一的序列值
		String cameraGroupNum = cameraGroup.getNumber() + seq;

		cameraGroup.setNumber(cameraGroupNum);

		int cameraGroupType = cameraGroupMapper.insertCameraGroup(cameraGroup);

		if (cameraGroupType < 1) {

			throw new BusinessException("保存数据失败");
		}
		return cameraGroup;

	}

	/** 修改相加分组信息 */
	@Override
	public void updateCameraGroup(CameraGroup cameraGroup) {
		Assert.notNull(cameraGroup, "保存数据,cameraGroup不能为空");

		int cameraGroupTypes = cameraGroupMapper.updateCameraGroup(cameraGroup);

		if (cameraGroupTypes < 1) {

			throw new BusinessException("修改数据失败");
		}
	}

	/** 根据ID查询相机分组信息 */
	@Override
	public CameraGroup findById(Integer id) {

		CameraGroup cameraGroup = cameraGroupMapper.findById(id);

		return cameraGroup;
	}

	/** 删除相机分组信息 */
	@Override
	public void deleteCameraGroup(Integer id) {
		cameraGroupMapper.deleteCameraGroup(id);
	}

	/** 根据number查询相机分组数据 */
	@Override
	public List<Map<String, Object>> findByCameraGroupNumber(String number) {
		return cameraGroupMapper.findByCameraGroupNumber(number);
	}
    /**
     * @Description 根据number查询相机分组数据
     * @param number 相机number
     * @param pageNum 起始个数
     * @param pageSize 页数
     * @date 2019-01-02 17:02:50
     * @author xieyingchao
     */
	@Override
	public List<CameraGroup> findByCameraGroupCount(String number,Integer pageNum, Integer pageSize) {
	    int startNum = (pageNum-1) * pageSize;
		return cameraGroupMapper.findByCameraGroupCount(number,startNum,pageSize);
	}

    @Override
    public int findByCameraGroupTotalNum(String number) {
        return cameraGroupMapper.findByCameraGroupTotalNum(number);
    }

    /** 查询所有相机分组数据 */
	@Override
	public List<Map<String, Object>> findAllCameraGroup() {

		List<Map<String, Object>> list =

				cameraGroupMapper.findAllCameraGroup();

		return list;
	}

	@Override
	public int selectSquences() {
		int selectSquences = cameraInfoMapper.selectSquences();
		return selectSquences;
	}
	/** 根据name查询相机分组数据 */
	@Override
	public int selectCarmeraGroupByName(String name){
		List<CameraGroup> list=cameraGroupMapper.findByName(name);
		return list.size();
	}

}
