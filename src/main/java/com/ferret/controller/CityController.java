package com.ferret.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.CameraInfo;
import com.ferret.bean.User;
import com.ferret.utils.Tree;
import com.ferret.service.camera.cameraInfo.CameraInfoService;
import com.ferret.service.camera.cameragroup.CameraGroupService;
import com.ferret.service.common.city.CityService;

/**
 * @pack: com.ferret.controller;
 * @auth: Administrator;
 * @since: 2017/12/15 0015;
 * @desc:
 */
@RestController
public class CityController extends BaseController {
	@Autowired
	private CityService cityService;
	@Autowired
	private CameraGroupService cameraGroupService;
	@Autowired
	private CameraInfoService cameraInfoService;

	private boolean expand = false;

	/**
	 * 某一节点的城市树
	 * 修改:不再从后台session中获取用户地区信息,改为从前台获取  y 0814
	 * 未解决:用户未登录问题
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/cities/{code}", method = RequestMethod.GET)

	// 1.needGroup 和 needInfo 为false(0) 仅显示城市树
	// 2.needGroup 为 true(1) needInfo 为false(0) 显示城市树 相机分组
	// 3.needGroup 和 needInfo 为true(1) 显示城市树 相机分组 摄像头组

	public List<Map<String, Object>> citys(@PathVariable(value = "code") Integer code, Boolean needGroup,
			Boolean needInfo, HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        Assert.notNull(user,"用户登录超时或者后台服务已重启,请重新登录!");
		List<Map<String, Object>> citys = cityService.findByCode(code);
		/** 城市信息添加到tree */
		Tree tree = new Tree("id", "code", "pCode", "children", "name");

		citys = tree.toIviewTree(citys, true);

		if (needGroup || needInfo) {
			getCameraGroup(citys, needGroup, needInfo);
		}
		return citys;
	}
	
	/**
	 * 页面需要几个特定的区域,所以此处多了一个方法,返回固定的几个地区
	 * @param code
	 * @param needGroup false
	 * @param needInfo false
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/citiesTest/{code}", method = RequestMethod.GET)
	public List<Map<String, Object>> citysTest(@PathVariable(value = "code") Integer code, Boolean needGroup,
			Boolean needInfo, HttpServletRequest req) {
		
		List<Map<String, Object>> citys = cityService.findCityTest();
		/** 城市信息添加到tree */
		Tree tree = new Tree("id", "code", "pCode", "children", "name");

		citys = tree.toIviewTree(citys, true);

		if (needGroup || needInfo) {
			getCameraGroup(citys, needGroup, needInfo);
		}
		return citys;
	}

	/** 相机分组下的摄像头 */
	private void getCameraInfo(List<Map<String, Object>> cameraGroupTree, Boolean needInfo) {
		for (Map<String, Object> map : cameraGroupTree) {
			List<Map<String, Object>> child = (List<Map<String, Object>>) map.get("children");
			if (null != child) {
				getCameraInfo(child, needInfo);
			} else {
				// group
				String name = String.valueOf(map.get("name"));
				map.put("expand", expand);
				map.put("title", name);
				if (needInfo) {
					// info
					String id = String.valueOf(map.get("number"));
					List<CameraInfo> cameraInfos = cameraInfoService.findByCameraInfoByNumber(id);
					List<JSONObject> list = new ArrayList<>();
					for (CameraInfo info : cameraInfos) {
						JSONObject object = (JSONObject) JSON.toJSON(info);
						object.put("title", info.getName());
						object.put("expand", expand);
						list.add(object);
					}
					map.put("children", list);
				}
			}
		}
	}

	/** 城市区域下相机分组 */
	private void getCameraGroup(List<Map<String, Object>> cityTree, Boolean needGroup, Boolean needInfo) {
		for (Map<String, Object> map : cityTree) {
			List<Map<String, Object>> child = (List<Map<String, Object>>) map.get("children");
			if (null != child) {
				getCameraGroup(child, needGroup, needInfo);
			} else {
				String number = String.valueOf(map.get("number"));
				if (needGroup) {
					List<Map<String, Object>> cameraGroups = cameraGroupService.findByCameraGroupNumber(number);
					// 根据group获取下级info
					getCameraInfo(cameraGroups, needInfo);
					map.put("children", cameraGroups);
				}
			}
		}
	}

}
