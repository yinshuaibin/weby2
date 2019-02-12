package com.ferret.bean;

import java.util.List;

import lombok.Data;

/**
 * 行人区域分析  实体类
 * @author zwc
 * 
 * 修改人:y
 * 修改时间:0720
 * 修改原因:删除了CameraData,改为了CameraInfo
 */
@Data
public class AllopatryAnalyse {
	//区域A   相机列表
	private List<CameraInfo> cameraNumbersListA;
	//开始时间
	private String beginTimeA;
	//结束时间
	private String endTimeA;
	
	//区域A
	private List<CameraInfo> cameraNumbersListB;
	private String beginTimeB;
	private String endTimeB;
	
	//分页
	private int pageNum;
	private int pageSize;
	private Integer total;
}
