package com.ando.exam.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ando.exam.base.BaseController;
import com.ando.exam.base.BasePage;
import com.ando.exam.sys.config.Constant;
import com.ando.exam.sys.entity.Role;
import com.ando.exam.sys.entity.RoleUser;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.service.UserService;

@RestController
@RequestMapping(Constant.APP_SYS_USER)
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/save")
	public Object save(User user) {
		try {
			userService.save(user);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@RequestMapping("/delete")
	public Object delete(String empids) {
		try {
			String[] empidsArr = empids.trim().split(",");
			for(String empid:empidsArr){
				if(!StringUtils.isEmpty(empid)) {
					userService.delete(empid);
				}
			}
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@GetMapping(value = "/getPage")
	public Object getPage(BasePage<User> basePage) {
		try {
			Page<User> userList = userService.getPage(basePage);
			this.getMap().put("users", userList.getContent());
			this.getMap().put("total", userList.getTotalElements());
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	
	/**
	 * @Descrip: 角色管理->用户管理，获取角色分配的用户关系   
	 * @Title: getRoleUserPage   
	 * @param basePage
	 * @param user
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@GetMapping(value = "/getRoleUserPage")
	public Object getRoleUserPage(Role role,BasePage<User> basePage) {
		try {
			Page<User> userList = userService.getRoleUserPage(role,basePage);
			this.getMap().put("users", userList.getContent());
			this.getMap().put("total", userList.getTotalElements());
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	/**
	 * @Descrip: 角色管理->用户管理,保存角色分配的用户关系  
	 * @Title: saveRoleUsers   
	 * @param empid
	 * @param roleIds
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@PostMapping(value = "/saveRoleUsers")
	public Object saveRoleUsers(Long roleId,String empids) {
		try {
			userService.saveRoleUser(roleId,empids);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	/**
	 * @Descrip: 角色管理->用户管理，删除角色分配的用户关系 
	 * @Title: deleteRoleUsers   
	 * @param empid
	 * @param roleIds
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@PostMapping(value = "/deleteRoleUsers")
	public Object deleteRoleUsers(Long roleId,String empids) {
		try {
			userService.deleteUserRoles(roleId,empids);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
}