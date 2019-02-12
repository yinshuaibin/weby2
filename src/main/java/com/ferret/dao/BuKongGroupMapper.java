package com.ferret.dao;

import com.ferret.bean.InterfaceBean.BuKongContorlType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuKongGroupMapper {
    /**
     * 分页查询所有布控分组   y 1225
     * @return
     */
    @Select("select * from jh_controlpeople_type limit #{startNum},#{pageSize}")
    List<BuKongContorlType> findAllBuKongGroup(@Param("startNum") int startNum, @Param("pageSize") int pageSize);

    /**
     * @Description 查询布控分组总数
     * @date 2019-01-22 15:39:35
     * @author xieyingchao
     */
    @Select("select count(id) from jh_controlpeople_type")
    int findAllBuKongGroupCount();

    /**
     * @Description 查询所有布控分组
     * @date 2019-01-22 16:08:58
     * @author xieyingchao
     */
    @Select("select * from jh_controlpeople_type")
    List<BuKongContorlType> findAllBuKongGroupList();

    /**
     * 根据布控分组名称查询是否有该分组名称   y 1226
     * @param groupName 布控分组名称
     * @return
     */
    @Select("select count(id) from jh_controlpeople_type where typename = #{groupName}")
    int findGroupByGroupName(String groupName);

    /**
     * 添加布控  y 1226
     * @param bukongGroup
     * @return
     */
    @Insert("insert into jh_controlpeople_type values (null,#{typename},1,now(),#{remark})")
    int addBuKongGroup(BuKongContorlType bukongGroup);

    /**
     * 根据布控分组id删除对应的布控分组  y 1226
     * @param groupId 布控分组id
     * @return
     */
    @Delete("delete from  jh_controlpeople_type where id = #{groupId}")
    int delBuKongGroup(String groupId);

    /**
     * 修改布控分组信息   y 1226
     * @param bukongGroup
     * @return
     */
    @Update("update jh_controlpeople_type set typename = #{typename},remark = #{remark} where id = #{id}")
    int updateBuKongGroup(BuKongContorlType bukongGroup);

    @Select("select count(id) from jh_controlpeople_type where id = #{id}")
    int findBkGroupCountById(String id);

    /**
     * @Description 根据布控分组Id修改布控分组布控状态
     * @param isControl 是否布控 0：否 1：是
     * @param id 布控分组id
     * @date 2019-01-22 09:35:53
     * @author xieyingchao
     */
    @Update("update jh_controlpeople_type set iscontrol = #{isControl} where id = #{id}")
    int updateBuKongIscontrol(@Param("isControl") int isControl,@Param("id") int id);

    /**
     * 通过 人员类型id  查询人员类型名称
     * @param id
     * @return
     */
    @Select("SELECT typename from jh_controlpeople_type WHERE id = #{controltypeId} ")
    String getControltypeName(@Param("controltypeId") Integer id);

}