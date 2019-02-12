package com.ferret.bean.clustercompare;

import lombok.Data;

@Data
public class PickFaceInfoList {

	private PickFaceAngle angle;
	private String fData;
	private WidthHeight leftEye;
	private WidthHeight mouth;
	private WidthHeight nose;
	private int quality;
	private Rect rect;
	private WidthHeight rightEye;
}
