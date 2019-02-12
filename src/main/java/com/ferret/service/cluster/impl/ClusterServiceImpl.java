package com.ferret.service.cluster.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.Base64ImageEntity;
import com.ferret.bean.ClusterPass;
import com.ferret.dao.ClusterDateMapper;
import com.ferret.dto.ClusterBrowseDTO;
import com.ferret.service.cluster.ClusterService;
import com.ferret.service.common.city.CityService;
import com.ferret.utils.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 修改人:y
 * 修改日期:0716
 * @author Administrator
 *
 */
@Service("clusterDateService")
@Slf4j
public class ClusterServiceImpl implements ClusterService {
	@Value("${personArea}")
	private String personArea;
	@Value("${image.prefix.uploadDir}")
	private String imgDir;
	@Value("${interfaceservice.clusterSearchUrl}")
	private String clusterSearchUrl;
	@Value("${interfaceservice.faceFeatureExtraction}")
	private String faceFeatureExtraction;
	@Autowired
	private ClusterDateMapper dateMapper;


	@Autowired
	private CityService cityService;
	private RestTemplate restTemplate = new RestTemplate();

	/**
	 * 通过时间段,查询档案数据
	 * 修改人:y
	 * 修改时间: 0716
	 *
	 * @param clusterPass
	 * @return
	 */
	public List<ClusterBrowseDTO> findClusterByDate(ClusterPass clusterPass) {
		// 前端传过来的页数，转换成 开始查询的 初始位值
		Integer startNum = (clusterPass.getPageNum() - 1) * 24;
		Integer minNum = clusterPass.getMinNum();
		return dateMapper.findClusterByTime(clusterPass.getStartTime(), clusterPass.getEndTime(), startNum, minNum);
	}

	/**
	 * @Description 通过时间段,查询档案数据（档案浏览）
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param minNum    最小抓拍次数
	 * @return
	 */
	@Override
	public List<ClusterBrowseDTO> findClusterByDate(String startTime, String endTime, int minNum, int pageNum) {
        // 前端传过来的页数，转换成 开始查询的 初始位值
		int startNum = (pageNum - 1) * 24;
		return dateMapper.findClusterByTime(startTime, endTime, startNum, minNum);
	}

	/**
	 * 替换 findClusterByDate(String startTime, String endTime, int minNum, int pageNum)
	 * @param startTime
	 * @param endTime
	 * @param minNum
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<ClusterBrowseDTO> findClusterByDate(String startTime, String endTime, int minNum, int pageNum, int pageSize) {
		// 前端传过来的页数，转换成 开始查询的 初始位值
		int startNum = (pageNum - 1) * pageSize;
		return dateMapper.findClusterByTime(startTime, endTime, startNum, minNum);
	}

	/**
	 * 通过时间段,查询档案总条数
	 * 修改人:y
	 * 修改时间: 0716
	 *
	 * @param clusterPass
	 * @return
	 */
	public Integer findTotalByDate(ClusterPass clusterPass) {
		return dateMapper.findTotalByDate(clusterPass.getStartTime(), clusterPass.getEndTime(), clusterPass.getMinNum());
	}

	/**
	 * @Descriptions 通过时间段查询档案总条数
	 * @Author xyc 2018/12/18
	 * @return
	 */
	@Override
	public Integer findTotalByDate(String startTime, String endTime, int minNum) {
		return dateMapper.findTotalByDate(startTime, endTime, minNum);
	}

	/**
	 * 通过person_id,查询tb_cluster_pass中符合记录的所有数据 并分页
	 * 修改人:y
	 * 修改时间: 0716
	 *
	 * @param clusterPass
	 * @return
	 */
	public List<ClusterBrowseDTO> findClusterByPersonId(ClusterPass clusterPass) {
		// 查询时间段用户id 取10条
		Integer startNum = (clusterPass.getPageNum() - 1) * (clusterPass.getPageSize());
		List<ClusterBrowseDTO> result = dateMapper.findClusterByPersonId(clusterPass.getPersonId(), startNum, clusterPass.getPageSize());

		return result;
	}

