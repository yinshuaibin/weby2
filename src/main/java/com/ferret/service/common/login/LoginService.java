package com.ferret.service.common.login;

import javax.servlet.http.HttpServletRequest;

import com.ferret.bean.User;
import com.ferret.exception.BusinessException;

public interface LoginService {
	/**
	 * 将登录信息存入数据库
	 * @param user
	 * @param request
	 * @return
	 */
	void insertLoginMessage(User user,HttpServletRequest request);


	/**
	 * 登录验证
	 */
	User loginByUserName(String userName) throws BusinessException;
}
