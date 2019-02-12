package com.ferret.filter;

import com.ferret.common.base.Common;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 *
 * @author Administrator
 */
@Slf4j
@WebFilter(filterName = "loginFilter", urlPatterns = "/**", asyncSupported = true, initParams = {
		// 配置本过滤器放行的请求后缀
		@WebInitParam(name = "exclusions", value = "*.js,*.jpg,*.png,*.gif,*.ico,*.css,*.mp3,/druid/*") })
public class LoginFilter implements Filter, Common {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 获取请求url url:/face/getBKGroup
		chain.doFilter(request,response);
	}

	@Override
	public void init(FilterConfig arg0) {
		log.debug("过滤器启动");
	}
}
