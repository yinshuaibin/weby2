package com.ferret.controller;

import com.ferret.bean.InterfaceBean.BuKongJH;
import com.ferret.service.bukong.BuKongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 布控记录的接口,处理增删改查等操作
 * @author qyn
 * @version 1.0
 */
@Slf4j
@RestController
public class BuKongController extends BaseController{
	
	@Resource
	private BuKongService buKongService;

	/**
	 * 单人布控     y  1225
	 * @param buKongJH  布控实体类
	 * @return
	 */
	@RequestMapping(value = "/addOneBuKong",method = RequestMethod.POST)
	public String addOneBuKong(@RequestBody BuKongJH buKongJH){
		return buKongService.addOneBuKong(buKongJH);
	}

	/**
	 * 单人布控删除  y 1225
	 * @param businessid  唯一业务id
	 * @return
	 */
	@RequestMapping(value = "/delBuKongByBusinessid/{businessid}",method = RequestMethod.GET)
	public String delBuKongByBusinessid(@PathVariable("businessid") String businessid){
		return buKongService.delBuKongByBusinessid(businessid);
	}

	/**
	 * 批量布控删除   y 1225
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "/delBuKongByIds")
	public String delBuKongByIds(@RequestBody Map reqMap){
		return buKongService.delBuKongByIds((List<String>)reqMap.get("ids"));
	}

	/**
	 * 修改布控信息, 根据唯一业务id修改   y 1225
	 * @param buKongJH
	 * @return
	 */
	@RequestMapping(value = "/uptadeBuKong",method = RequestMethod.POST)
	public String uptadeBuKong (@RequestBody BuKongJH buKongJH){
		return buKongService.uptadeBuKong(buKongJH);
	}

	/**
	 * 新接口撤控, 复控   y 1225
	 * @param businessid  唯一业务id
	 * @param status       状态码   0:撤控  1:复控
	 */
	@RequestMapping(value = "/restartBukong/{businessid}/{status}", method = RequestMethod.GET)
	public String updateBukongStatus(@PathVariable("businessid") String businessid,@PathVariable("status") String status ){
		return buKongService.updateBukongStatus(businessid,status);
	}

    /**
     * 批量撤控,复控   y 1225
     * @param reqMap
     * @return
     */
	@RequestMapping(value = "/restartBukongs",method = RequestMethod.POST)
    public String updateBuKongStatus(@RequestBody Map reqMap){
	    return buKongService.updateBukongStatus((List<String>)reqMap.get("businessids"),(String)reqMap.get("status"));
    }

    /**
     * @Description 根据布控分组Id 分页查询布控分组下布控人员
     * @param contorlType 布控分组Id
     * @param pageNum
     * @param pageSize 每页个数
     * @date 2019-01-19 14:57:24
     * @author xieyingchao
     */
	@RequestMapping(value = "/findBuKongByContorlType/{contorlType}/{pageNum}/{pageSize}",method = RequestMethod.GET)
	public Map findBuKongByContorlType(@PathVariable("contorlType") String contorlType,
                                       @PathVariable("pageNum") Integer pageNum,
                                       @PathVariable("pageSize")Integer pageSize){
		Map respMap = new HashMap();
		if(pageNum == 1){
			//说明是第一次查询,查询总条数
			respMap.put("totalNum",buKongService.findBuKongByContorlTypeCount(contorlType));
		}
		respMap.put("resultList",buKongService.findBuKongByContorlType(contorlType,pageSize,pageNum));
		return respMap;
	}

	/**
	 * 分页模糊匹配查询布控信息   y1225
	 * @param reqMap
	 * @return
	 */
	@RequestMapping(value = "/findBuKongList",method = RequestMethod.POST)
	public Map findBuKongList(@RequestBody Map reqMap){
		Map respMap = new HashMap();
		List<String> typeIds = (List<String>)reqMap.get("contorlTypeIds");
		String reason = (String)reqMap.get("reason");
		Integer pageNum = (Integer)reqMap.get("pageNum");
		Integer pageSize = (Integer)reqMap.get("pageSize");
		Integer totalNum = (Integer)reqMap.get("totalNum");
		String startTime = (String)reqMap.get("startTime");
		String endTime = (String)reqMap.get("endTime");
		String status = (String)reqMap.get("status");
		Integer state = null;
		if(null != status && !status.equals("")){
            state = Integer.parseInt(status);
        }
		if(null ==totalNum || pageNum == 1){
			//说明是第一次查询,查询总条数
			respMap.put("totalNum",buKongService.findBuKongListCount(typeIds,reason,startTime,endTime,state));
		}
		respMap.put("resultList",buKongService.findBuKongResultList(typeIds,reason,startTime,endTime,pageSize,pageNum,state));
		return respMap;

	}
}
