package com.ferret.dao;

import com.ferret.bean.User;
import com.ferret.dao.provider.UserMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
	/** 查询用户信息 */
	List<User> findAllUser();

	/**
	 * 查询总条数
	 * 
	 * @return
	 */
	@Select("select count(*) from tb_role r left join tb_user u on r.role_id = u.role_id where u.enabled = 1")
	int findCount();

	/**
	 * 根据地区id查询符合条件的总条数
	 */
	@SelectProvider(type = UserMapperProvider.class, method = "findCountByArea")
	int findCountByArea(@Param("areaId") String areaId);

	/** 根据username查询用户信息 */
	@Select("SELECT * FROM tb_user WHERE  username=#{username}")
	User findByUsername(@Param("username") String username);

	/** 增加用户信息 */
	@Insert("INSERT INTO tb_user(" + "user_id,salt,role_id,username,nickname,password,"
			+ "login_ip,dt_create,last_login,area_id,area_name,"
			+ "scores,email,telephone,office_phone,enabled,account_non_expired,"
			+ "account_non_locked,credentials_non_expired,idcard,jnumber,createtime,overtime)" + "VALUES"
			+ "(#{userId},#{salt},#{roleId},#{username},#{nickname},#{password},"
			+ "#{loginIp},#{dtCreate},#{lastLogin},#{areaId},#{areaName},"
			+ "#{scores},#{email},#{telephone},#{officePhone},#{enabled},#{accountNonExpired},"
			+ "#{accountNonLocked},#{credentialsNonExpired},#{idCard},#{jnumber},#{createTime},#{overTime})")
	int insertUser(User user);

	/** 修改用户信息 */
	@Update("UPDATE tb_user SET role_id=#{roleId},salt=#{salt},username=#{username},"
			+ "nickname=#{nickname},password=#{password}," + "login_ip=#{loginIp},dt_create=#{dtCreate},"
			+ "last_login=#{lastLogin},area_id=#{areaId},area_name=#{areaName},"
			+ "scores=#{scores},email=#{email},telephone=#{telephone},"
			+ "office_phone=#{officePhone},enabled=#{enabled},"
			+ "account_non_expired=#{accountNonExpired},account_non_locked=#{accountNonLocked},"
			+ "credentials_non_expired=#{credentialsNonExpired},idcard=#{idCard},jnumber=#{jnumber},overtime=#{overTime} " + "WHERE user_id=#{userId}")
	int updateUser(User user);

	/** 删除用户信息 */
	@Delete("DELETE FROM tb_user WHERE user_id=#{userId}")
	int deleteUser(@Param("userId") String userId);

	/**
	 * 查询: 根据用户名查询用户信息(包括对应角色及角色对应权限)
	 * 
	 * @param username
	 * @return
	 */
	@Select("SELECT user_id, role_id,salt, username, nickname, login_ip, password, dt_create, "
			+ "last_login, area_id, area_name, scores, email, telephone, office_phone, enabled, "
			+ "account_non_expired, account_non_locked, credentials_non_expired,createtime,overtime " + "FROM tb_user "
			+ "WHERE username = #{username} AND enabled = 1 and overtime >= now()")
	@Results({ @Result(id = true, property = "userId", column = "user_id"),
			@Result(property = "roleId", column = "role_id"), @Result(property = "username", column = "username"),
			@Result(property = "nickname", column = "nickname"), @Result(property = "loginIp", column = "login_ip"),
			@Result(property = "password", column = "password"), @Result(property = "dtCreate", column = "dt_create"),
			@Result(property = "lastLogin", column = "last_login"), @Result(property = "areaId", column = "area_id"),
			@Result(property = "areaName", column = "area_name"), @Result(property = "scores", column = "scores"),
			@Result(property = "email", column = "email"), @Result(property = "telephone", column = "telephone"),
			@Result(property = "officePhone", column = "office_phone"),
			@Result(property = "enabled", column = "enabled"),
			@Result(property = "jnumber", column = "jnumber"),
			@Result(property = "accountNonExpired", column = "account_non_expired"),
			@Result(property = "accountNonLocked", column = "account_non_locked"),
			@Result(property = "credentialsNonExpired", column = "credentials_non_expired"),
			@Result(property="city",column="area_id",one = @One(select = "com.ferret.dao.CityMapper.findCityByArea")),
			@Result(property = "role", column = "role_id", one = @One(select = "com.ferret.dao.RoleMapper.selectByRoleId")),
			@Result(property = "createTime", column = "createtime"),
			@Result(property = "overTime", column = "overtime")})
	User selectByUsername(String username);

	/**
	 * 查询用户对应地区id下的或同级的地区的用户
	 * @param areaId
	 * @return
	 */
	@SelectProvider(type = UserMapperProvider.class, method = "findByArea")
	@Results({ @Result(property = "roleId", column = "role_id"), @Result(property = "loginIp", column = "login_ip"),
			@Result(property = "dtCreate", column = "dt_create"),
			@Result(property = "lastLogin", column = "last_login"), @Result(property = "areaId", column = "area_id"),
			@Result(property = "areaName", column = "area_name"),
			@Result(property = "officePhone", column = "office_phone"),
			@Result(property = "accountNonExpired", column = "account_non_expired"),
			@Result(property = "accountNonLocked", column = "account_non_locked"),
			@Result(property = "accountNonLocked", column = "account_non_locked"),
			@Result(property = "jnumber", column = "#")})
	List<User> findByArea(@Param("areaId") String areaId);
	
	/**
	 * 删除角色时使用,如果有用户拥有对应的角色, 则不让其删除此角色
	 * @param roleId
	 * @return
	 */
	@Select("select * from tb_user where role_id=#{roleId}")
	List<User> findUserByRoleId(String roleId);

	/**
	 * 用户登录成功后,将用户的登录信息,存入到数据库中
	 */
	@Insert("insert into tb_user_login (user_id,user_name,login_ip,login_time) values(#{userId},#{username},#{loginIp},now())")
	int insertUserLogin(User user);


	/**
	 * 根据身份证号查询对应的用户
	 * @param idCard
	 * @return
	 */
	@Select("select * from tb_user where idcard = #{idCard}")
	@Results({ @Result(id = true, property = "userId", column = "user_id"),
			@Result(property = "roleId", column = "role_id"), @Result(property = "username", column = "username"),
			@Result(property = "nickname", column = "nickname"), @Result(property = "loginIp", column = "login_ip"),
			@Result(property = "password", column = "password"), @Result(property = "dtCreate", column = "dt_create"),
			@Result(property = "lastLogin", column = "last_login"), @Result(property = "areaId", column = "area_id"),
			@Result(property = "areaName", column = "area_name"), @Result(property = "scores", column = "scores"),
			@Result(property = "email", column = "email"), @Result(property = "telephone", column = "telephone"),
			@Result(property = "officePhone", column = "office_phone"),
			@Result(property = "enabled", column = "enabled"),
			@Result(property = "jnumber", column = "jnumber"),
			@Result(property = "accountNonExpired", column = "account_non_expired"),
			@Result(property = "accountNonLocked", column = "account_non_locked"),
			@Result(property = "credentialsNonExpired", column = "credentials_non_expired"),
			@Result(property="city",column="area_id",one = @One(select = "com.ferret.dao.CityMapper.findCityByArea")),
			@Result(property = "role", column = "role_id", one = @One(select = "com.ferret.dao.RoleMapper.selectByRoleId")),
			@Result(property = "createTime", column = "createtime"),
			@Result(property = "overTime", column = "overtime")})
	User findUserByIdCard(String idCard);

	/**
	 *
	 * @param idCard
	 * @param userId
	 * @return
	 */
	@Select("select * from tb_user where idcard = #{idCard} and user_id != #{userId}")
	User checkUserByIdcardAndUserId(@Param("idCard")String idCard,@Param("userId")String userId);


	/**
	 * @Description  判断用户是否过期
	 * @param userId 用户唯一Id
	 * @param today 现在时间 （yyyy-MM-dd） 格式
	 * @date 2019-01-21 14:29:16
	 * @author xieyingchao
	 */
	@Select("SELECT count(user_id) FROM tb_user WHERE user_id = #{userId} AND overtime >= #{today}")
	int findUserByOverTime(@Param("userId") String userId,@Param("today") String today);
}