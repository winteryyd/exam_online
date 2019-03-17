package com.ando.exam.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ando.exam.base.BasePage;
import com.ando.exam.sys.entity.Menu;
import com.ando.exam.sys.entity.MenuNode;
import com.ando.exam.sys.entity.Role;
import com.ando.exam.sys.entity.RoleMenu;
import com.ando.exam.sys.entity.User;

public interface RoleService {
	Page<Role> getPage(BasePage<Role> page);
	
	void save(Role role);

	void delete(Long roleId);
	/**
	 * @Descrip: 角色管理->菜单管理，获取角色菜关系  
	 * @Title: getRoleMenuList   
	 * @param roleId
	 * @return      
	 * @return: List<RoleMenu>      
	 * @throws
	 */
	List<RoleMenu> getRoleMenuList(Long roleId);

	/**
	 * @Descrip: 角色管理->菜单管理，保存角色菜单关系   
	 * @Title: saveRoleMenus   
	 * @param roleId
	 * @param menuIds      
	 * @return: void      
	 * @throws
	 */
	void saveRoleMenus(Long roleId, String menuIds);
	/**
	 * @Descrip: 用户管理->权限管理，查询用户角色权限    
	 * @Title: getUserRolePage   
	 * @param page
	 * @param user
	 * @return      
	 * @return: Page<Role>      
	 * @throws
	 */
	Page<Role> getUserRolePage(User user,BasePage<Role> page);
	/**
	 * @Descrip: 用户管理->权限管理，保存用户分配的角色信息 
	 * @Title: saveRoleUser   
	 * @param empid
	 * @param roleIds      
	 * @return: void      
	 * @throws
	 */
	void saveUserRole(String empid, String roleIds);
	/**
	 * @Descrip:用户管理->权限管理， 删除用户角色关系 
	 * @Title: deleteUserRoles   
	 * @param empid
	 * @param roleIds      
	 * @return: void      
	 * @throws
	 */
	void deleteUserRoles(String empid, String roleIds);

 
	/**
	 * @Descrip: 角色管理->菜单管理，根据角色获取菜单树
	 * @Title: getRoleMenuTree   
	 * @param roleId
	 * @return      
	 * @return: MenuNode      
	 * @throws
	 */
	MenuNode getRoleMenuTree(Long roleId);

	/**
	 * @Descrip: 菜单管理->角色管理，获取菜单的角色关系  
	 * @Title: getMenuRolePage   
	 * @param menu
	 * @param page
	 * @return      
	 * @return: Page<Role>      
	 * @throws
	 */
	Page<Role> getMenuRolePage(Menu menu, BasePage<Role> page);
}
