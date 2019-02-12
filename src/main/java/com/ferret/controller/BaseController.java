package com.ferret.controller;

import java.util.HashMap;


import com.ferret.bean.CameraGroup;
import com.ferret.bean.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ferret.bean.User;


@Controller
//此类为统一设置项目主路径
@RequestMapping("/face")
public abstract class BaseController extends ApplicationObjectSupport{
	//系统配置信息
	public static HashMap config=new HashMap();

	//用户实体类
	public static User user=null;
	public Logger logger = LoggerFactory.getLogger(getClass());
    //城市实体类
	public static City city=null;
	//相机分组实体类
	public static CameraGroup cameraGroup=null;
}
