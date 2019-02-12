package com.ferret.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.staticBean.*;
import com.ferret.dao.StaticMapper;
import com.ferret.service.common.StaticService;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.utils.ImagePrefixProperties;
import com.ferret.utils.JayCommonUtil;
import com.ferret.utils.ListSortUtils;
import com.ferret.utils.staticUtils.JsonListUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wd
 */

@Service
public class StaticServiceImpl implements StaticService {

	@Autowired
	private StaticMapper staticMapper;
	
	@Autowired
	private ImagePrefixProperties imagePrefixProperties;
	
	@Value("${static.HNSTStatic}")
	private String HNSStaticUrl;
	
	@Value("${static.XZJStatic}")
	private String XZJStaticUrl;
	
	@Override
	public void handleJPGData(String localPathZP) {
		File ZPFile = new File(localPathZP);
		File[] listFiles = ZPFile.listFiles();
		if (listFiles!= null  && listFiles.length > 0) {
            for (int i = 0; i < listFiles.length; i++) {
                File file = listFiles[i];
                String name = file.getName();
                if (StringUtils.isNotBlank(name) && name.contains(".")) {
                    String[] split = name.split(".");
                    if(split[1].equals("jpg")) {
                        //调取接口处理图片，并将其与返回信息存入数据库
                        //存之前先查询对应逃犯编号是否有编号有直接一同存入若无则先存入图片路径
                    }
                }
            }
        }
	}
	
	/**
	 * 根据指定条件查询静态数据
	 */
	@Override
	public List<List<?>> searchStaticData(SeachStatic data) {
		String img1 = data.getImg1();
		List<String> createImgData = ImageBase64Utils.createImgData(imagePrefixProperties.getStaticDir());
        String imageName = createImgData.get(0);
        String imagePath = createImgData.get(1);
        imagePath = imagePath + imageName + ".jpg";
        
        String[] split = img1.split(",");
        if(split.length==2) {
        	img1 = split[1];
        }
        ImageBase64Utils.generateImage(img1, imagePath);
		
		List<String> prots = data.getProts();
		String sex = data.getSex();
		float threshold = data.getThreshold();
		int smallAge = data.getSmallAge();
		int bigAge = data.getBigAge();
		int count = data.getCount();
		//prots以及sex处理
		String disposeSex = this.disposeSex(sex);
		//创建省厅参数胡实体类
		Search search = new Search();
		search.setImageBase64(img1);
		search.setMinage(smallAge);
		search.setMaxage(bigAge);
		search.setThreshold(threshold/10);
		search.setResultSize(count);
		search.setSex(disposeSex);
		//创建刑侦局参数胡实体类
		SearchXzj search2 = new SearchXzj();
		search2.setImageBase64(img1);
		search2.setMinage(smallAge);
		search2.setMaxage(bigAge);
		search2.setThreshold(threshold/10);
		search2.setResultSize(count);
		search2.setSex(disposeSex);
		List<FaceReturnData> callSStatic = null;
		List<FaceReturnData> callXStatic = null;
		//不会出现没有库id的现象，至少会有一个
		if(prots.contains("1")) {//是否查询常口库
			//查询省厅常口所需数据
			callSStatic = callSStatic(search,imagePath);
		}
		if(prots.contains("2")) {//是否查询前科库全部
			String[] paramValue = {"0","1","2","3","4","5","6","7","8","9"};
			search2.setPort(paramValue);
			callXStatic = callXStatic(search2,imagePath);
		}else{//未全选前科库
			List<String> param = prots;
			String[] paramValue = disposePorts(param);
			search2.setPort(paramValue);
			callXStatic = callXStatic(search2,imagePath);
		}
		ListSortUtils<?> lsu = new ListSortUtils<>();
		List<List<?>> splitList = null;
		List<FaceReturnData> callStatic = new ArrayList<>();
		System.err.println(prots.contains("1"));
		System.err.println(prots.contains("2"));
		List<FaceReturnData> subList = null;
		//结果处理
		if(prots.contains("1")&&prots.contains("2")) {//是否全部查询
			System.out.println("全部查询-------------------------------------");
			callSStatic.addAll(callXStatic);
			lsu.sort(callSStatic, "threshold", "desc");
			if(callSStatic.size()>15) {
				subList = callSStatic.subList(0, count);
			}
			System.err.println(callStatic.size());
		}else if(!prots.contains("1")&&prots.contains("2")) {//是否只查询前科库全部
			System.out.println("只查询前科库全部------------------------------");
			lsu.sort(callXStatic, "threshold", "desc");
			subList = callXStatic.subList(0, count);
			System.err.println(callStatic.size());
		}else if(prots.contains("1") && !prots.contains("2") && prots.size() > 1) {//是否查询常口以及前科库部分
			System.out.println("查询常口以及前科库部分-------------------------------");
			callXStatic.addAll(callSStatic);
			lsu.sort(callXStatic, "threshold", "desc");
			subList = callXStatic.subList(0, count);
		}else if(!prots.contains("1") && !prots.contains("2") && prots.size() > 1) {//是否只查询前科库部分
			System.out.println("只查询前科库部分-------------------------------");
			lsu.sort(callXStatic, "threshold", "desc");
			subList = callXStatic.subList(0, count);
		}else{//是否只查询常口库
			System.out.println("只查询常口库----------------------------------");
			lsu.sort(callSStatic, "threshold", "desc");
			subList = callSStatic.subList(0, count);
			System.err.println(callStatic.size());
		}
		if(null != callSStatic) {
			callStatic.addAll(subList);
		}
		splitList = JayCommonUtil.splitList(callStatic, 10);
		return splitList;
	}
	
