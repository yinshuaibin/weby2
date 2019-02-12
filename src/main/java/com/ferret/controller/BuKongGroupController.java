package com.ferret.controller;

import com.ferret.bean.InterfaceBean.BuKongContorlType;
import com.ferret.service.bukong.BuKongGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator;
 * @since 2018/1/26;
 */
@RestController
public class BuKongGroupController extends BaseController{

    @Autowired
    private BuKongGroupService buKongGroupService;

    /**
     * 分页查询所有布控分组   y 1225
     * @return
     */
    @RequestMapping(value = "/findAllBuKongGroup/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Map findAllBuKongGroup(@PathVariable("pageNum") int pageNum,
                                  @PathVariable("pageSize") int pageSize){
        Map respMap = new HashMap();
        if(pageNum == 1){
            //说明是第一次查询,查询总条数
            respMap.put("totalNum",buKongGroupService.findAllBuKongGroupCount());
        }
        respMap.put("resultList",buKongGroupService.findAllBuKongGroup(pageNum,pageSize));
        return respMap;
    }

    /**
     * @Description 查询所有布控分组
     * @date 2019-01-22 16:12:05
     * @author xieyingchao
     */
    @RequestMapping(value = "/findAllBuKongGroupList", method = RequestMethod.GET)
    public List<BuKongContorlType>  findAllBuKongGroupList(){
        return buKongGroupService.findAllBuKongGroup();
    }


    /**
     * 校验分组名称  分组名称不能重复   y 1226
     * @param name
     * @return
     */
    @RequestMapping(value = "/checkBuKongGroupName")
    public String checkBuKongGroupName(String name){
        return buKongGroupService.checkBuKongGroupName(name);
    }

    /**
     * 添加布控分组  y 1226
     * @param buKongGroup
     * @return
     */
    @RequestMapping(value = "/addBuKongGroup",method = RequestMethod.POST)
    public String addBuKongGroup(@RequestBody  BuKongContorlType buKongGroup){
        return buKongGroupService.addBuKongGroup(buKongGroup);
    }

    /**
     * 根据布控分组id删除该布控分组(如果该分组下有布控数据,则不允许删除)  y 1226
     * @param buKongGroupId
     * @return
     */
    @RequestMapping(value = "/delBuKongGroup")
    public String delBuKongGroup(String buKongGroupId){
        return buKongGroupService.delBuKongGroup(buKongGroupId);
    }

    /**
     * 修改布控分组信息   y 1226
     * @param buKongGroup
     * @return
     */
    @RequestMapping(value = "/updateBuKongGroup",method = RequestMethod.POST)
    public String updateBuKongGroup(@RequestBody  BuKongContorlType buKongGroup){
        return buKongGroupService.updateBuKongGroup(buKongGroup);
    }
    /**
     * @Description  根据布控分组Id修改布控分组布控状态
     * @param isControl 0：否 1：是
     * @param id 布控分组Id
     * @date 2019-01-22 09:45:58
     * @author xieyingchao
     */
    @RequestMapping(value = "/updateBukongIsControl/{isControl}/{id}", method = RequestMethod.GET)
    public String updateControlTypeIsControl(@PathVariable("isControl") int isControl,
                                             @PathVariable("id") int id){
        return buKongGroupService.updateBuKongIscontrol(isControl,id);
    }
}
