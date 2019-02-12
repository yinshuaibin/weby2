package com.ferret.service.common;


public interface SequenceService {
	
	String roleIdMark="R";//角色id标记
	String cameraIdMark="C";//相机id标记
	String bkGroupIdMark="B";//布控分组标记
	
	String roleId="roleId";
	String bkGroupId="bk_group_id";
	String cameraNumber="camera_number";
	String cameraGroup="camera_group";
	/**
	 * @return 返回指定序列的下一个值
	 * */
    String nextVal(String seqName);
	
	/**
	 * @param area：410101 郑州市
	 * @return 返回新生成的角色编号
	 * */
    String createRoleId(String area);
	
	
	/**
	 * @param area 区域编号，placeId 场所编号 场所指：旅馆，网吧，火车站，汽车站等
	 * @return String 布控分组ID
	 * */
    String createCameraId(String area, String placeId) ;

	/**
	 * 生成摄像头编号
	 * @param groupNumber 摄像头分组编号
	 * @return
	 */
    String createCameraId(String groupNumber);
	/**
	 * @return 生成返回布控分组的编号
	 * */
    String createBKGroupId(String area) ;

	/**
	 * @return 生成相机分组id 分组是指街道、道路名称等
	 * */
    String createCameraGroup() ;
}
