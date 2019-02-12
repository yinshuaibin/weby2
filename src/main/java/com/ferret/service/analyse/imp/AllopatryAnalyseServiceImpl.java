package com.ferret.service.analyse.imp;

import com.ferret.bean.AllopatryAnalyse;
import com.ferret.bean.AllopatryPersonAnalyse;
import com.ferret.bean.CameraInfo;
import com.ferret.bean.ClusterSearch;
import com.ferret.dao.AllopatryAnalyseMapper;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.dao.ClusterDateMapper;
import com.ferret.service.analyse.AllopatryAnalyseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zwc
 */
@Service
public class AllopatryAnalyseServiceImpl implements AllopatryAnalyseService {
	
	@Autowired
	private AllopatryAnalyseMapper allopatryAnalyseMapper;
	
	@Autowired
	private CameraInfoMapper cameraInfoMapper;

	@Autowired
    private ClusterDateMapper clusterDateMapper;

	/**
	 * 根据区位码，获取相机列表并整理数据
	 */
	@Deprecated
	@Override
	public List<CameraInfo> getCameraList(List<String> cameraNumber) {
		return cameraInfoMapper.findCameraINfoBySelect(cameraNumber);
	}
	/**
	 * 区域碰撞分析（查询在2个时间 地点，出现的同一个人。）
	 * @return 
	 */
	@Deprecated
	@Override
	public Map<String, Object> getAllopatryAnalyse(AllopatryAnalyse allopatryAnalyse) {
		//处理相机列表A 数据
		StringBuilder sbA = new StringBuilder();
		List<CameraInfo> cameraNumbersListA = allopatryAnalyse.getCameraNumbersListA();
		for (CameraInfo cameraGroupA : cameraNumbersListA) {
			if (StringUtils.isNotBlank(cameraGroupA.getIp())) {
				sbA.append(cameraGroupA.getIp()).append(",");
			}
		}
		String cameraListA = sbA.substring(0, sbA.length()-1).toString();
		//处理相机列表B 数据
		StringBuilder sbB = new StringBuilder();
		List<CameraInfo> cameraNumbersListB = allopatryAnalyse.getCameraNumbersListB();
		for (CameraInfo cameraGroupB : cameraNumbersListB) {
			if (StringUtils.isNotBlank(cameraGroupB.getIp())) {
				sbB.append(cameraGroupB.getIp()).append(",");
			}
		}
		String cameraListB = sbB.substring(0, sbB.length()-1).toString();
		//处理分页  参数
		int startPageNum = (allopatryAnalyse.getPageNum()-1)*allopatryAnalyse.getPageSize();
		Integer allopatryAnalyseTotal = 0;
		//分页处理。
		if (allopatryAnalyse.getTotal() == 0) {
			//总条数
			allopatryAnalyseTotal = allopatryAnalyseMapper.getAllopatryAnalyseTotal(allopatryAnalyse.getBeginTimeA(),allopatryAnalyse.getEndTimeA(),allopatryAnalyse.getBeginTimeB(),allopatryAnalyse.getEndTimeB(),cameraListA,cameraListB);

		}
		//System.err.println("------"+allopatryAnalyse.getBeginTimeA()+"------"+allopatryAnalyse.getEndTimeA()+"------"+allopatryAnalyse.getBeginTimeB()+"------"+allopatryAnalyse.getEndTimeB()+"------"+cameraListA+cameraListB);
		//查询数据
		List<AllopatryPersonAnalyse> allopatryAnalyse2 = allopatryAnalyseMapper.getAllopatryAnalyse(allopatryAnalyse.getBeginTimeA(),allopatryAnalyse.getEndTimeA(),
				cameraListA,allopatryAnalyse.getBeginTimeB(),allopatryAnalyse.getEndTimeB(),cameraListB,startPageNum,allopatryAnalyse.getPageSize());
		Map<String, Object> map = new HashMap<>();
		map.put("total", allopatryAnalyseTotal);
		map.put("allopatryPersonAnalyse", allopatryAnalyse2);
        return map;
	}
    /**
     *区域分析（获取2个时间地点 出现的同一个人） 新
     * @return
     */
	@Override
	public Map<String, Object> getAllopatryAnalyse(String startTimeA, String endTimeA, List<String> cameraIPsA, String startTimeB, String endTimeB, List<String> cameraIPsB) {
        List<Map<String, Integer>> allopatryAnalyse = allopatryAnalyseMapper.findAllopatryAnalyse(startTimeA, endTimeA, cameraIPsA, startTimeB, endTimeB, cameraIPsB);
        // 遍历取每一个map集合中的personId和num
        Map<String, Object> resultMap = new HashMap<>();
        ArrayList<ClusterSearch> clusterSearches = new ArrayList<>();
        if (allopatryAnalyse.size() > 0 ) {
            for (Map<String, Integer> map:allopatryAnalyse) {
                //通过personId个人信息(tb_cluster表)
                ClusterSearch personSearch = clusterDateMapper.findClusterByPerId(map.get("person_id") + "");
                personSearch.setCount(Integer.valueOf((map.get("num")+"")));
                clusterSearches.add(personSearch);
            }
        }
        resultMap.put("total",clusterSearches.size());
        resultMap.put("allopatryPersonAnalyse", clusterSearches);
        return resultMap;
	}

    /**
     * 区域碰撞分析（查询该personId符合A条件，或者符合B条件的数据）
     * @return
     */
    @Override
    public Map<String,Object> getAllopatryPersonInfo(String personId, String beginTimeA, String endTimeA, ArrayList<String> cameraNumbersListA, String beginTimeB, String endTimeB, ArrayList<String> cameraNumbersListB,Integer pageNum,Integer pageSize,Integer total) {
		// 分页 判断接受的total为0  查询总数
		List<ClusterSearch> allopatryPersonInfo = null;
        Integer pageStartNum = (pageNum -1) * pageSize;
		Map<String,Object> map = new HashMap<>();
		if(total == 0){
		    //查询总条数
            Integer allopatryPersonTotal = allopatryAnalyseMapper.getAllopatryPersonTotal(personId, beginTimeA, endTimeA, cameraNumbersListA, beginTimeB, endTimeB, cameraNumbersListB);
			allopatryPersonInfo = allopatryAnalyseMapper.getAllopatryPersonInfo(personId, beginTimeA, endTimeA, cameraNumbersListA, beginTimeB, endTimeB, cameraNumbersListB,pageStartNum,pageSize);
            map.put("total",allopatryPersonTotal);
		}else {
			// 直接查询分页数据
            allopatryPersonInfo = allopatryAnalyseMapper.getAllopatryPersonInfo(personId, beginTimeA, endTimeA, cameraNumbersListA, beginTimeB, endTimeB, cameraNumbersListB,pageStartNum,pageSize);
        }
        if (allopatryPersonInfo.size()>0){
            for (ClusterSearch clusterSearch:allopatryPersonInfo ) {
                String createTime = clusterSearch.getCreateTime();
                if (createTime.contains(".")){
                    clusterSearch.setCreateTime(createTime.substring(0,createTime.indexOf(".")));
                }
            }
        }
        map.put("personInfo",allopatryPersonInfo);
        return map;
    }


}
