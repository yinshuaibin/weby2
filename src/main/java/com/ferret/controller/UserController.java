package com.ferret.controller;

import com.ferret.bean.User;
import com.ferret.bean.UserName;
import com.ferret.service.common.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @pack: com.ferret.controller;
 * @auth: xy;
 * @since: 2017/12/27 0027;
 * @desc:
 */
@RestController
@Slf4j
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 增加用户信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public Boolean doInsertUser(@RequestBody User user) {
		User findByUsername = userService.findByUsername(user.getUsername());
		if (findByUsername == null) {
			User users = userService.saveUser(user);
			if (users != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity doUpdateUser(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * 根据ID删除某用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity doDeleteUser(@PathVariable("userId") String userId) {

		userService.deleteUser(userId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * 查询用户信息(分页)
	 * 修改:不再从后台session中获取用户地区信息,改为从前台获取  y 0814
	 *
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> doFindAllUser(@RequestParam(value = "areaId")String areaId,@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, HttpServletRequest req) {
		// 根据页面返回的登录用户的地区id,查询出此用户下所有的子用户
		List<User> list = userService.findByArea(pageNo, pageSize, areaId);
		return list;
	}

	/**
	 * 总条数
	 * 修改:不再从后台session中获取地区id,从用户登录的前台中获取  y 0814
	 * @return
	 */
	@RequestMapping(value = "/usersCount", method = RequestMethod.GET)
	public int userCount(String areaId,HttpServletRequest req) {
        int userCount = userService.findCount(areaId);
		return userCount;
	}

	/**
	 * 根据用户名查询
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/doFindByUsername", method = RequestMethod.POST)
	@Deprecated
	public User doFindByUsername(@RequestBody UserName username) {
		User user = userService.findByUsername(username.getUsername());
	    return user;
	}

	/**
	 * 根据用户名查询
	 *
	 * @param reqMap
	 * @return
	 */
	//@RequestMapping(value = "/doFindByUsername", method = RequestMethod.POST)
	public User doFindByUsername(@RequestBody Map reqMap) {
		User user = userService.findByUsername((String)reqMap.get("username"));
		return user;
	}


	/**
	 * 根据身份证号查找对应的用户信息,如果即有身份证号又有用户id说明是校验用户身份证号是否重复
	 * @param idCard
	 * @return
	 */
	@RequestMapping(value = "/findUserByIdCard")
	public User findUserByIdCard(String idCard,String id){
		if(StringUtils.isNotBlank(id)){
			return userService.checkUserByIdcardAndUserId(idCard,id);
		}
		return userService.findUserByIdCard(idCard);
	}
}
