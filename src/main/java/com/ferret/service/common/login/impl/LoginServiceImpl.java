package com.ferret.service.common.login.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ferret.bean.User;
import com.ferret.exception.BusinessException;
import com.ferret.dao.UserMapper;
import com.ferret.service.common.login.LoginService;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Resource
	private UserMapper userMapper;

	@Override
	public void insertLoginMessage(User user,HttpServletRequest request) throws BusinessException{
		//用户登录成功,存储登录信息表
        user.setLoginIp(getIpAddr(request));
        userMapper.insertUserLogin(user);
		System.out.println(user.getRoleId());
	}

    @Override
    public User loginByUserName(String userName) {
        //根据用户名查询信息
        return userMapper.selectByUsername(userName);
    }

    public  String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != inet) {
                    ip = inet.getHostAddress();
                }
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
