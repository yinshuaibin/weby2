package com.ferret.bean.pagebean;

import com.ferret.bean.CameraInfo;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 修改人 y
 * 修改时间:0723
 * 修改原因:分页查询时,不再查询总条数
 * @author bin
 *修改原因:前台增加了一个勾选摄像头功能,改了前台传递过来的摄像头数据,原来的不再使用  y 0811
 */
@Data
public class HistoryParam {
	private String startDateTime;
	private String endDateTime;
	private String[] cameraId;//因前台修改,这里不再使用,改为下边的cameraList
	private int pageNum;
	private int pageSize;
	private int dataTotal;
	private List<CameraInfo> cameraList;
}
