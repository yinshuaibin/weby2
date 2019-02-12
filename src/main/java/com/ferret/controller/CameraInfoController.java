package com.ferret.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ferret.bean.CameraInfo;
import com.ferret.common.base.Common;
import com.ferret.dto.PageDTO;
import com.ferret.service.camera.cameraInfo.CameraInfoService;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.controller;
 * @auth: cc;
 * @since: 2017/12/15 0015;
 * @desc:
 */
@Slf4j
@RestController
public class CameraInfoController extends BaseController implements Common {
	@Autowired
	private CameraInfoService cameraInfoService;

	/**
	 * 增加摄像头数据
	 *
	 * @param cameraInfo
	 * @return
	 */
	@RequestMapping(value = "/cameras", method = RequestMethod.POST)
	public ResponseEntity doInsertCameraInfo(@RequestBody CameraInfo cameraInfo) {
		CameraInfo info = cameraInfoService.saveCameraInfo(cameraInfo);
		return new ResponseEntity<>(info, HttpStatus.CREATED);
	}

	/**
	 * 校验相机name
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/checkCamerasName", method = RequestMethod.GET)
	public int checkCamerasName(String name, Integer id){
        int namenum=cameraInfoService.checkCameraName(name,id);
		return namenum;
	}

	/**
	 * 校验相机ip
	 * @param ip
	 * @return
	 */
	@RequestMapping(value = "/checkCamerasIp",method = RequestMethod.GET)
	public int checkCamerasIp(String ip, Integer id){
		int ipnum=cameraInfoService.checkCameraIp(ip,id);
		return ipnum;
	}
	/**
	 * 校验相机ip是否被占用
	 * @param ip
	 * @return
	 */
	@RequestMapping(value = "/doesIPoccupy", method = RequestMethod.GET)
	public int doesIPoccupy(Integer id, String ip){
		return cameraInfoService.checkCameraIpOccupy(id,ip);
	}
	/**
	 * 删除时校验相机ip是否被占用
	 * @param ip
	 * @return
	 */
	@RequestMapping(value = "/delectcheckIp", method = RequestMethod.GET)
	public int delectcheckIp(String ip,Integer id){
		System.out.println(ip);
		return cameraInfoService.delectcheckIp(ip);
	}

	/**
	 * 修改摄像头数据
	 *
	 * @param cameraInfo
	 * @return
	 */
	@RequestMapping(value = "/cameras", method = RequestMethod.PUT)
	public ResponseEntity doUpdateCameraInfo(@RequestBody CameraInfo cameraInfo) {

		cameraInfoService.updateCameraInfo(cameraInfo);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * @Descriptions 删除摄像头数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/cameras/{id}", method = RequestMethod.DELETE)
	public ResponseEntity doDeleteCameraInfo(@PathVariable("id") Integer id) {
		cameraInfoService.deleteCameraInfo(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * 分页查询
	 *
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/cameras", method = RequestMethod.GET)
	public ResponseEntity findAll(Integer pageNo, Integer pageSize, String userId) {
		Assert.notNull(userId, "userId为空,请检查请求参数");
		PageDTO<CameraInfo> pageDTO = cameraInfoService.listCamerasByUserId(pageNo, pageSize, userId);
		return ResponseEntity.ok(pageDTO);
	}

	/**
	 * 总记录数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/camerasCount", method = RequestMethod.GET)
	public int count() {
		int cameraCount = cameraInfoService.findCount();
		return cameraCount;
	}

	/**
	 * 根据Id查询
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/cameras/{id}", method = RequestMethod.GET)
	public CameraInfo doFindByIdCameraInfo(@PathVariable("id") Integer id) {
		CameraInfo c = cameraInfoService.findByID(id);

		System.out.println(JSON.toJSONString(c, true));
		return c;
	}
	@RequestMapping(value = "/getCameraInfosByGroupId/{group_id}", method = RequestMethod.GET)
	public Map doFindCameraInfoByNumber(@PathVariable("group_id") String group_id) {
        Map<String,Object> map = new HashMap<>();
        List<CameraInfo> cameraInfos = cameraInfoService.findByCameraInfoByNumber(group_id);
        map.put("responseEntity",cameraInfos);
		return map;
	}
	/**
	 * 获取 已添加相机的数量
	 * @return
	 */
	@RequestMapping(value = "/addedCameraNum", method = RequestMethod.GET)
	public Map addedCameraNum() {
		return cameraInfoService.addedCameraNum();
	}
}
