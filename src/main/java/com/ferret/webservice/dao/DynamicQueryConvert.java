package com.ferret.webservice.dao;

import com.ferret.bean.CameraInfo;
import com.ferret.bean.DynamicQueryEntity;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.utils.ImageFeatureUtils;
import com.ferret.webservice.client.CommitQueryCmd;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cc;
 * @since 2018/1/17;
 * 修改原因:前台增加了一个勾选摄像头功能,改了前台传递过来的摄像头数据,原来的不再使用  y 0811
 */
@Service
public class DynamicQueryConvert {

    @Autowired
    private CameraInfoMapper cameraInfoMapper;

    public CommitQueryCmd convertCommitQueryCmd(DynamicQueryEntity entity) {

        // 1. 根据特征key获取对应的特征数据
        Assert.isTrue(StringUtils.hasLength(entity.getImg1()) || StringUtils.hasLength(entity.getImg2()), "图片特征key不能为空");
        String feature1 = ImageFeatureUtils.get(entity.getImg1());
        String feature2 = ImageFeatureUtils.get(entity.getImg2());

        Assert.isTrue(StringUtils.hasLength(feature1) || StringUtils.hasLength(feature2), "特征超时或不存在,请重新上传图片");

        // 2. 获取摄像头摄像头ids
        List<Integer> ids = new ArrayList<>();
        List<CameraInfo> cameraList = entity.getCameraList();
        for(CameraInfo c:cameraList){
            ids.add(c.getId());
        }
        String cameraIds = StringUtils.collectionToDelimitedString(ids, ",");

        // 3.转换时间
//        XMLGregorianCalendar startTime = com.ferret.utils.DateUtils.convertXMLGregorianCalendar(entity.getStartTime());
//        XMLGregorianCalendar endTime = com.ferret.utils.DateUtils.convertXMLGregorianCalendar(entity.getEndTime());


        // 4.生成实体类;
        CommitQueryCmd commitQueryCmd = new CommitQueryCmd();
        // 将base64 的字符串 转成 byte 编码;
        // 用普通getBytes无法解析;
        commitQueryCmd.setFeature1(StringUtils.hasLength(feature1) ? Base64.decodeBase64(feature1) : null);
        commitQueryCmd.setFeature2(StringUtils.hasLength(feature2) ? Base64.decodeBase64(feature2) : null);
        commitQueryCmd.setCamIDs(cameraIds);
//        commitQueryCmd.setDtBegin(startTime);
//        commitQueryCmd.setDtEnd(endTime);
        commitQueryCmd.setFLimit(entity.getThreshold());
        commitQueryCmd.setNWantNum(entity.getCount());

        return commitQueryCmd;
    }
}
