package com.ferret.service.cluster.impl;

import com.ferret.bean.ClusterPass;
import com.ferret.bean.ClusterWei;
import com.ferret.bean.Stranger;
import com.ferret.common.base.Common;
import com.ferret.dao.ClusterWeiMapper;
import com.ferret.dao.ClusterWeiZuMapper;
import com.ferret.dto.PageDTO;
import com.ferret.service.cluster.ClusterWeiZuService;
import com.ferret.utils.ClusterScheduledTasks;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("clusterWeiZuService")
public class ClusterWeiZuServiceImpl implements ClusterWeiZuService, Common {

    /*@Value("${webservice.clusterDataUrl}")
    private String clusterDataUrl;*/
    @Value("${personArea}")
    private String personArea;
    @Value("${image.prefix.uploadDir}")
    private String imgDir;
    /*@Value("${webservice.featureUrlM}")
    private String featureUrlM;*/

    @Autowired
    private ClusterWeiZuMapper clusterWeiZuMapper;
    @Autowired
    private ClusterWeiMapper clusterWeiMapper;

    //返回页面的实时陌生维族人map
    private Map resultClusterWeiMap = new HashMap();
    //实施陌生维族人用来用来比对查询出来的数据是否更新的list集合
    private List<Stranger> clusterWeiList = new ArrayList();

    @Override
    public List<ClusterPass> findClusterWeiByDate(ClusterPass clusterPass) {
        // 前端传过来的页数，转换成 开始查询的 初始位值
        Integer startNum = (clusterPass.getPageNum() - 1) * 24;
        Integer minNum = clusterPass.getMinNum();
        return clusterWeiZuMapper.findClusterWeiByTime(clusterPass.getStartTime(), clusterPass.getEndTime(), startNum,minNum);
    }

    @Override
    public Integer findTotalByDate(ClusterPass clusterPass) {
        return clusterWeiZuMapper.findTotalByDate(clusterPass.getStartTime(), clusterPass.getEndTime(), clusterPass.getMinNum());
    }


    /**
     * 获取最新的tb_cluster_wei中的6条维族人数据
     * y 1012
     * @return
     */
    @Override
    public Map getNewClusterWei() {
        List<Stranger> result = clusterWeiZuMapper.getNewClusterWei();
        //如果这里的list集合没有数据,说明是第一次查询,直接给这个list集合赋值
        if(clusterWeiList.size() < 1){
            clusterWeiList.addAll(result);
        }else{
            //已经查询出来过数据了
            if(result.size() > 0 ){
                //如果查询出来的第一条的personid和用来比对的集合中的第一条的personid不相同,说明最新的6维族人数据更新了
                if(!result.get(0).getPersonId().equals(clusterWeiList.get(0).getPersonId())){
                    resultClusterWeiMap.put("flag",true);
                    clusterWeiList.clear();
                    clusterWeiList.addAll(result);
                }else {
                    resultClusterWeiMap.put("flag",false);
                }
            }
        }// 处理时间多了.0问题
        for (int x = 0; x < clusterWeiList.size(); x++) {
            Stranger c = result.get(x);
            if (c.getCreateTime() != null && c.getCreateTime().length() > 18) {
                c.setCreateTime(c.getCreateTime().substring(0, 19));
            }
        }
        resultClusterWeiMap.put("resultList",clusterWeiList);

        return resultClusterWeiMap;
    }

    @Override
    public List<ClusterPass> findClusterByPersonId(ClusterPass clusterPass) {
        // 查询时间段用户id 取10条
        Integer startNum = (clusterPass.getPageNum() - 1) * (clusterPass.getPageSize());
        List<ClusterPass> result = clusterWeiZuMapper.findClusterByPersonId(clusterPass.getPersonId(), startNum, clusterPass.getPageSize());
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
    public ClusterPass getPhotoByPersonId(String personId) {
        return clusterWeiZuMapper.getPhotoByPersonId(personId);
    }

    @Override
    public PageDTO<ClusterWei> listByPage(Integer pageNum , Integer pageSize) {
        PageInfo<ClusterWei> page = PageHelper.startPage(pageNum,pageSize).doSelectPageInfo(clusterWeiMapper::listAll);
        return new PageDTO<>(page);

    }

    @Override
    public List<ClusterWei> listRecentRecords() {
        return ClusterScheduledTasks.get();
    }
}
