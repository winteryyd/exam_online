package com.ando.exam.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
	@Autowired
	LoginInterceptor loginInterceptor;
	/**
	 * 不需要登录拦截的url
	 */
	final String[] notLoginInterceptPaths = { 
												Constant.APP_STATIC+Constant.REG,
												Constant.APP_INDEX,
												Constant.APP_USER,
												Constant.APP_LOGIN
											};

	public void addInterceptors(InterceptorRegistry registry) {
		// 登录拦截器
		registry.addInterceptor(loginInterceptor).addPathPatterns(Constant.REG).excludePathPatterns(notLoginInterceptPaths);
	}

	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/***
	 * addResourceLocations指的是文件放置的目录，addResoureHandler指的是对外暴露的访问路径
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//排除静态资源拦截
		registry.addResourceHandler(Constant.APP_STATIC+Constant.REG)
				.addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+Constant.STATIC+Constant.SLASH);
	}
}
