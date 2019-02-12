package com.ferret.service.analyse;

import com.ferret.bean.AllopatryAnalyse;
import com.ferret.bean.CameraInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AllopatryAnalyseService {

	/**
	 * 修改人:y
	 * 修改时间:0720
	 * 修改原因:将cameraDate删除,改成返回cameraInfo
	 * @param cameraNumber
	 * @return
	 */
	@Deprecated
	List<CameraInfo> getCameraList(List<String> cameraNumber);
	@Deprecated
	Map<String, Object> getAllopatryAnalyse(AllopatryAnalyse allopatryAnalyse);

	/**
	 * 区域碰撞新
	 * @param startTimeA
	 * @param endTimeA
	 * @param cameraIPsA
	 * @param startTimeB
	 * @param endTimeB
	 * @param cameraIPsB
	 * @return Map<String,Integer> String:personID, Integer:出现次数
	 */
	Map<String, Object> getAllopatryAnalyse(String startTimeA, String endTimeA, List<String> cameraIPsA,
												 String startTimeB, String endTimeB, List<String> cameraIPsB) ;
	/**
	 * 区域碰撞 查询该personId符合A条件，或者符合B条件的数据
	 * @param personId
	 * @param beginTimeA
	 * @param endTimeA
	 * @param cameraNumbersListA
	 * @param beginTimeB
	 * @param endTimeB
	 * @param cameraNumbersListB
	 * @param pageNum
	 * @param pageSize
	 * @param total
	 * @return
	 */
	Map<String,Object> getAllopatryPersonInfo(String personId, String beginTimeA, String endTimeA, ArrayList<String> cameraNumbersListA,
											   String beginTimeB, String endTimeB, ArrayList<String> cameraNumbersListB,
											   Integer pageNum,Integer pageSize,Integer total);

}
