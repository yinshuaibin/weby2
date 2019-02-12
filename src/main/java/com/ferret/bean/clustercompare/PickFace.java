package com.ferret.bean.clustercompare;

import lombok.Data;

@Data
public class PickFace {
	/**
	 * 特征提取返回
	 */
	private String response;
	private Integer statue;
	private String error;
	private PickFaceResInfo resInfo;
}
