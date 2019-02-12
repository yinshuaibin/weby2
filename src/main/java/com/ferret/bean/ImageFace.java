package com.ferret.bean;

import java.util.List;

import com.ferret.bean.clustercompare.WidthHeight;

import lombok.Data;

@Data
public class ImageFace {

	//接受的base64字符
	private String baseImgeKey1;
	
	//返回的人脸个数
	private int faceNum;
	
	//返回的人脸图片base64集合
	private List<String> faceBase64;
	
	private WidthHeight widthHeight;
}
