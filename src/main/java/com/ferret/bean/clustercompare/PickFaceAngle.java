package com.ferret.bean.clustercompare;

import lombok.Data;

@Data
public class PickFaceAngle {

	private double confidence;
	private int pitch;
	private int roll;
	private int yaw;
}
