package com.ferret.service.bukong.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.BuKong;
import com.ferret.bean.ImageFeature;
import com.ferret.bean.InterfaceBean.BuKongJH;
import com.ferret.bean.InterfaceBean.InterfaceSearchParam;
import com.ferret.bean.User;
import com.ferret.bean.bukong.NewBuKong;
import com.ferret.bean.bukong.RevokeBuKong;
import com.ferret.bean.pagebean.BuKongPage;
import com.ferret.bean.pagebean.SelectBuKongPage;
import com.ferret.common.base.Common;
import com.ferret.dao.BuKongGroupMapper;
import com.ferret.dao.BuKongMapper;
import com.ferret.dao.RealTimeAlarmMapper;
import com.ferret.dto.BuKongDTO;
import com.ferret.dto.PageDTO;
import com.ferret.service.bukong.BuKongService;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.utils.ImagePrefixProperties;
import com.ferret.utils.InterfaceUtils.InterfaceRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tm.com.TmClusterQryLib;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BuKongServiceImpl implements BuKongService, Common {

    @Autowired
    private ImagePrefixProperties imagePrefixProperties;

    //没有传递身份证号时,图片名称中添加此字段
    private static final String NULL_SEND_CARD = "null_send_idcard";

    @Resource
    private BuKongMapper buKongMapper;

    @Autowired
    private BuKongGroupMapper buKongGroupMapper;

    @Resource
    private RealTimeAlarmMapper realTimeAlarmMapper;

    /**
     * 单个添加布控信息   y 1224
     * 使用接口实时添加
     * @param buKongJH  实体类
     */
    @Override
    public String addOneBuKong(BuKongJH buKongJH) {
        if(null == buKongJH){
            return "请传入正确的布控信息";
        }
        int count = buKongGroupMapper.findBkGroupCountById(buKongJH.getContorltype());
        if(count < 1){
            return  "布控分组选择错误,请正确选择布控分组";
        }
        JSONObject jsonParam = new JSONObject();
        //设置调用的接口的方法名称
        jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.ADD_LIST);
        List<Object> dataList=new ArrayList<>();
        dataList.add(setInterfaceOnePerson(buKongJH));
        jsonParam.put(InterfaceSearchParam.ADD_DATA_LIST,dataList);
        //调用工具类发送请求,获得结果
        String result = InterfaceRequest.interfaceResultIsFaceImageResult(imagePrefixProperties.getInterfaceUrl()+InterfaceSearchParam.FACE_SEARCH, jsonParam);
        if(InterfaceSearchParam.SUCCESS.equals(result)){
            //添加成功后,修改数据库的布控原因(中间件不支持写布控原因)
            buKongMapper.updateBukongConreason(buKongJH.getBusinessid(),buKongJH.getConreason(),buKongJH.getConage());
        }
        return result;
    }

    /**
     * 添加多个布控人员   y 1224
     * 使用接口实时添加
     * @param buKongJHS 实体类集合
     */
    @Override
    public String addBuKongList(List<BuKongJH> buKongJHS) {
        // 批量布控的布控分组id一定相同,先验证布控分组是否存在
        if(buKongJHS == null && buKongJHS.size() < 1){
            return "请传入正确的布控参数";
        }
        int count = buKongGroupMapper.findBkGroupCountById(buKongJHS.get(0).getContorltype());
        if(count < 1){
            return  "布控分组选择错误,请正确选择布控分组";
        }
        JSONObject jsonParam = new JSONObject();
        //设置调用的接口的方法名称
        jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.ADD_LIST);
        List<Object> dataList=new ArrayList<>();
        for (BuKongJH buKongJH : buKongJHS){
            dataList.add(setInterfaceOnePerson(buKongJH));
        }
        jsonParam.put(InterfaceSearchParam.ADD_DATA_LIST,dataList);
        //调用工具类发送请求,获得结果
        String result= InterfaceRequest.interfaceResultIsFaceImageResult(imagePrefixProperties.getInterfaceUrl()+InterfaceSearchParam.FACE_SEARCH,jsonParam);
        if(InterfaceSearchParam.SUCCESS.equals(result)){
            //添加成功后,修改数据库的布控原因(中间件不支持写布控原因)
            for(BuKongJH buKongJH : buKongJHS){
                buKongMapper.updateBukongConreason(buKongJH.getBusinessid(),buKongJH.getConreason(),buKongJH.getConage());
            }
        }
        return result;
    }


    /**
     * 单人布控删除  y 0119  改:删除时,无法删除有报警信息的布控数据
     * @param businessid  唯一业务id
     * @return
     */
    @Override
    public String delBuKongByBusinessid(String businessid){
        // 根据布控业务id查询报警信息, 如果有报警信息,禁止删除该布控数据
        int alarmCountByBukongBusinessid = realTimeAlarmMapper.findAlarmCountByBukongBusinessid(businessid);
        if(alarmCountByBukongBusinessid >0){
            return "无法删除有报警数据的布控信息";
        }
        JSONObject jsonParam = new JSONObject();
        //设置调用的接口的方法名称
        jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.REMOVE_BY_BUSINESS_ID);
        jsonParam.put(InterfaceSearchParam.BUSINESSID,businessid);
        return  InterfaceRequest.interfaceResultIsFaceImageResult(imagePrefixProperties.getInterfaceUrl()+InterfaceSearchParam.FACE_SEARCH,jsonParam);
    }

    /**
     * 批量布控删除   y 0119 改:如果传递过来布控id集合中有的布控信息有报警数据, 则禁止删除该布控信息,只删除没有报警数据的布控信息
     * @param ids 布控id集合
     * @return
     */
    @Override
    public String delBuKongByIds(List<String> ids){
        if( null != ids && ids.size()>0){
            int idSize = ids.size();
            // 根据传递的布控id,筛选出报警过的布控id,这些布控信息不删除
            List<String> alarmBuKongIds = realTimeAlarmMapper.findAlarmBuKongIds(ids);
            // 将不需要删除的布控信息的id删除掉
            if(null !=alarmBuKongIds && alarmBuKongIds.size()>0){
                for(int x=0; x<ids.size() ;x++){
                    for (String id :alarmBuKongIds){
                        if(ids.get(x).equals(id)){
                            ids.remove(x);
                            x--;
                        }
                    }
                }
            }
            // 如果传递过来的布控数据全部都有报警数据,返回删除条数0
            if(ids.size() <1 ){
                return "0";
            }
            JSONObject jsonParam = new JSONObject();
            //设置调用的接口的方法名称
            jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.REMOVE_BY_IDS);
            //拼接字符串,供接口使用
            StringBuffer sb = new StringBuffer();
            for (String id :ids){
                sb.append(id).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            jsonParam.put(InterfaceSearchParam.REMOVE_IDS,sb.toString());
            String s = InterfaceRequest.interfaceResultIsFaceImageResult(imagePrefixProperties.getInterfaceUrl() + InterfaceSearchParam.FACE_SEARCH, jsonParam);
            // 如果传递过来的id集合经过了筛选,那么返回页面删除条数
            if(idSize > ids.size()){
                return ids.size()+"";
            }
            // 否则返回页面接口调用的返回值
            return s;
        }
        return "传入参数为空,请传入正确的参数";
    }


    /**
     * 删除布控分组下的所有布控信息
     * @param contorlType 布控分组id  y 1225
     * @return
     */
    @Override
    public String delBuKongByContorlType(String contorlType){
        //查询布控分组下的id
        List<String> ids = buKongMapper.findBuKongIdsByContorlType(contorlType);
        //删除这些布控人员
        return  this.delBuKongByIds(ids);
    }

    /**
     * 修改布控状态,达到删除/恢复布控的效果 y 1224
     * 使用接口实时修改 (如果该布控人员状态为生效, 继续传递生效状态码,将会出现错误)
     * @param businessid 唯一业务id
     * @param status 状态码: 0:不生效,1:生效
     */
    @Override
    public String updateBukongStatus(String businessid,String status) {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("businessid",businessid);
        jsonParam.put("status",status);
        jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.EDIT_BY_BUSINESS_ID);
        //调用工具类发送请求,获得结果
        return InterfaceRequest.interfaceResultIsFaceImageResult(imagePrefixProperties.getInterfaceUrl()+InterfaceSearchParam.FACE_SEARCH,jsonParam);
    }

    /**
     * 修改布控状态,达到删除/恢复布控的效果   y 1225
     * 使用接口实时修改
     * @param businessids  唯一业务id集合
     * @param status 状态码: 0:不生效, 1:生效
     * @return
     */
    public  String updateBukongStatus(List<String> businessids, String status){
        if(null != businessids && null != status){
            for (String businessid:businessids){
                updateBukongStatus(businessid, status);
            }
        }
        return InterfaceSearchParam.SUCCESS;
    }


    @Override
    public String uptadeBuKong(BuKongJH buKongJH) {
        if(buKongJH !=null){
            int count = buKongGroupMapper.findBkGroupCountById(buKongJH.getContorltype());
            if(count < 1){
                return  "布控分组选择错误,请正确选择布控分组";
            }
            int i = buKongMapper.uptadeBuKongByBusinessid(buKongJH);
            if( i == 1 ){
                return  InterfaceSearchParam.SUCCESS;
            }
        }
        return "修改信息失败,请传入正确信息";
    }

    /**
     * 根据布控分组id分页查询布控信息  y1224
     * @param contorType
     * @return
     */
    @Override
    public List<BuKongJH> findBuKongByContorlType(String contorType, Integer pageSize, Integer pageNum) {
      List<BuKongJH> buKongResultList = buKongMapper.findBuKongByContorlType(contorType,pageSize,(pageNum -1)*pageSize);
        if(null != buKongResultList && buKongResultList.size() > 0){
            for(BuKongJH bk :buKongResultList){
                // 替换成网络路径
                bk.setImagePath(imagePrefixProperties.getBukongImageUrl()+bk.getImagePath());
            }
            return buKongResultList;
        }
        return null;
    }

    /**
     * 根据布控分组id查询该分组下总布控条数  y1224
     * @param contorType
     * @return
     */
    @Override
    public Integer findBuKongByContorlTypeCount(String contorType) {
        return buKongMapper.findBuKongByContorlTypeCount(contorType);
    }

    /**
     * 条件模糊分页查询布控信息   y 1225
     * @param controTypeIds  布控分组id
     * @param reason         用来模糊匹配的条件
     * @param startTime      布控开始时间
     * @param endTime        布控结束时间
     * @param pageSize       每页记录数
     * @param pageNum        当前页数
     * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空   '' 或 null
     * @return
     */
    @Override
    public List<BuKongJH> findBuKongResultList(List<String> controTypeIds, String reason, String startTime, String endTime, Integer pageSize, Integer pageNum,Integer status) {
        List<BuKongJH> buKongResultList = buKongMapper.findBuKongResultList(controTypeIds, reason, startTime, endTime, pageSize, (pageNum - 1) * pageSize,status);
        if(null != buKongResultList && buKongResultList.size() > 0){
            for(BuKongJH bk :buKongResultList){
                // 替换成网络路径
                bk.setImagePath(imagePrefixProperties.getBukongImageUrl()+bk.getImagePath());
            }
            return buKongResultList;
        }
        return null;
    }



    /**
     * 条件模糊分页查询布控信息总数   y 1225
     * @param controTypeIds  布控分组id
     * @param reason         用来模糊匹配的条件
     * @param startTime      布控开始时间
     * @param endTime        布控结束时间
     * @param status         布控状态: 0:撤控 1:布控 如果撤控布控都查询,则此字段传空 '' 或 null
     * @return
     */
    @Override
    public Integer findBuKongListCount(List<String> controTypeIds, String reason, String startTime, String endTime,Integer status) {
        return buKongMapper.findBuKongListCount(controTypeIds,reason,startTime,endTime,status);
    }

    /**
     * 根据前台传递过来的实体类,转换成接口需要的字段名字,设置进map,供接口使用(根据类型不同,分为添加和修改,参数设置部分不同)
     * @param buKongJH
     * @return
     */
    Map<String,Object> setInterfaceOnePerson(BuKongJH buKongJH){
        Map<String,Object> dataOneMap = new HashMap<>();
        //用来格式化当前时间,图片名称中需要
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        //用来格式化前台传递的时间,供中间件使用
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //防止传递过来的base64不符合要求
        if(buKongJH.getImageData() != null){
            buKongJH.setImageData(buKongJH.getImageData().replaceAll("\r|\n", "").substring(buKongJH.getImageData().indexOf(",")+1));
        }
        //设置图片的base64
        dataOneMap.put("file", buKongJH.getImageData());
        dataOneMap.put("name", buKongJH.getName());
        //设置身份证号
        dataOneMap.put("cardnumber", buKongJH.getCardnumber());
        //设置唯一业务id,使用uuid
        String businessid = UUID.randomUUID().toString();
        dataOneMap.put("businessid",businessid);
        buKongJH.setBusinessid(businessid);
        //设置创建时间,前台不传递,则为当前时间
        if(null == buKongJH.getCreateTime()) {
            dataOneMap.put("createtime",dateFormat.format(new Date()));
        }else {
            dataOneMap.put("createtime",dateFormat.format(buKongJH.getCreateTime()));
        }
        //设置静态库类型id(该选项必填,页面控制,后台不再判断)
        dataOneMap.put("contorltype", buKongJH.getContorltype());
        // 设置状态 为1 生效(默认)
        dataOneMap.put("status","1");
        //设置上传文件的名字
        //如果没有传递文件名称,则使用  当前时间_身份证号.jpg
        if( null == buKongJH.getFileName()){
            StringBuffer sb = new StringBuffer(simpleDateFormat.format(new Date()));
            if(buKongJH.getCardnumber()!=null){
                sb.append("_").append(buKongJH.getCardnumber());
            }else {
                sb.append("_").append(NULL_SEND_CARD);
            }
            sb.append(".jpg");
            dataOneMap.put("filename",sb.toString());
        }else {
            dataOneMap.put("filename", buKongJH.getFileName());
        }
        //设置备注
        dataOneMap.put("remark", buKongJH.getRemark());
        //设置性别
        dataOneMap.put("sex", buKongJH.getSex());
        //设置家庭住址
        dataOneMap.put("address", buKongJH.getAddress());
        //设置相似度分值
        dataOneMap.put("similar", buKongJH.getSimilar());
        return dataOneMap;
    }
}
