package com.ferret.service.common;

import java.util.List;

import com.ferret.bean.searchimage.FaceService;
import com.ferret.bean.searchimage.People;
import com.ferret.bean.searchimage.PeopleResult;
import com.ferret.bean.searchimage.SearchImage;


public interface SearchImageService {
	/**
	 * @see 存储单条查询结果
	 * @param PeopleResult
	 * @return int 存储结果
	 * @author qin
	 * */
	public Integer insertPeopleResult(PeopleResult result);
	
	/**
	 * @see 批量插入
	 * @param PeopleResult
	 * @return int 存储结果
	 * @author qin
	 * */
	public Integer insertPeopleResult(List<PeopleResult> results);
	
	/**
	 * @see 根据任务id,查询任务结果
	 * @param String 任务名称
	 * @return List<People>
	 * @author qin
	 * */
	public List<People> findPeopleResult(String taskId);
	
	/**
	 * @see 查出现有比对服务的url
	 * @param 无
	 * @return 
	 * @author qin
	 * */
	public List<FaceService> queryFaceService();
	
	/**
	 * @see 查出某个人的各个厂商的结果
	 * @param 无
	 * @return 
	 * @author qin
	 * */
	public List<People> peopleInfo(String taskId,String idCard);
	
	/**
	 * @see 数据库保存批量图片的信息
	 * @param 无
	 * @return 
	 * @author zwc
	 * */
	public void saveImageInfo(SearchImage simg);
}