	/**
	 * @Descriptions 通过person_id,查询tb_cluster_pass中最近一周的所有数据
	 *               用于地图标记最近一周的轨迹
	 * @author xyc
	 * @param personId
	 * @return
	 */
	@Override
	public List<ClusterPass>  findClusterByPersonId(String personId) {
		List<ClusterPass> result = dateMapper.findClusterByPersonIdAndTime(personId);
		// 处理时间多了.0问题
		for (int x = 0; x < result.size(); x++) {
			ClusterPass c = result.get(x);
			if (c.getCreateTime() != null) {
				c.setCreateTime(c.getCreateTime().substring(0, 19));
			}
		}
		return result;
	}
	@Override
	public List<ClusterBrowseDTO> findClusterByPerId(int count, float threshold, String imageData) {
		String uploadName = imageData;
		// 保存图片
		if (StringUtils.isNotBlank(imageData)) {
			if (imageData.length() > 500) {
				if (imageData.contains(",")) {
					String[] split =imageData.split(",");
					uploadName = split[1];
				}
			} else {
				Base64ImageEntity base64ImageEntity = ImageBase64Utils.generateBase64ImageEntity(imageData);
				uploadName = base64ImageEntity.getImageData();
			}

			Map<String,Object> map = new HashMap<>();
			map.put("method","Feature");
			map.put("file1",uploadName.replace("\r\n",""));
			String string = JSONObject.toJSONString(map);
			// 图片提取特征
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity(string,headers);
			String o = restTemplate.postForObject(faceFeatureExtraction, entity, String.class);
			JSONObject jsonObject = JSONObject.parseObject(o, JSONObject.class);
			Integer resultcode = jsonObject.getInteger("resultcode");
			Integer featureNum = jsonObject.getInteger("Feature_Num");
			// 0- 提取人脸特征成功
			if (resultcode == 0 && featureNum > 0) {
				JSONArray feature_list = jsonObject.getJSONArray("Feature_List");
				// 人脸检测后的图片，应只有一个人脸
				JSONObject jsonObject1 = feature_list.getJSONObject(0);
				String feature = jsonObject1.getString("Feature");
				//根据体征反查图片id
				Map<String,Object> r = new HashMap<>();
                r.put("file1", feature);
                String resultquery = restTemplate.postForObject(clusterSearchUrl, new HttpEntity<>(JSONObject.toJSONString(r), headers), String.class);
                JSONObject resultqueryJson = JSONObject.parseObject(resultquery, JSONObject.class);
				Integer resultcode1 = resultqueryJson.getInteger("resultcode");
				//接口返回状态，0-成功，-1参数为空，1未找到聚类。
				if (resultcode1 == 0){
					float fsimi = resultqueryJson.getFloat("fsimi");
					if (fsimi < threshold) {
						return null;
					}
					String personId = resultqueryJson.getString("nPersonId");
					/*//通过personId查询jh_person信息 和总数
					List<ClusterBrowseDTO> personByPersonid = dateMapper.findPersonByClusterId(personId);*/
					/**
					 * 1,表结构变更。通过clusterid查询对应的personid，在通过personid查询对应的所有clusterid
					 * 2, 统计clusterid集合 的数据
					 */
					String clusterids = dateMapper.findClusterids(personId);
					if (StringUtils.isNotBlank(clusterids)) {
						ArrayList<String> arrayList = new ArrayList<>();
						if (clusterids.contains(",")) {
							String[] split = clusterids.split(",");
							for (int i = 0; i < split.length; i++) {
								arrayList.add(split[i]);
							}
						} else {
							arrayList.add(clusterids);
						}
						List<ClusterBrowseDTO> personByClusterids = dateMapper.getPersonByClusterids(arrayList);
						// 通过personid查询jh_person表 应只有一条记录
						if (personByClusterids != null && personByClusterids.size() > 0) {
							for (int i = 0; i < personByClusterids.size(); i++) {
								personByClusterids.get(i).setFilimt(fsimi);
							}
						}
						return personByClusterids;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @Description 通过person_id,查询tb_cluster_pass中符合记录的所有数据 并分页
	 * @param pageNum
	 * @param pageSize
	 * @param personId 
	 * @date 2019-01-14 09:35:09
	 * @author xieyingchao
	 */
	@Override
	public List<ClusterBrowseDTO> findClusterByPersonId(int pageNum, int pageSize, String personId) {
		// 查询时间段用户id 取10条
		Integer startNum = (pageNum - 1) * pageSize;
		List<ClusterBrowseDTO> result = dateMapper.findClusterByPersonId(personId, startNum, pageSize);
		// 处理地址问题
		for (int x = 0; x < result.size(); x++) {
			ClusterBrowseDTO c = result.get(x);
			if(null != c.getNumber()) {
				c.setCameraName(cityService.findAreaName(Integer.valueOf(c.getNumber())) + " " +c.getCameraName());
			}
		}
		return result;
	}

	/**
	 * @Description 根据personId 查询聚类信息代表图
	 * @param personId
	 * @date 2019-01-14 14:39:29
	 * @author xieyingchao
	 */
	@Override
	public ClusterBrowseDTO getPhotoByPersonId(String personId) {
		return dateMapper.getPhotoByPersonId(personId);

	}

	/**
	 * @Description 根据personId 查询落地后的身份信息（姓名，身份证号，身份证照片）
	 * @param personId 
	 * @date 2019-01-14 14:39:15
	 * @author xieyingchao
	 */
    @Override
    public ClusterBrowseDTO getPersonInfoByPersonId(String personId) {
        return dateMapper.getPersonInfoByPersonId(personId);
    }
}
