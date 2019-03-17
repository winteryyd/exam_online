package com.ando.exam.sys.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.ando.exam.base.BasePage;
import com.ando.exam.sys.entity.Role;
import com.ando.exam.sys.entity.User;

public interface UserService {
	Page<User> getPage(BasePage<User> page);
	
	void save(User user);
	
	void delete(String empid);
	
	Optional<User> loginUser(String empid);

	void resetPassword(String empid, String password);

	/**
	 * @Descrip: 角色管理->用户管理，获取角色分配的用户关系    
	 * @Title: getRoleUserPage   
	 * @param role
	 * @param basePage
	 * @return      
	 * @return: Page<User>      
	 * @throws
	 */
	Page<User> getRoleUserPage(Role role, BasePage<User> basePage);
	/**
	 * @Descrip: 角色管理->用户管理，保存角色分配的用户关系    
	 * @Title: saveRoleUser   
	 * @param roleId
	 * @param empids      
	 * @return: void      
	 * @throws
	 */
	void saveRoleUser(Long roleId, String empids);
	/**
	 * @Descrip: 角色管理->用户管理，删除角色分配的用户关系 
	 * @Title: deleteRoleUsers   
	 * @param empid
	 * @param roleIds
	 * @return      
	 * @return: Object      
	 * @throws
	 */
	void deleteUserRoles(Long roleId, String empids);
}
