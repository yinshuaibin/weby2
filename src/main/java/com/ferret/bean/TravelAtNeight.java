package com.ferret.bean;

import java.util.List;

import lombok.Data;

/**
 * 夜行人实体类
 * 
 * @author y 日期:0720
 *
 */
@Data
public class TravelAtNeight {
	// 开始日期
	private String startDate;
	// 结束日期
	private String endDate;
	// 夜间开始时间
	private String startTime;
	// 夜间结束时间
	private String endTime;
	// 所选相机或地区
	private List<CameraInfo> cameraList;
	// 最小出行次数
	private Integer minNum;
	// 页数
	private Integer pageNum;
	// 每页记录数
	private Integer pageSize;
	// 分页开始条数
	private Integer startNum;
	// 档案编号
	private String personId;
	// 总条数, 第一次查询时返回, 分页时不再查询
	private Integer totalNum;
}
