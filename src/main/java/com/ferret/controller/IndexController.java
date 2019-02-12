package com.ferret.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Tomcat地址映射至登录页面
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
}
