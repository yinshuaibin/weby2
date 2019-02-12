package com.ferret.bean;

import java.util.List;

import lombok.Data;
@Data
public class ClusterSearchResult {

	private String cmd;
	private String code;
	private String facenum;
	private Integer itnum;
	private String response;
	private Integer statue;
	private String error;
	
	private List<QrybyImageResult> resInfo;
	
	
	
}
