package com.ferret.controller;

import com.ferret.service.common.impl.BackgroundImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ferret.bean.Background;

@RestController
public class BackgroundImageController extends BaseController {

	/**
	 * 根据图片特征id查询背景图
	 * 
	 * @author huyunlong
	 * @param id
	 *            图片特征id
	 * @return background实体对象
	 */
	@Autowired
	private BackgroundImageServiceImpl backgroundImageService;

	// 通过提供的背景图片id查看背景图片 BII=Background Image Id
	@RequestMapping(value = "/findBgImage"/* ,method=RequestMethod.POST */)
	public Background findBgImage(Integer id) {
		return backgroundImageService.findBackgroundImage(id);
	}

	/**
	 *
	 * 根据图片特征id查询背景图信息
	 * 
	 * @author cc
	 * @param featureId
	 *            图片特征id
	 * @return response
	 */
	@RequestMapping(value = "/backgroundImages/{featureId}", method = RequestMethod.GET)
	public ResponseEntity getBackgroundImageByFeatureId(@PathVariable("featureId") Long featureId) {
		String path = backgroundImageService.getBackgroundImgByFeatureId(featureId);
		return ResponseEntity.ok(path);
	}
}
