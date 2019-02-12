package com.ferret.bean;

import java.util.List;

import lombok.Data;

@Data
public class PageResult<T> {
	private Integer pageNum;
	private Integer pageSize;
	private String personId;//根据personId查询同行人的时候用来接收id
	private String personId2;//查询同行历史的时候用来接收另一个personId
	private String cameraIp;//接收相机ip
	private int total;//总记录数
	private List<T> result;//当前页记录
	private String startTime;
	private String endTime;
	private int minNum;//最小同行次数
}
