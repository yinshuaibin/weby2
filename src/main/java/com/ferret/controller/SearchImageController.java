package com.ferret.controller;

import com.ferret.bean.searchimage.*;
import com.ferret.utils.FileUtils;
import com.ferret.service.common.SearchImageService;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.utils.ImagePrefixProperties;
import com.ferret.utils.NameUtils;
import com.ferret.utils.TaskFaceUtils;
import com.ferret.utils.readCluster.tools.CalendTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;




@RestController
public class SearchImageController extends BaseController{
	
	@Autowired
	private ImagePrefixProperties imagePrefixProperties;
	@Autowired
	private SearchImageService searchService;
	//@Value("${image.prefix.clusterReverseDir}")
	//接收各个厂家比对结果的服务
	private static List<FaceService> services;
	
	private String imgDir=CalendTools.getTimeString().substring(0, 8);
	@RequestMapping(value = "/faceresult", method = RequestMethod.POST)
	public BackMsg faceResult(@RequestBody SearchResult sr) {
		System.err.println("接收结果"+sr.getSysId()+" 时间："+CalendTools.getTimeString());
		if(sr.getResult()!=null&&sr.getResult().length()>20) {
			//将收到的比对结果转换为PeopleResult对象
			List<PeopleResult>ls=TaskFaceUtils.resovlePeopleResult(sr);
			//结果排序
			Collections.sort(ls);
			//存储比对结果
			int i=1;
			StringBuffer idCardBuff=new StringBuffer();
			for(PeopleResult result:ls) {
				if(idCardBuff.indexOf(result.getIdCard())==-1&&result.getIdCard().length()<=18) {
					idCardBuff.append(result.getIdCard()).append(",");
					result.setListNum(i++);
					//System.err.println(" idcard:"+result.getIdCard().length());
				}
			}
		}
		//返回消息
		BackMsg msg=new BackMsg();
		msg.setTaskId(sr.getTaskId());
		msg.setBackMsg("0");
		return msg;
	}
	//计算最终的综合结果
	@RequestMapping(value = "/faceCollect", method = RequestMethod.POST)
	public List<People> faceCollect(@RequestBody SearchImage simg) {
		System.out.println("-----------aa:"+simg.getTaskId());
		List<People>ls=searchService.findPeopleResult(simg.getTaskId());
		return ls;
	}
	//批量比对图片上传
	@RequestMapping(value = "/imgUpload/{batchTaskId}",method = RequestMethod.POST)
	//public Map imgUpload(HttpServletRequest request) throws Exception{
	public Map imgUpload(@RequestParam("file") MultipartFile upload,@PathVariable("batchTaskId") String batchTaskId) throws Exception {
		String taskId = batchTaskId + NameUtils.getFiveNum();
		String imageName = taskId+".jpg";
		System.out.println("batchTaskId---"+batchTaskId+"---------taskId------"+imageName);
		Map<String, String> map = new HashMap<String, String>();
		//System.err.println("name:"+upload.getOriginalFilename());
		FileUtils.uploadFile( upload, imagePrefixProperties.getUploadDir()+"/"+imgDir+"/",imageName);
        map.put( "name", imagePrefixProperties.getUploadDir()+"/"+imgDir+"/"+imageName );
        map.put( "size", upload.getSize()+"");
        
        map.put( "status", "finished" );
      //  map.put( "state", "sucess" );
       // map.put("url", imagePrefixProperties.getUploadUrl()+"/"+imgDir+"/"+upload.getOriginalFilename());
        map.put("url", imagePrefixProperties.getUploadUrl()+"/"+imgDir+"/"+imageName);
	    return map;

	}

	
	//批量比对
	@RequestMapping(value = "/batchPicStatic", method = RequestMethod.POST)
	public SearchImage searchImage(@RequestBody SearchImage simg) {
		List<UploadImgList> uploadImgList = simg.getUploadImgList();
//		for (UploadImgList uploadImgList2 : uploadImgList) {
//			System.out.println(uploadImgList2.getName()+"------"+uploadImgList2.getUrl());
//		}
		if(services==null) {
			services=searchService.queryFaceService();
		}
		System.err.println("服务列表长度:"+services.size());
		for (UploadImgList everyImgInfo : uploadImgList) {
			SearchImage everySearchImage = new SearchImage();
			everySearchImage.setThreshold(0.1f);
			String everyImageUrl = everyImgInfo.getUrl();
			if (StringUtils.isNotBlank(everyImageUrl)) {
				//生成uid
				String taskId = everyImageUrl.substring(everyImageUrl.lastIndexOf("/")+1,everyImageUrl.lastIndexOf("."));
				System.err.println("----------截取的字符串taskid---"+taskId);
				//设置比对任务的taskId
				everySearchImage.setTaskId(taskId);
				//把url转成base64
				String getImageStr = ImageBase64Utils.getImageStr(everyImgInfo.getName());
				everySearchImage.setImageBase64(getImageStr);
				everySearchImage.setResultSize(simg.getResultSize());
				everySearchImage.setThreshold(simg.getThreshold());
				for(FaceService faceService:services) {
					TaskFaceUtils taskUtils=new TaskFaceUtils(everySearchImage, faceService.getValue());
					System.err.println("发送请求"+faceService.getName()+" 时间："+CalendTools.getTimeString());
					taskUtils.start();
					//TaskFaceUtils.sendTask(simg, faceService.getValue());
				}
			}
		}
		simg.setResultSize(uploadImgList.size());
		return simg;
	}
	
	//计算最终的综合结果
		@RequestMapping(value = "/peopleInfo", method = RequestMethod.POST)
		public List<People> peopleInfo(@RequestBody People p) {
			
			List<People>ls=new ArrayList();
			People p1=new People();
			p1.setIdCard("410124");
			p1.setListNum(2);
			p1.setSimilay(0.75f);
			p1.setSysId("海康");
			People p2=new People();
			p2.setIdCard("410124");
			p2.setListNum(2);
			p2.setSimilay(0.75f);
			p2.setSysId("图铭");
			ls.add(p1);
			ls.add(p2);
		//	List<People>ls=searchService.peopleInfo(p.getTaskId(), p.getIdCard());
			return ls;
		}
	//发送比对任务,返回任务id给接口调用方
	@RequestMapping(value = "/sendTask", method = RequestMethod.POST)
	public String sendTask(@RequestBody SearchImage simg) {
		//得到批量图片的信息 保存数据库
		//searchService.saveImageInfo(simg);
		
		if(services==null) {
			services=searchService.queryFaceService();
		}
		//System.err.println("服务列表长度:"+services.size());
		simg.setThreshold(0.1f);
		//生成uid
		String taskId=UUID.randomUUID().toString().replaceAll("[-]","");
		//设置比对任务的taskId
		simg.setTaskId(taskId);
		for(FaceService faceService:services) {
			TaskFaceUtils taskUtils=new TaskFaceUtils(simg, faceService.getValue());
			//System.err.println("发送请求"+faceService.getName()+" 时间："+CalendTools.getTimeString());
			taskUtils.start();
			//TaskFaceUtils.sendTask(simg, faceService.getValue());
		}
		return taskId;
	}
}
