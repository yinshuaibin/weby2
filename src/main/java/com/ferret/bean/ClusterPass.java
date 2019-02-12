package com.ferret.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ferret.utils.Path2UrlSerialize;

import lombok.Data;

/**
 * 聚类实体类
 * 修改人:y
 * 修改日期:0716
 *
 * 修改人:y
 * 修改日期:0827
 * 修改原因:档案浏览分页时不再查询总条数
 * @author Administrator
 *
 *
 */
@Data
public class ClusterPass {

	private String personId;
	//返回页面的createTime
	private String createTime;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String imagePath;
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String represent_img1;
	
    private Integer count;
	private String startTime;
	private String endTime;
	private Integer totalNum;//用来接收界面传递过来的总条数,如果有说明是分页,则不再查询总条数
	
	private Integer pageNum;
	private Integer pageSize;
	private String cameraName;
	private String cameraIp;
	private String[] area; //用来接受档案浏览时页面传递过来的地区id
	private Integer minNum;//最小抓拍次数限制
	//经纬度
	private double longitude;
	private double latitude;
	// IdCard
	private String idcard;
	// 姓名
	private String name;
	// 身份证照片
	@JsonSerialize(using = Path2UrlSerialize.class)
	private String represent_img5;
	//用来接收相机表中的number字段
	private String number;
}
