package com.ferret.controller;

import com.ferret.bean.ClusterPass;
import com.ferret.bean.ImageFace;
import com.ferret.dto.ClusterBrowseDTO;
import com.ferret.service.cluster.ClusterService;
import com.ferret.service.face.FaceService;
import com.ferret.service.identify.IdentityFactory;
import com.ferret.utils.ImagePrefixProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聚类档案接口
 * @author zwc
 * @date 2018-03-14
 * 修改人: y
 * 修改日期:0716
 */
@RestController
public class ClusterController extends BaseController {

	@Value("${image.prefix.uploadDir}")
	private String imgDir;
	@Value("${personArea}")
	private String personArea;
	@Autowired
	private ClusterService clusterService;
	@Autowired
	ImagePrefixProperties imagePrefixProperties;
	@Autowired
	private IdentityFactory identityFactory;

	@Autowired
	private FaceService faceService;

	/**
	 * @Descriptions 档案反查（通过 图片对比 反查结果）
	 * @Author xyc 2018/12/17
	 * @throws Exception
	 */
	@RequestMapping(value = "/cluster/search", method = RequestMethod.POST)
	public List<ClusterBrowseDTO> clusterSearch(@RequestBody Map map) throws Exception {
		float threshold = (float)(Integer)(map.get("threshold"))/10;
		List<ClusterBrowseDTO> clusterSearches = clusterService.findClusterByPerId((Integer) (map.get("count")),threshold,(String)(map.get("img1")));
		return clusterSearches;
	}
	/**
	 * @Descriptions 根据时间段查询档案数据
	 * @Author xyc 2018/12/17
	 * @throws Exception
	 */
    @RequestMapping(value = "/cluster/getClusterByDate", method = RequestMethod.POST)
	public Map<String, Object> getUser(@RequestBody Map map) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		Integer pageSize = (Integer) map.get("pageSize");
		if (pageSize == null) {
			pageSize = 24;
		}
		// 按照页面传递时间查询
		if (StringUtils.isBlank((String)(map.get("startTime")))&&StringUtils.isBlank((String)(map.get("endTime")))) {
			return null;
		}
		//查询总数据
		List<ClusterBrowseDTO> list = clusterService.findClusterByDate((String)(map.get("startTime")),(String)(map.get("endTime")),
				(Integer)(map.get("minNum")),(Integer)(map.get("pageNum")),pageSize);
		//说明是第一次查询,查询并返回总条数
		if((Integer)(map.get("totalNum"))<1){
			//查询总条数
			Integer totalNum = clusterService.findTotalByDate((String)(map.get("startTime")),(String)(map.get("endTime")),
					(Integer)(map.get("minNum")));
			resultMap.put("totalNum", totalNum);
		}
		resultMap.put("resultList", list);
		return resultMap;
	}
	/**
	 * @Descriptions 通过 id 查询tb_cluster_pass中的所有对应数据
	 * @author xyc 2018/12/17
	 * @throws Exception
	 */
    @RequestMapping(value = "/cluster/getClusterByPersonId", method = RequestMethod.POST)
	public Map getUserById(@RequestBody Map map) throws Exception {
		Map<String, Object> reaultMap = new HashMap<>();
		// 通过persionId 查询 该id 总条数
		List<ClusterBrowseDTO> result = clusterService.findClusterByPersonId((Integer)(map.get("pageNum")),(Integer)(map.get("pageSize")),(String)(map.get("personId")));
		reaultMap.put("resultList", result);
		return reaultMap;
	}
	/**
	 * @Descriptions 通过person_id,查询tb_cluster_pass中最近十条的所有数据
	 *               用于地图标记最近十条的轨迹
	 * @author xyc
	 * @param personId
	 * @throws Exception
	 */
	@RequestMapping(value = "/cluster/getClusterByPersonAndTime/{personId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("personId") String personId) throws Exception {
		return ResponseEntity.ok(clusterService.findClusterByPersonId(personId));
	}
	/**
	 * @Descriptions 通过 personId获取身份证图片（查询tb_cluster表 request_img1）
	 */
	@RequestMapping(value = "/getPhotoByPersonId", method = RequestMethod.POST)
	public ClusterBrowseDTO getPhotoByPersonId(@RequestBody Map map) throws Exception {

		ClusterBrowseDTO clusterBrowseDTO = clusterService.getPersonInfoByPersonId((String)(map.get("personId")));
		if(null == clusterBrowseDTO.getImagePath() || ("").equals(clusterBrowseDTO.getImagePath())) {
			ClusterBrowseDTO browseDTO =  clusterService.getPhotoByPersonId((String)(map.get("personId")));
			if (null != browseDTO && null != browseDTO.getImagePath()) {
				clusterBrowseDTO.setImagePath(browseDTO.getImagePath());
			}
		}
		return  clusterBrowseDTO;
	}

	/**
	 * 人脸截取 新中间件  y 0103
	 * 采用faceService中的人脸截取方法
	 */
	@RequestMapping(value = "/getImgFaceNum", method = RequestMethod.POST)
	public ImageFace getImgFace_Num_new(@RequestBody Map reqMap){
		return faceService.FaceExtraction((String)reqMap.get("baseImgeKey1"));
	}

	/**
	 * 2018-11-19 zwc
	 * 身份落地，调用省厅多人像接口
	 */
	@RequestMapping(value = "/identityLanding/{personId}", method = RequestMethod.GET)
	public String identityLanding(@PathVariable("personId") String personId) throws Exception {
		identityFactory.getIdentityChecker().check(personId);
		return "success";
	}

}
