package com.ferret.service.common;

import com.ferret.bean.Background;

public interface IBackgroundImageService {

	Background findBackgroundImage(Integer historypassId);

	/**
	 * 根据历史记录图片的特征id查询对应的背景图
	 * @param featureId 历史记录图片特征id
	 * @return 图片http地址
	 */
	String getBackgroundImgByFeatureId(Long featureId);
}
