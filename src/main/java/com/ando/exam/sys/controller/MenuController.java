package com.ando.exam.sys.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ando.exam.base.BaseController;
import com.ando.exam.sys.config.Constant;
import com.ando.exam.sys.entity.Menu;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.service.MenuService;

@RestController
@RequestMapping(Constant.APP_SYS_MENU)
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/assignPrivileges")
	public Object assignPrivileges(String menuIds,String roleIds) {
		try {
			menuService.assignPrivileges(menuIds,roleIds);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@RequestMapping("/revokePrivileges")
	public Object revokePrivileges(String menuIds,String roleIds) {
		try {
			menuService.revokePrivileges(menuIds,roleIds);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@RequestMapping("/add")
	public Object add(Menu menu) {
		try {
			menuService.save(menu);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	@RequestMapping("/edit")
	public Object edit(Menu menu) {
		try {
			menuService.save(menu);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@RequestMapping("/delete")
	public Object delete(Long menuId) {
		try {
			menuService.delete(menuId);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@GetMapping(value = "/getMenuHeadBar")
	public Object getMenuHeadBar(HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			this.getMap().put("menuHeadBar", menuService.getMenuHeadBarList(user));
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@GetMapping(value = "/getNavigationTree")
	public Object getNavigationTree(String module,HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			this.getMap().put("navTreeList", menuService.getNavigationList(user,module));
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@GetMapping(value = "/getMenus")
	public Object getMenus(Long pid) {
		return menuService.getMenus(pid);
	}
}