	public String disposeSex(String sex) {
		String sex2 = "NONE";
		if(sex.equals("男")) {
			sex2 = "MAN";
		}else if(sex.equals("女")) {
			sex2 = "WOMAN";
		}
		return sex2;
	}
	
	public String[] disposePorts(List<String> prots) {
		prots.remove("1");
		prots.remove("2");
		String[] paramValue = new String[prots.size()];
		prots.toArray(paramValue);
		return paramValue;
	}
	
	public List<FaceReturnData> callSStatic(Search search,String imageOldPath) {//调用省厅接口
		RestTemplate restTemplate = new  RestTemplate();
		Object postForObject = restTemplate.postForObject(HNSStaticUrl, search, Object.class);
		String jsonString = JSONObject.toJSONString(postForObject);
		List<PersonData> data = JsonListUtil.jsonToList(jsonString, PersonData.class);
		System.err.println("省厅接口返回"+data.size()+"条数据--------------------------------");
		List<FaceReturnData> ruquestData = new ArrayList<>();
		for (PersonData personData : data) {
			String imagepath = personData.getImagepath();
			List<String> createImgData = ImageBase64Utils.createImgData(imagePrefixProperties.getStaticDir());
            String imageName = createImgData.get(0);
            String imagePath = createImgData.get(1);
            imagePath = imagePath + imageName + ".jpg";
            ImageBase64Utils.generateImage(imagepath, imagePath);
            FaceReturnData frd = new FaceReturnData();
            frd.setImageOldPath(imageOldPath);
            frd.setId(personData.getIdcard());
            frd.setImagePath(imagePath);
            frd.setName(personData.getName());
            frd.setThreshold(personData.getThreshold());
            frd.setProperty("河南省  常口库");
            ruquestData.add(frd);
		}
		return ruquestData;
	}
	
	public List<FaceReturnData> callXStatic(SearchXzj search,String imageOldPath) {//调用刑侦局接口
		RestTemplate restTemplate = new  RestTemplate();
		Object postForObject = restTemplate.postForObject(XZJStaticUrl, search, Object.class);
		String jsonString = JSONObject.toJSONString(postForObject);
		List<PersonInfo> data = JsonListUtil.jsonToList(jsonString, PersonInfo.class);
		System.err.println("刑侦局接口返回"+data.size()+"条数据--------------------------------");
		List<FaceReturnData> ruquestData = new ArrayList<>();
		for (PersonInfo personData : data) {
			String imagepath = personData.getImagepath();
			List<String> createImgData = ImageBase64Utils.createImgData(imagePrefixProperties.getStaticDir());
            String imageName = createImgData.get(0);
            String imagePath = createImgData.get(1);
            imagePath = imagePath + imageName + ".jpg";
            ImageBase64Utils.generateImage(imagepath, imagePath);
            FaceReturnData frd = new FaceReturnData();
            frd.setImageOldPath(imageOldPath);
            frd.setId(personData.getIdcard());
            frd.setImagePath(imagePath);
            frd.setName(personData.getName());
            frd.setThreshold(personData.getThreshold());
            frd.setProperty(personData.getRecord());
            ruquestData.add(frd);
		}
		return ruquestData;
	}

	@Override
	public List<StaticPorts> queryPorts() {//查询所有细分库id，姓名
		System.err.println("开始查询----------");
		List<StaticPorts> queryPorts = staticMapper.queryPorts();
		
		return queryPorts;
	}
	
}
