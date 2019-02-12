package com.ferret.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ferret.utils.ImagePrefixProperties;

/**
 *
 * 跨域处理请求
 * @author cc;
 * @since 2018/1/2;
 */
@Configuration
public class MyWebAppConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST","PUT","OPTIONS","HEADER","PATCH","DELETE")
                .maxAge(3600);
    }
    
	@Autowired
	private ImagePrefixProperties imagePrefixProperties;
	
	
	/**
	 * 删除springboot的历史查询格式的图片本地映射,改为走IIS映射
	 * 修改人:y
	 * 修改时间:0718
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**").addResourceLocations("file:" + imagePrefixProperties.getUploadDir()+"/");
		registry.addResourceHandler("/bk/**").addResourceLocations("file:" + imagePrefixProperties.getBukongDir()+"/");
		registry.addResourceHandler("/clusters/**")
				.addResourceLocations("file:" + imagePrefixProperties.getClusterDir()+"/");
		registry.addResourceHandler("/searchImg/**")
		.addResourceLocations("file:" + imagePrefixProperties.getStaticDir()+"/");
	}

}
