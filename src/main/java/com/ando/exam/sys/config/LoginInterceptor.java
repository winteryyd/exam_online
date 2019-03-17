package com.ando.exam.sys.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class LoginInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request.getSession().getAttribute("user") == null) {
			response.setHeader("Content-Type", "text/html;charset=UTF-8");
			response.setStatus(501);
			response.getWriter().append("用户未登录！");
			return false;
		}
		return true;
	}
}
