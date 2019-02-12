package com.ferret.service.common.user;

import com.ferret.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @pack: com.ferret.service.common.user;
 * @auth: Administrator;
 * @since: 2017/12/27 0027;
 * @desc:
 */
public interface UserService {

    /** 根据username查询用户信息*/
    User findByUsername(@Param("username")String username);

    /** 增加用户信息*/
    User saveUser(User user);

    /** 修改用户信息*/
    void updateUser(User user);


    /** 删除用户信息*/
    void deleteUser(@Param("userId") String userId);
    /** 总条数*/
    int findCount(String areaId);
    /** 分页查询,根据此角色的地区id,查询出此用户下,所有的子用户*/
    List<User> findByArea(Integer pageNo,
                           Integer pageSize,String areaId);

    /**
     * 根据用户名查询对应的用户
     * @param idCard
     * @return
     */
    User findUserByIdCard(String idCard);

    /**
     * 根据用户名和身份证号校验该身份证号是否重复
     * @param idCard
     * @param userId
     * @return
     */
    User checkUserByIdcardAndUserId(String idCard,String userId);
}
