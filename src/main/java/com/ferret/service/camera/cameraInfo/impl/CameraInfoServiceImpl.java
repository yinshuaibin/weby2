package com.ferret.service.camera.cameraInfo.impl;

import com.ferret.bean.CameraInfo;
import com.ferret.common.base.Common;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.dao.UserCameraMapper;
import com.ferret.dto.PageDTO;
import com.ferret.exception.BusinessException;
import com.ferret.exception.ServiceException;
import com.ferret.service.camera.cameraInfo.CameraInfoService;
import com.ferret.service.common.SequenceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.service.cameraInfoservice.impl;
 * @auth: Administrator;
 * @since: 2017/12/15 0015;
 * @desc:
 */
@Service
public class CameraInfoServiceImpl implements CameraInfoService,Common {

    @Autowired
    private CameraInfoMapper cameraInfoMapper;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private UserCameraMapper userCameraMapper;

    /** 海康相机 */
    private String CAMERA_TYPE_HK = "01";
    /** 大华相机 */
    private String CAMERA_TYPE_DH = "01";

    /**
     * 增加数据
     *
     * @param cameraInfo
     */
    @Override
    public CameraInfo saveCameraInfo(CameraInfo cameraInfo) {
        Assert.notNull(cameraInfo, "添加数据时,cameraInfo不能为空");
        String camreaId = null;
        String number = sequenceService.createCameraId(cameraInfo.getGroupId());
        if(null != cameraInfo.getManufacturer()) {
            switch (cameraInfo.getManufacturer()) {
                case "1":
                    camreaId = cameraInfo.getIp() + "_" + CAMERA_TYPE_HK;
                    cameraInfo.setCameraId(camreaId);
                    break;
                case "2":
                    camreaId = cameraInfo.getIp() + "_" + CAMERA_TYPE_DH;
                    cameraInfo.setCameraId(camreaId);
                    break;
                default:
                    break;
            }
            cameraInfo.setNetPort(Integer.valueOf(cameraInfo.getManufacturer()));
        }
        cameraInfo.setAddTime(new Date());
        cameraInfo.setEnabled(true);
        cameraInfo.setNumber(number);
        int cameraInfoType = cameraInfoMapper.insertCameraInfo(cameraInfo);
        if (cameraInfoType < 1) {
            throw new BusinessException("保存数据失败");
        }
        return cameraInfo;
    }

	/**author：hyl
	 * 检查相机是否重名
	 * @param name
	 * @return
	 */
	@Override
	public int checkCameraName(String name,Integer id) {
        /** 查询对象为空，返回0表示成功可以添加 **/
        List<CameraInfo> tempCamera = cameraInfoMapper.selectCameraName(name);
        if(tempCamera.size()==0){
            return 0;
        }else{
            if(id==null){//如果传过来的id为空，则为新增方法，否则为修改方法
                return 1;
            }else{
                if(id.equals(tempCamera.get(0).getId())){//如果传过来的id=查到的id则当前重复数据为自身，否则为其他
                    return 0;
                }else {
                    return 1;
                }
            }
        }
	}

	/**author：hyl
	 * 检查相机ip是否重复
	 * @param ip
	 * @return
	 */
	@Override
	public int checkCameraIp(String ip,Integer id) {
        /** 查询对象为空，返回0表示成功可以添加 **/
        List<CameraInfo> tempCamera = cameraInfoMapper.selectCameraIp(ip);
        if(tempCamera.size()==0){
            return 0;
        }else{
            if(id==null){//如果传过来的id为空，则为新增方法，否则为修改方法
                return 1;
            }else{
                if(id.equals(tempCamera.get(0).getId())){//如果传过来的id=查到的id则当前重复数据为自身，否则为其他
                    return 0;
                }else {
                    return 1;
                }
            }
        }
	}
	/**author：hyl
	 * 检查相机ip是否被占用
	 * @param ip
	 * @return
	 */
	public int checkCameraIpOccupy(Integer id,String ip){
		//获取查询的相机信息
		CameraInfo cameraInfo=cameraInfoMapper.findById(id);
		String cip=cameraInfo.getIp();
		if(cip.equals(ip)){
			return 0;
		}else{
			int temp = cameraInfoMapper.selectCameraIpFrom(cip);
			return temp;
		}
	}
	public int delectcheckIp(String ip){
		return cameraInfoMapper.selectCameraIpFrom(ip);
	}

