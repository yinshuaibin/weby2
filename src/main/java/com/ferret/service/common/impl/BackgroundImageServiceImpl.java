package com.ferret.service.common.impl;

import com.ferret.bean.Background;
import com.ferret.dao.BackgroundImageMapper;
import com.ferret.service.common.IBackgroundImageService;
import com.ferret.utils.ImagePrefixProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackgroundImageServiceImpl implements IBackgroundImageService {

	@Autowired
	private BackgroundImageMapper backgroundImageMapper;

	@Autowired
	private ImagePrefixProperties imagePrefixProperties;

	/**
	 * 修改人:y
	 * 修改时间:0911
	 * 修改原因:自行从配置文件中拿到路径替换背景图的路径,不再走统一路径替换
	 * @param id
	 * @return
	 */
	@Override
	public Background findBackgroundImage(Integer id) {
		Background findBgById = backgroundImageMapper.findBgById(id);
		if(findBgById!=null && findBgById.getBakimagepath()!=null) {
			findBgById.setBakimagepath(findBgById.getBakimagepath().replace("\\", "/").replace(imagePrefixProperties.getHistoryDir(),
					imagePrefixProperties.getBackGroundUrl()));
		}
		return findBgById;
	}

	/**
	 * 修改人  y
	 * 修改时间   0718
	 * 
	 * 修改原因:添加背景图映射
	 */
	@Override
	public String getBackgroundImgByFeatureId(Long featureId) {
		String path = backgroundImageMapper.getBackgroundImageByFeatureId(featureId);
		if (StringUtils.isNotBlank(path)) {
			// 替换成http路径
			return path.replace("\\", "/").replace(imagePrefixProperties.getHistoryDir(),
					imagePrefixProperties.getBackGroundUrl());
		} else {
			return null;
		}
	}
}
