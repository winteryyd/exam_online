package com.ando.exam.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ando.exam.sys.config.Constant;

@Controller
public class IframeController{

	@RequestMapping(Constant.APP_SYS+"/headPhoto")
	public String hello() {
		return "headPhoto";
	}
}