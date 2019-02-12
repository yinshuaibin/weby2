package com.ferret.controller;

import com.ferret.bean.CameraGroup;
import com.ferret.service.camera.cameragroup.CameraGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.controller;
 * @auth: Administrator;
 * @since: 2017/12/21 0021;
 * @desc:
 */
@RestController
public class CameraGroupController extends BaseController {
    @Autowired
    private CameraGroupService cameraGroupService;

    /**
     * 增加相机分组数据
     *
     * @param cameraGroup
     * @return
     */
    @RequestMapping(value = "/cameraGroups", method = RequestMethod.POST)
    public Map doInsertCameraGroup(@RequestBody CameraGroup cameraGroup) {
        CameraGroup group = cameraGroupService.saveCameraGroup(cameraGroup);
        ResponseEntity<CameraGroup> responseEntity = new ResponseEntity<>(group, HttpStatus.CREATED);
        Map<String,Object> map = new HashMap<>();
        int selectSquences = cameraGroupService.selectSquences();
        map.put("value",selectSquences);
        map.put("responseEntity", responseEntity);
        return map;
    }
    /**
     * 增加相机分组数据
     *
     * @return
     */
    @RequestMapping(value = "/cameraGroup/transferData", method = RequestMethod.POST)
    public Integer transferData() {
        return cameraGroupService.selectSquences();
    }

    /**
     * 修改数据
     *
     * @param cameraGroup
     * @return
     */
    @RequestMapping(value = "/cameraGroup", method = RequestMethod.PUT)
    public ResponseEntity doUpdateCameraGroup(@RequestBody CameraGroup cameraGroup) {
        cameraGroupService.updateCameraGroup(cameraGroup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/cameraGroups/{id}", method = RequestMethod.DELETE)
    public void doDeleteCameraGroup(@PathVariable("id") Integer id) {

        cameraGroupService.deleteCameraGroup(id);

        System.out.println("删除成功");
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/cameraGroup/{id}", method = RequestMethod.GET)
    public CameraGroup doFindById(@PathVariable("id") Integer id) {
        CameraGroup c =
                cameraGroupService.findById(id);
        return c;
    }

    /**
     * 校验相机分组name
     * @param name
     * @return
     */
    @RequestMapping(value = "/checkCameraGroupName", method = RequestMethod.GET)
    public int checkCamerasName(String name){
        return cameraGroupService.selectCarmeraGroupByName(name);
    }
    /**
     * 根据地区id查询地区下相机分组集合并统计分组下相机数量
     * @param
     */
    @RequestMapping(value = "/getCameraGroupListCount", method = RequestMethod.POST)
    public ResponseEntity getCameraGroupCount(@RequestBody Map map){
        Map<String,Object> resultMap = new HashMap<>();
        int totalNumber = (Integer)(map.get("totalNum"));
        int pageNum = (Integer)(map.get("pageNum"));
        int pageSize = (Integer)(map.get("pageSize"));
        List<CameraGroup> listdate = cameraGroupService.findByCameraGroupCount((String)(map.get("code")),pageNum,pageSize);
        if (totalNumber < 1 && pageNum==1) {
            int totalNum = cameraGroupService.findByCameraGroupTotalNum((String)(map.get("code")));
            resultMap.put("totalNum",totalNum);
        } else {
            resultMap.put("totalNum",totalNumber);
        }
        resultMap.put("responseEntity", listdate);
        return ResponseEntity.ok(resultMap);
    }
}
