package com.ferret.service.dynamic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ferret.bean.CameraInfo;
import com.ferret.bean.InterfaceBean.Dynamic;
import com.ferret.bean.InterfaceBean.DynamicFaceResult;
import com.ferret.bean.InterfaceBean.InterfaceSearchParam;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.dao.RealTimeImageMapper;
import com.ferret.service.dynamic.ImageDynamicService;
import com.ferret.utils.InterfaceUtils.InterfaceRequest;
import com.ferret.utils.InterfaceUtils.ResultCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 动态检索
 * @Author xieyingchao 2018/12/26
 */
@Slf4j
@Service
public class ImageDynamicServiceImpl implements ImageDynamicService {

    @Value("${webservice.wsdlUrl}")
    private String wsdlUrl;
    @Value("${interfaceservice.faceFeatureExtraction}")
    private String reloadFaceSearchUrl;

    //用来封装接口参数
    private JSONObject jsonParam=new JSONObject();

    @Autowired
    private RealTimeImageMapper realTimeImageMapper;

    /**
     * @Description 人脸比对服务重新加载人脸库请求
     * @date 2019-01-23 09:55:19
     * @author xieyingchao
     */
    @Override
    public boolean reloadFaceSearch() {
        jsonParam.clear();
        jsonParam.put(InterfaceSearchParam.METHOD, InterfaceSearchParam.MEMORY_FEATURE_RELOAD_FORM_DATABASE);
        String rs = InterfaceRequest.interfaceRequest(reloadFaceSearchUrl, jsonParam);
        //转换成对应的结果实体类
        DynamicFaceResult dynamicFaceResult = JSON.parseObject(rs, new TypeReference<DynamicFaceResult>() {});
        if( null != dynamicFaceResult){
            String message = ResultCodeUtils.errMessage(dynamicFaceResult.getResultcode(), dynamicFaceResult.getStatus());
            if(InterfaceSearchParam.SUCCESS.equals(message)){
                return true;
            }
        }
        return false;
    }

    /**
     * @Descriptions 动态检索重新加载请求
     * @param loadType All 加载历史和今天， REAL 只加载今天
     * @return
     */
    @Override
    public boolean reloadFaceTrauls(String loadType) {
        jsonParam.clear();
        jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.FEATURE_RE_LOAD);
        jsonParam.put(InterfaceSearchParam.LOAD_TYPE,loadType);
        String rs = InterfaceRequest.interfaceRequest(wsdlUrl, jsonParam);
        //转换成对应的结果实体类
        DynamicFaceResult dynamicFaceResult = JSON.parseObject(rs, new TypeReference<DynamicFaceResult>() {});
        if( null != dynamicFaceResult){
            String message = ResultCodeUtils.errMessage(dynamicFaceResult.getResultcode(), dynamicFaceResult.getStatus());
            if(InterfaceSearchParam.SUCCESS.equals(message)){
                return true;
            }
        }
        return false;
    }
    /**
     * @Description 动态检索
     * @param startTime 开始时间(yyyy-MM-dd)
     * @param endTime 结束时间(yyyy-MM-dd)
     * @param threshold 阈值
     * @param imageData 图片Base64
     * @param cameraIds 相机ID数组
     * @param projectName 任务名称
     * @param count 返回张数
     * @date 2019-01-02 13:53:29
     * @author xieyingchao
     */
    @Override
    public List<Dynamic> DynamicFaceTrauls(String startTime, String endTime,float threshold, String imageData, List<String> cameraIds,String projectName,int count) {
        List<Dynamic> dynamics = new ArrayList<>();
        jsonParam.clear();
        jsonParam.put(InterfaceSearchParam.METHOD, InterfaceSearchParam.TRAILS);
        jsonParam.put(InterfaceSearchParam.FILE_1, imageData.substring(imageData.indexOf(",")+1).replaceAll("\r|\n", ""));
        jsonParam.put(InterfaceSearchParam.THRESHOLD, threshold);
        jsonParam.put(InterfaceSearchParam.CAMERA_ID_LIST, cameraIds);
        jsonParam.put(InterfaceSearchParam.PROJECT_NAME, projectName);
        jsonParam.put(InterfaceSearchParam.YEAR_BEGIN, getYear(startTime));
        jsonParam.put(InterfaceSearchParam.MONTH_BEGIN, getMonth(startTime));
        jsonParam.put(InterfaceSearchParam.DAY_BEGIN ,getDay(startTime));
        jsonParam.put(InterfaceSearchParam.YEAR_END, getYear(endTime));
        jsonParam.put(InterfaceSearchParam.MONTH_END, getMonth(endTime));
        jsonParam.put(InterfaceSearchParam.DAY_END, getDay(endTime));
        String rs = InterfaceRequest.interfaceRequest(wsdlUrl, jsonParam);
        //转换成对应的结果实体类
        DynamicFaceResult dynamicFaceResult = JSON.parseObject(rs, new TypeReference<DynamicFaceResult>() {});
        if( null != dynamicFaceResult){
            String message = ResultCodeUtils.errMessage(dynamicFaceResult.getResultcode(), dynamicFaceResult.getStatus());
            if(InterfaceSearchParam.SUCCESS.equals(message)){
                List<Dynamic> dynamicResultList = dynamicFaceResult.getTrails_Pic_LIST();
                if(null != dynamicResultList && dynamicResultList.size() > 0) {
                    // 根据比值排序
                    dynamicResultList.sort(new Comparator<Dynamic>() {
                        @Override
                        public int compare(Dynamic o1, Dynamic o2) {
                            return (int)(o1.getScore() * 100)- (int) (o1.getScore() * 100);
                        }
                    });
                    // 根据返回数量 判断是否截断数组
                    if(dynamicResultList.size() > count){
                        dynamicResultList = dynamicResultList.subList(0,count);
                    }
                    // 根据featureId查询相机信息
                    for (Dynamic dynamic:dynamicResultList) {
                        dynamic = getDynamicCameraInfo(dynamic);
                        dynamics.add(dynamic);
                    }
                }
                return dynamics;
            }
        }
        return null;
    }
    /**
     * @Description 根剧featureId查询相机信息，并添加入对象中
     * @param dynamic
     * @date 2019-01-02 15:38:34
     * @author xieyingchao
     */
    Dynamic getDynamicCameraInfo(Dynamic dynamic) {
        Dynamic dynamic1 = realTimeImageMapper.getRealTimeImageAndCamera(new BigInteger(dynamic.getFeatureId()));
        dynamic1.setScore(dynamic.getScore());
        dynamic1.setFeatureId(dynamic.getFeatureId());
        return dynamic1;
    }

    /**
     * @Description 获取年
     * @date 2019-01-02 13:56:03
     * @author xieyingchao
     */
    private String getYear (String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sf.parse(time);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
    private String getYear (Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * @Description 获取月
     * @date 2019-01-02 13:56:21
     * @author xieyingchao
     */
    private String getMonth (String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sf.parse(time);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(calendar.get(Calendar.MONTH)+1);
    }
    private String getMonth (Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return String.valueOf(calendar.get(Calendar.MONTH)+1);
    }
    /**
     * @Description 获取日期
     * @date 2019-01-02 13:56:44
     * @author xieyingchao
     */
    private String getDay (String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sf.parse(time);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }
    private String getDay (Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }
}