	/**
	 * 修改数据
	 *
	 * @param cameraInfo
	 */
	@Override
	public int updateCameraInfo(CameraInfo cameraInfo) {
		if (cameraInfo == null) {
			throw new ServiceException("保存数据时,cameraInfo不能为空");
		}
		int flag = 1;
		if (StringUtils.equals(getAreaId(cameraInfo.getGroupId()), getAreaId(cameraInfo.getNumber().substring(1)))) {
			int cameraInfoTypes = cameraInfoMapper.updateCameraInfo(cameraInfo);
			if (cameraInfoTypes >= 1) {
				flag = 0;
			}
		}
		return flag;

	}


    /**
     * 删除数据
     *
     * @param id
     */
    @Override
    public void deleteCameraInfo(Integer id) {

        int cameraInfoType =  cameraInfoMapper.deleteCameraInfo(id);
        if (cameraInfoType < 1) {
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public CameraInfo findByID(Integer id) {

        CameraInfo cameraInfo =
                cameraInfoMapper.findById(id);

        return cameraInfo;
    }


    @Override
    public int findCount() {
         int cameraCount=cameraInfoMapper.findCount();
        if(cameraCount<1){
            throw new BusinessException("获取总条数失败");
        }
        return cameraCount;
    }

    /**
     * 根据group_id查询
     *
     * @param group_id 对应相机id字段值
     * @return
     */
    @Override
    public List<CameraInfo> findByCameraInfoByNumber(String group_id) {

//        List<Map<String, Object>> listCameraInfo = (List<Map<String, Object>>)cacheMap.get(groupId);
//
//        if (null == listCameraInfo){
//            listCameraInfo = cameraInfoMapper.findByCameraInfoByNumber(groupId);
//            cacheMap.put(groupId,listCameraInfo);
//        }

        return cameraInfoMapper.findByCameraInfoByNumber(group_id);
    }

    @Override
    public PageDTO<CameraInfo> listCamerasByUserId(Integer pageNo, Integer pageSize, String userId) {


        // 获取角色权限对应的摄像头
        List<String> cameraNumber = userCameraMapper.getCameraNumByUserId(userId);

        // 判断是否有摄像头数据.
        Assert.notEmpty(cameraNumber,"用户无权限获取对应的摄像头列表");

        // 查询并分页,PageHelper的lambda写法
        Page<CameraInfo> page = PageHelper.startPage(pageNo,pageSize)
                .doSelectPage(()->{
                    cameraInfoMapper.listCamerasByRoleId(cameraNumber);
                }
        );
        // 新建一个pageDTO对象
        return new PageDTO<>(page.getResult(),page.getTotal());
    }

    /**
     * 获取 已添加相机的数量
     * @return
     */
    @Override
    public Map addedCameraNum() {
        Map<String,Object> map = new HashMap<>();

        int cameraMaxNum = cameraInfoMapper.addCameraMaxNum();
        int addedCameraNum = cameraInfoMapper.addedCameraNum();
        // 已经添加的相机数量，小于限制的最大数量
        if (addedCameraNum < cameraMaxNum) {
            map.put("status","success");
        } else {
            map.put("status","相机数量已达到最大限时，无法添加相机。");
        }
        return map;
    }

    /**
	 * 取数字字符串前6位,并且末尾不为0的值
	 * 
	 * @param id
	 * @return
	 */
	private String getAreaId(String id) {
		String reslut = null;
		String b = id.substring(0, 6);
		Integer parseInt = Integer.parseInt(b);
		if (parseInt % 10 != 0) {
			reslut = b;
		} else if (parseInt % 100 != 0) {
			reslut = b.substring(0, 5);
		} else if (parseInt % 1000 != 0) {
			reslut = b.substring(0, 4);
		} else if (parseInt % 10000 != 0) {
			reslut = b.substring(0, 3);
		} else {
			reslut = b.substring(0, 2);
		}
		return reslut;
	}
}
