package com.ferret.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ferret.bean.searchimage.People;
import com.ferret.bean.searchimage.PeopleResult;
import com.ferret.bean.searchimage.SearchImage;
import com.ferret.dao.provider.PeopleResultMapperProvider;


@Mapper
public interface PeopleResultMapper {
	
	/**
	 * @see 存储单条查询结果
	 * @param PeopleResult
	 * @return int 存储结果
	 * @author qin
	 * */
	@Insert("INSERT into tb_task_result (taskid,sysid,idcard,similay) VALUES(#{taskId},#{sysId},#{idCard},#{similay})")
	Integer insertPeopleResult(PeopleResult peopleResult);
	
	/**
	 * @see 批量插入
	 * @param PeopleResult
	 * @return int 存储结果
	 * @author qin
	 * */
	@Insert({"<script>",
			"INSERT into tb_task_result (taskid,sysid,idcard,similay,listnum)",
			"values",
			"<foreach collection=\"peopleResults\" item=\"item\" index=\"index\" separator=\",\">",
			"(#{item.taskId},#{item.sysId},#{item.idCard},#{item.similay},#{item.listNum})",
			"</foreach>",
			"</script>"
			})
	Integer insertPeopleResults(@Param("peopleResults")List<PeopleResult> peopleResults);
	
	/**
	 * @see 根据任务id,查询任务结果
	 * @param String 任务名称
	 * @return List<People>
	 * @author qin
	 * */
	/*@Select("select a.* from (\r\n" + 
			"select idcard,SUM(similay) t_similay from tb_task_result where taskid=#{taskId}  group by idcard) a ORDER BY a.t_similay")
	List<People> findPeopleResult(@Param("taskId")String taskId);*/
	@Select("call proc_multistatic(#{taskId})")
	@Results({
        @Result(property = "taskId",column = "taskid"),
        @Result(property = "idCard",column = "idcard"),
        @Result(property = "yitu",column = "yitu"),
        @Result(property = "yuncong",column = "yuncong"),
        @Result(property = "keda",column = "keda"),
        @Result(property = "haikang",column = "haikang"),
        @Result(property = "tuming",column = "tuming"),
        @Result(property = "yitus",column = "yitu_s"),
        @Result(property = "yuncongs",column = "yuncong_s"),
        @Result(property = "kedas",column = "keda_s"),
        @Result(property = "haikangs",column = "haikang_s"),
        @Result(property = "tumings",column = "tuming_s"),
        @Result(property = "similay",column = "similay"),
        @Result(property = "listNum",column = "listnum"),
        @Result(property = "rn",column = "rn")
	})
	List<People> findPeopleResult(@Param("taskId")String taskId);

	@Select("SELECT taskid,idcard,listnum,similay, " + 
			"		case sysid \r\n" + 
			"		when 'tuming' then '图铭' " + 
			"		when 'haikang' then '海康' " + 
			"		when 'yitu' then '依图' " + 
			"		when 'yuncong' then '云从 ' " + 
			"		when 'kedacom' then '科达' " + 
			"		end sysid from tb_task_result  where taskId = #{taskId} and idcard=#{idCard}")
	@Results({
        @Result(property = "taskId",column = "taskid"),
        @Result(property = "idCard",column = "idcard"),
        @Result(property = "similay",column = "similay"),
        @Result(property = "sysId",column = "sysid"),
        @Result(property = "listNum",column = "listnum"),
	})
	List<People> peopleInfo(@Param("taskId")String taskId,@Param("idCard") String idCard);
	/**
	 * @see 数据库保存批量图片的信息
	 * @param 无
	 * @return 
	 * @author zwc
	 * */
	@InsertProvider(type=PeopleResultMapperProvider.class, method = "saveImageInfo")
	void saveImageInfo(SearchImage simg);
	
}
