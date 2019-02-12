package com.ferret.controller;

import com.ferret.bean.User;
import com.ferret.exception.BusinessException;
import com.ferret.service.common.login.LoginService;
import com.ferret.service.common.user.UserService;
import com.jit.util.GACertParser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

	@Resource
	private LoginService loginService;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	@Value("${mapIp}")
	private String mapIp;

	private static final String CERT_KEY = "javax.servlet.request.X509Certificate";

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	@Deprecated
	public User loginCheck(@RequestBody User loginUser,HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		try {
		UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getUsername(),loginUser.getPassword());
			subject.login(token);
		} catch (IncorrectCredentialsException e) {
			throw new BusinessException("密码错误");
		} catch (AuthenticationException e) {
			throw new BusinessException("该用户不存在或已经过期,请换个用户登录");
		} catch (NullPointerException e) {
			throw new BusinessException("用户名或密码不存在");
		}
		User user = (User) subject.getPrincipal();
		// 将用户登录信息存入数据库
		loginService.insertLoginMessage(user,request);
		// 将用户信息存入session
		session.setAttribute("user", user);
		// 七天过期时间
		session.setMaxInactiveInterval(604800);
		// 对返回的用户密码进行清除
		user.setPassword(null);
		return user;
	}


	@GetMapping(value = "/loginCert")
	public ResponseEntity<?> loginCert(HttpServletRequest request) {
		try {
			// 从requst对象中获取用户证书
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute(CERT_KEY);
			if (certs == null) {
				return ResponseEntity.badRequest().build();
			} else {
				X509Certificate cert = certs[0];
				GACertParser gc = new GACertParser();// 获取证书对象、提取证书中各项内容的值
				gc.setCert(cert);
				// 获取岗位编码
				// 证书DN中签发的人名、身份证号、组织机构编码
				String[] v3 = gc.getName_IDN_UID();
				String name = v3[0];
				String idNum = v3[1];
				String gNum = v3[2];
				System.out.println(name + ";" + idNum + ";" + gNum);

				User user = userService.findUserByIdCard(idNum);
				if(user != null) {
					session.setAttribute("user", user);
					// 七天过期时间
					session.setMaxInactiveInterval(604800);
					user.setPassword(null);
					return ResponseEntity.ok(user);
				}
				return ResponseEntity.badRequest().build();
			}
		} catch (Exception e2) {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * 用户登陆后，获取地图的ip
	 */
	@GetMapping("/face/mapIp")
	public String getMapUrl(){
		return  mapIp;
	}

	/**
	 * 点击退出按钮退出当前用户,清除session中的用户信息  y0815
	 * @return
	 */
	@RequestMapping("/face/removeUser")
	public String removeUser(){
		session.removeAttribute("user");
		return  "退出成功";
	}
}
