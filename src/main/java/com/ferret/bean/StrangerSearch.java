package com.ferret.bean;

import lombok.Data;

import java.util.List;

@Data
public class StrangerSearch {
	private String startTime;
	private String stime;//经过选择时间段后,此时间将处理后返回页面,以供根据单个person查询时使用
	private String endTime;
	private List<CameraInfo> cameraInfos;//相机number数组,目前默认支取第一个相机,且不支持选择某个区域
	private String personId;
	private int pageNum;
	private int pageSize;
	private int flag;// 用来标记用户选择的查询方式,0:最近一周,1:最近两周,2:最近一个月
	private int maxNum=200;//用户选择的过滤次数,最大次数,如不选择,默认200
	private int minNum=0;//用户选择的过滤次数,最小次数,如不选择,默认0
	// 分页开始条数
	private Integer startNum;
	
}
