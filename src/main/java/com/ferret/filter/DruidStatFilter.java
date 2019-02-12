package com.ferret.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 配置druid过滤器
 *
 * @author cc;
 * @version 1.0
 * @since 2018/3/26;
 */

@WebFilter(
        filterName = "druidWebStatFilter",
        urlPatterns = {"/*"},
        initParams = {
                //配置本过滤器放行的请求后缀
                @WebInitParam(name = "exclusions", value = "/druid/*")
        }
)
public class DruidStatFilter extends WebStatFilter {
}
