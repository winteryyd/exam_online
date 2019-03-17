package com.ando.exam.sys.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ando.exam.sys.config.Constant;
import com.ando.exam.sys.entity.Dict;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.service.DictService;
import com.ando.exam.sys.service.UserService;
import com.ando.exam.utils.MD5Utils;

@RestController
public class IndexController {
	@Autowired
	private UserService userService;
	@Autowired
	private DictService dictService;

	@RequestMapping(Constant.APP_INDEX)
	@ResponseBody
	public ModelAndView index(String theme) {
		ModelAndView mo = new ModelAndView("index");
		if (Constant.validate(theme)) {
			mo.addObject("theme", theme);
		} else {
			mo.addObject("theme", "triton");
		}
		Dict dict = dictService.getDict("background");
		if (dict == null) {
			mo.addObject("background", Constant.STATIC + "/resources/images/lock-screen-background.jpg");
		} else {
			mo.addObject("background", dict.getDictLabel());
		}
		return mo;
	}

	@RequestMapping(Constant.APP_USER)
	public Object getUserInfo(HttpSession session) {
		return session.getAttribute("user");
	}

	@PostMapping(Constant.APP_LOGIN)
	public String loginVerify(String empid, String password, HttpSession session) throws Exception {
		Optional<User> loginUser = userService.loginUser(empid);
		if (!loginUser.isPresent()) {
			return "账号不存在！";
		}
		if (loginUser.get().getActive() == 0) {
			return "账号禁止登录!";
		}
		password = MD5Utils.md5(password.trim());
		if (!loginUser.get().getPassword().equals(password)) {
			return "密码不正确！";
		}
		session.setAttribute("user", loginUser.get());
		return "success";
	}

	@PostMapping(Constant.APP_LOGOUT)
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "success";
	}

	@PostMapping(Constant.APP_RESET)
	public String resetPassword(String password, HttpSession session) throws Exception {
		User loginUser = (User) session.getAttribute("user");
		password = MD5Utils.md5(password.trim());
		userService.resetPassword(loginUser.getEmpid(), password);
		session.removeAttribute("user");
		return "success";
	}
}