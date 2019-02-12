package com.ferret.controller;

import com.ferret.bean.ClusterPass;
import com.ferret.bean.ClusterWei;
import com.ferret.dto.PageDTO;
import com.ferret.service.cluster.ClusterWeiZuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClusterWeiZuController extends BaseController {

    @Autowired
    private ClusterWeiZuService clusterWeiZuService;

    /**
     * 根据时间段查询维族档案数据
     * @return map
     * @throws Exception
     */
    @RequestMapping(value = "/cluster/getClusterByDateWei", method = RequestMethod.POST)
    public Map<String, Object> getClusterByDateWei(@RequestBody ClusterPass clusterPass) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 按照页面传递时间查询
        if (StringUtils.isBlank(clusterPass.getStartTime())&&StringUtils.isBlank(clusterPass.getEndTime())) {
            return null;
        }
        //查询总数据
        List<ClusterPass> list = clusterWeiZuService.findClusterWeiByDate(clusterPass);
        //说明是第一次查询,查询并返回总条数
        if(clusterPass.getTotalNum()<1){
            //查询总条数
            Integer totalNum = clusterWeiZuService.findTotalByDate(clusterPass);
            map.put("totalNum", totalNum);
        }
        map.put("resultList", list);
        return map;
    }

    /**
     * 修改了逻辑,可根据相机列表区域, 查询最新的
     * @return
     */
    @RequestMapping("/getNewClusterWei")
    public Map getNewClusterWei() {
        return clusterWeiZuService.getNewClusterWei();
    }

    /**
     *  通过 id 查询tb_cluster_wei中的所有对应数据
     * @param clusterPass
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cluster/getClusterWeiByPersonId", method = RequestMethod.POST)
    public Map<String, Object> getUserWeiById(@RequestBody ClusterPass clusterPass) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 通过persionId 查询 该id 总条数
        // 通过persionId 查询该id所有记录（分页查询）
        List<ClusterPass> list = clusterWeiZuService.findClusterByPersonId(clusterPass);
        map.put("resultList", list);
        return map;
    }

    /**
     * 通过 personId获取身份证图片（查询tb_cluster表 request_img1）
     */
    @RequestMapping(value = "/getPhotoWeiByPersonId", method = RequestMethod.POST)
    public ClusterPass getPhotoWeiByPersonId(@RequestBody ClusterPass clusterPass) throws Exception {

        return  clusterWeiZuService.getPhotoByPersonId(clusterPass.getPersonId());
    }
    @RequestMapping(value = "/clusters/wei", method = RequestMethod.GET)
    public ResponseEntity listByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        PageDTO<ClusterWei> pageDTO = clusterWeiZuService.listByPage(pageNum, pageSize);
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * 获取最近的数据
     * @return
     */
    @RequestMapping(value = "/clusters/wei/recent",method = RequestMethod.GET)
    public ResponseEntity listRecentRecords(){
        List<ClusterWei> list = clusterWeiZuService.listRecentRecords();
        return ResponseEntity.ok(list);
    }
}
