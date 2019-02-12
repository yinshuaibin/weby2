package com.ferret.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.searchimage.PeopleResult;
import com.ferret.bean.searchimage.SearchImage;
import com.ferret.bean.searchimage.SearchResult;


public class TaskFaceUtils extends Thread{
	
	
	private SearchImage simg;
	private String url;
	
	public TaskFaceUtils(SearchImage simg,String url) {
		this.simg=simg;
		this.url=url;
	}
	
	@Override
	public void run() {
		this.sendTask(simg, url);
	}
	/**
	 * @author qin
	 * @param SearchImage 人脸比对的条件，人脸比对的地址
	 * @return resultJsonStr
	 * */
	public static  String sendTask(SearchImage simg,String url){
		//System.out.println("访问url:"+url);
		RestTemplate rtl=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder.create()
		        .setRedirectStrategy(new LaxRedirectStrategy())
		        .build();
		factory.setHttpClient(httpClient);
		rtl.setRequestFactory(factory);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		String searchJson=JSONObject.toJSONString(simg);
		String resultJsonStr = null;
		try {
			resultJsonStr = rtl.postForObject(url, new HttpEntity<String>(searchJson, headers), String.class);
		} catch (Exception e) {
			System.out.println("-----------------This "+url+" link exception（error）！！！-----------------");
		}
		return resultJsonStr;
	}
	/**
	 * @author qin
	 * @param 人脸比对的结果json
	 * @return PeopleResult idcard及相似度的列表
	 * */
	public static List<PeopleResult> resovlePeopleResult(SearchResult result) {
		
		List<PeopleResult> ls=new ArrayList<PeopleResult>();
		String[] peopleStrs=result.getResult().split(",");
		for(int i=0;i<peopleStrs.length;i++) {
			PeopleResult people=new PeopleResult();
			String[] peopleFields=peopleStrs[i].split("[$]");
			people.setTaskId(result.getTaskId());
			people.setSysId(result.getSysId());
			people.setIdCard(peopleFields[0].trim());
			people.setSimilay(Float.parseFloat(peopleFields[1].trim()));
			ls.add(people);
		}
		return ls;
	}
}
