package com.ferret.service.common.impl;

import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferret.bean.searchimage.FaceService;
import com.ferret.bean.searchimage.People;
import com.ferret.bean.searchimage.PeopleResult;
import com.ferret.bean.searchimage.SearchImage;
import com.ferret.dao.FaceServiceMapper;
import com.ferret.dao.PeopleResultMapper;
import com.ferret.service.common.SearchImageService;
import com.ferret.utils.DBUtiles;


@Service("searchService")
public class SearchImageServiceImpl implements SearchImageService{
	@Autowired
	private PeopleResultMapper peopleResultMapper;
	@Autowired
	private FaceServiceMapper faceServiceMapper;
	private DBUtiles dbUtiles;
	/**
	 * @see 存储单条查询结果
	 * @param PeopleResult
	 * @return int 存储结果
	 * @author qin
	 * */
	@Override
	public Integer insertPeopleResult(PeopleResult result) {
		return peopleResultMapper.insertPeopleResult(result);
	}
	
	/**
	 * @see 批量插入
	 * @param PeopleResult
	 * @return int 存储结果
	 * @author qin
	 * */
	@Override
	public Integer insertPeopleResult(List<PeopleResult> results) {
		return peopleResultMapper.insertPeopleResults(results);
	}
	
	/**
	 * @see 根据任务id,查询任务结果
	 * @param String 任务名称
	 * @return List<People>
	 * @author qin
	 * */
	@Override
	public List<People> findPeopleResult(String taskId) {
		// TODO Auto-generated method stub
		List<People> pepoleLs=peopleResultMapper.findPeopleResult(taskId);
		if(pepoleLs.size()>0) {
			if(dbUtiles==null) {
				dbUtiles=new DBUtiles("156","com.mysql.jdbc.Driver",
						"jdbc:mysql://10.56.12.156:3306/db_checkface?characterEncoding=utf8&useSSL=false",
						"root","123456",5,3,10
						);
			}
			String sql="select idcard,name,imagepath,sex from tb_persons where idcard in (?) GROUP BY idcard";
			StringBuffer idCardStrs=new StringBuffer();
			for(People pe:pepoleLs) {
				idCardStrs.append("'").append(pe.getIdCard()).append("',");
			}
			idCardStrs.deleteCharAt(idCardStrs.length()-1);
			sql=sql.replaceFirst("[?]", idCardStrs.toString());
			idCardStrs.setLength(0);
			ResultSet rs=dbUtiles.mysqlQuery1(sql);
			String idCard,name,imagepath,sex;
			try {
				while(rs.next()) {
					idCard=rs.getString(1);
					name=rs.getString(2);
					imagepath=rs.getString(3);
					sex=rs.getString(4);
					for(People pe:pepoleLs) {
						if(StringUtils.equals(idCard, pe.getIdCard())) {
							pe.setName(name);
							String flag=imagepath.substring(0,1);
							if(StringUtils.equalsIgnoreCase("d", flag)) {
								 imagepath = imagepath.replace("d:\\FaceImage", "http://10.56.12.156:8082");
							}
							if(StringUtils.equalsIgnoreCase("f", flag)) {
								imagepath = imagepath.replace("F:/FaceImage2017", "http://10.56.12.156:8082");
							}
							pe.setImgUrl(imagepath);
							pe.setSex(sex);
						}
					}
				}
				rs.close();
				dbUtiles.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return pepoleLs;
	}
	
	@Override
	/**
	 * @see 查出现有比对服务的url
	 * @param 无
	 * @return List<FaceService>
	 * @author qin
	 * */
	public List<FaceService> queryFaceService(){
		return faceServiceMapper.queryFaceService();
	}

	/**
	 * @see 查出某个人的各个厂商的结果
	 * @param String taskId 任务标记,String idCard 身份证号
	 * @return 
	 * @author qin
	 * */
	public List<People> peopleInfo(String taskId,String idCard){
		return peopleResultMapper.peopleInfo(taskId, idCard);
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.equals("d:\\FaceImage", "d:\\FaceImage"));
	}
	
	/**
	 * @see 数据库保存批量图片的信息
	 * @param 无
	 * @return 
	 * @author zwc
	 * */
	@Override
	public void saveImageInfo(SearchImage simg) {
		peopleResultMapper.saveImageInfo(simg);
	}
	
}
