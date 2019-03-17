package com.ando.exam.sys.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Constant {

	public final static String SLASH = "/";// 斜杠
	public final static String REG = "/**";// 通识
	public final static String STATIC = "/static";// 静态资源文件夹
	public final static String APP_SYS = "/exam";// 平台系统名称
	public final static String APP_NAME = "在线考试系统";// 平台名称
	public final static String APP_STATIC = APP_SYS + STATIC;// 平台静态资源路径
	public final static String APP_INDEX = APP_SYS + "/index";// 平台首页
	public final static String APP_LOGIN = APP_SYS + "/login";// 平台登录
	public final static String APP_LOGOUT = APP_SYS + "/logout";// 平台退出
	public final static String APP_RESET = APP_SYS + "/reset";// 重置密码
	public final static String APP_USER = APP_SYS + "/loginUser";// 平台登录用户

	public final static String APP_SYS_USER = APP_SYS + "/user";// 平台用户
	public final static String APP_SYS_ROLE = APP_SYS + "/role";// 平台角色
	public final static String APP_SYS_MENU = APP_SYS + "/menu";// 平台菜单
	public final static String APP_SYS_DICT = APP_SYS + "/dict";// 平台词典
	
	public static Map<String, String> mo = new HashMap<String, String>();
	static {
		mo.put("static", Constant.STATIC);
		mo.put("app_name", Constant.APP_NAME);
		mo.put("app_sys", Constant.APP_SYS);
		mo.put("app_static", Constant.APP_STATIC);
		mo.put("app_index", Constant.APP_INDEX);
		mo.put("app_login", Constant.APP_LOGIN);
		mo.put("app_logout", Constant.APP_LOGOUT);
		mo.put("app_reset", Constant.APP_RESET);
		mo.put("app_user", Constant.APP_USER);
		mo.put("sys_user", Constant.APP_SYS_USER);
		mo.put("sys_role", Constant.APP_SYS_ROLE);
		mo.put("sys_menu", Constant.APP_SYS_MENU);
		mo.put("sys_dict", Constant.APP_SYS_DICT);
	}
	
	public static Set<String> set =new HashSet<String>();
	static {
		set.add("aria");
		set.add("crisp");
		set.add("graphite");
		set.add("gray");
		set.add("neptune");
		set.add("triton");
	}
	public final static boolean validate(String theme) {
		return set.contains(theme);
	}
}
