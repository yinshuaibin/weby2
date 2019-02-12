package com.ferret.service.common.impl;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.HistoryPass;
import com.ferret.bean.pagebean.HistoryParam;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.dao.HistoryPassMapper;
import com.ferret.dto.HistoryPassDTO;
import com.ferret.service.common.IHistoryPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator;
 * @since 2018/1/18;
 * 修改原因:前台增加了一个勾选摄像头功能,改了前台传递过来的摄像头数据,原来的不再使用  y 0811
 */
@Service
public class HistoryPassServiceImpl implements IHistoryPassService {

    @Autowired
    private HistoryPassMapper historyPassMapper;

    @Resource
    private CameraInfoMapper cameraInfoMapper;
    @Override
    public List<HistoryPassDTO> listHistoryPassDTO(Integer[] featureIds) {
        Assert.notNull(featureIds,"分页特征数据为空,无法查询!");
        return historyPassMapper.listHistoryPassDTO(featureIds);
    }

    /**
     * 修改人:hyl
     * 修改日期:0115
     * 修改原因:查询时不在使用存储过程
     */
    @Override
    public Map selectHistoryPass(HistoryParam param) {
    	Map<String,Object> map = new HashMap<>();
        //将相机集合中的ID取出组成新的集合，便于Sql操作
        List<CameraInfo> cameraList = param.getCameraList();
        List list = new ArrayList();
        for(CameraInfo c:cameraList){
            list.add(c.getId());
        }
        //计算分页开始
        int pageNum = param.getPageNum();
        pageNum = (pageNum - 1) * param.getPageSize();
        //查询历史记录
        List<HistoryPass> li = historyPassMapper.getHistoryPass(list,
                param.getStartDateTime(),
                param.getEndDateTime(),
                pageNum,param.getPageSize());
        //如果页面返回的总记录数小于1,并且分页开始页数为1,则查询总条数,否则不查询
        if(param.getDataTotal()<1&&param.getPageNum()==1) {
        	//查询记录总数
            int count = historyPassMapper.getHistoryPassCount(list, param.getStartDateTime(), param.getEndDateTime());
            map.put("count", count);
        }
        //将date与time合为一个属性
//        for (HistoryPass hp : li) {
//            hp.setImageDateTime(hp.getImagedate() + " " + hp.getImagetime());
//        }
        map.put("entity", li);
        return map;
    }

}
