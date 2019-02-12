package com.ferret.dao;
import com.ferret.bean.ClusterPass;
import com.ferret.bean.Stranger;
import com.ferret.dao.provider.ClusterWeiMapperProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterWeiZuMapper {

    /**
     * 根据时间段查询维族人
     * @param startTime 开始时间
     * @param endTime	结束时间
     * @param startNum  开始条数
     * @return
     */
    @Select("call cluster_date_wei(#{startTime},#{endTime},#{startNum},#{minNum})")
    @Options(statementType = StatementType.CALLABLE)
    @Results({ 	@Result(property="personId",column="person_id"),
            @Result(property="createTime",column="create_time"),
            @Result(property="imagePath",column="image_path"),
            @Result(property="cameraName",column="name")
    })
    List<ClusterPass> findClusterWeiByTime(@Param("startTime") String startTime,
                                           @Param("endTime") String endTime, @Param("startNum") Integer startNum, @Param("minNum") Integer minNum);
    /**
     * 通过时间段,查询维族档案总条数
     * @param startTime
     * @param endTime
     * @param minNum
     * @return
     */
    @Select("SELECT count(person_id) FROM tb_cluster_wei WHERE create_time>=#{startTime} AND create_time<=#{endTime} AND count>=#{minNum}")
    Integer findTotalByDate(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("minNum")int minNum);

    /**
     * 根据条件查询tb_cluster_wei中最新的6条聚类数据
     * y 1012
     * @return
     */
    @SelectProvider(type = ClusterWeiMapperProvider.class, method = "getNewClusterWei")
    @Results({ @Result(property = "personId", column = "person_id"),
            @Result(property = "imagePath", column = "image_path"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "cameraName", column = "name") })
    public List<Stranger> getNewClusterWei();


    /**
     * 根据person_id查询tb_cluster_image对应的所有数据  分页
     * 修改内容:按照时间倒叙排列
     * @param personId
     * @param startNum
     * @param pageSize
     * @return
     */
    @Select("SELECT tc.person_id,tc.image_path,tc.create_time start_time,tb.`name`,tb.longitude,tb.latitude "
            + "from tb_cluster_image tc LEFT JOIN tb_camera_info tb on tc.camera_ip=tb.ip WHERE tc.person_id=#{person_Id} order by tc.create_time desc limit #{startNum},#{pageSize}")
    @Results({
            @Result(property="personId",column="person_id"),
            @Result(property="imagePath",column="image_path"),
            @Result(property="cameraName",column="name"),
            @Result(property="createTime",column="start_time"),
            @Result(property="longitude",column="longitude"),
            @Result(property="latitude",column="latitude"),
    })
    List<ClusterPass> findClusterByPersonId(@Param("person_Id")String personId, @Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize);

    /**
     * 根据person_id查询tb_cluster_image对应的所有数据总条数
     * @return
     */
    @Select("SELECT count(person_id) FROM tb_cluster_image where person_id = #{personId}")
    Integer findTotalByPersonId(@Param("personId") String personId);

    @Select("SELECT represent_img1 from tb_cluster_wei WHERE person_id = #{personId} ")
    @Results({
            @Result(property="represent_img1",column="represent_img1"),
    })
    ClusterPass getPhotoByPersonId(String personId);


}
