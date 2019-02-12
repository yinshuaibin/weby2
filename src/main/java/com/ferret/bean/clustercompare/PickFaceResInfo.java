package com.ferret.bean.clustercompare;

import java.util.List;

import lombok.Data;

@Data
public class PickFaceResInfo {

	private List<PickFaceInfoList> faceList;
	private int faceNum;
	private int imgH;
	private int imgW;
}
