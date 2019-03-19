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
import com.ando.exam.sys.entity.Menu;
import com.ando.exam.sys.entity.Role;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.service.RoleService;

@RestController
@RequestMapping(Constant.APP_SYS_ROLE)
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/save")
	public Object save(Role role) {
		try {
			roleService.save(role);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@RequestMapping("/delete")
	public Object delete(String roleIds) {
		try {
			String[] roleIdsArr = roleIds.trim().split(",");
			for(String roleId:roleIdsArr){
				if(!StringUtils.isEmpty(roleId)) {
					roleService.delete(Long.valueOf(roleId));
				}
			}
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	@GetMapping(value = "/getPage")
	public Object getPage(BasePage<Role> basePage) {
		try {
			Page<Role> roleList = roleService.getPage(basePage);
			this.getMap().put("roles", roleList.getContent());
			this.getMap().put("total", roleList.getTotalElements());
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	/**
	 * @Descrip: 角色管理->菜单管理，获取角色的菜单树 
	 * @Title: getRoleMenuTree   
	 * @param roleId
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@GetMapping(value = "/getRoleMenuTree")
	public Object getRoleMenuTree(Long roleId) {
		try {
			this.getMap().put("menuTree", roleService.getRoleMenuTree(roleId));
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	/**
	 * @Descrip: 角色管理->菜单管理，保存角色菜单关系  
	 * @Title: saveRoleMenuPrivileges   
	 * @param roleId
	 * @param menuIds
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@PostMapping(value = "/saveRoleMenus")
	public Object saveRoleMenus(Long roleId,String menuIds) {
		try {
			roleService.saveRoleMenus(roleId,menuIds);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	
	/**
	 * @Descrip: 用户管理->权限管理，查询用户角色权限   
	 * @Title: getUserRolePage   
	 * @param basePage
	 * @param user
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@GetMapping(value = "/getUserRolePage")
	public Object getUserRolePage(User user,BasePage<Role> basePage) {
		try {
			Page<Role> roleList = roleService.getUserRolePage(user,basePage);
			this.getMap().put("roles", roleList.getContent());
			this.getMap().put("total", roleList.getTotalElements());
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	/**
	 * @Descrip: 用户管理->权限管理，保存用户分配的角色权限  
	 * @Title: saveUserRoles   
	 * @param empid
	 * @param roleIds
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@PostMapping(value = "/saveUserRoles")
	public Object saveUserRoles(String empid,String roleIds) {
		try {
			roleService.saveUserRole(empid,roleIds);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	/**
	 * @Descrip: 用户管理->权限管理，删除用户分配的角色权限 
	 * @Title: deleteUserRoles   
	 * @param empid
	 * @param roleIds
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@PostMapping(value = "/deleteUserRoles")
	public Object deleteUserRoles(String empid,String roleIds) {
		try {
			roleService.deleteUserRoles(empid,roleIds);
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
	
	/**
	 * @Descrip: 菜单管理->角色管理，获取菜单的角色关系 
	 * @Title: getMenuRolePage   
	 * @param menuId
	 * @param page
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	@GetMapping(value = "/getMenuRolePage")
	public Object getMenuRolePage(Menu menu,BasePage<Role> page) {
		try {
			Page<Role> rolePage = roleService.getMenuRolePage(menu,page);
			this.getMap().put("roles", rolePage.getContent());
			this.getMap().put("total", rolePage.getTotalElements());
			return this.returnSuccess();
		} catch (Exception e) {
			return this.returnError(e);
		}
	}
}