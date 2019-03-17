package com.ando.exam.sys.service;

import java.util.List;

import com.ando.exam.sys.entity.Menu;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.vo.MenuHeadBar;
import com.ando.exam.sys.vo.MenuNavigation;
/**
 * @Descrip: 菜单管理服务接口
 * @ClassName:  MenuService 
 * @author: ando
 * @date:   2019年2月2日 下午2:35:28    
 */
public interface MenuService {
	/**
	 * @Descrip: 获取头部菜单
	 * @Title: getMenuHeadBarList   
	 * @param: @param pid
	 * @param: @return      
	 * @return: List<MenuHeadBar>      
	 * @throws
	 */
	List<MenuHeadBar> getMenuHeadBarList(User user);
	/**
	 * @Descrip: 获取左侧菜单 
	 * @Title: getNavigationTreeList   
	 * @param user
	 * @param module
	 * @return      
	 * @return: List<Menu>      
	 * @throws
	 */
	List<MenuNavigation> getNavigationList(User user,String module);
	
	/**
	 * @Descrip: 根据父主键查询菜单 
	 * @Title: getMenus   
	 * @param pid
	 * @return      
	 * @return: List<Menu>      
	 * @throws
	 */
	List<Menu> getMenus(Long pid);
	/**
	 * @Descrip: 保存菜单 
	 * @Title: save   
	 * @param menu      
	 * @return: void      
	 * @throws
	 */
	void save(Menu menu);
	/**
	 * @Descrip: 删除菜单及子菜单和菜单权限 
	 * @Title: delete   
	 * @param menuId      
	 * @return: void      
	 * @throws
	 */
	void delete(Long menuId);
	/**
	 * @Descrip: 分配菜单权限给角色 
	 * @Title: assignPrivileges   
	 * @param menuIds
	 * @param roleIds      
	 * @return: void      
	 * @throws
	 */
	void assignPrivileges(String menuIds, String roleIds);
	/**
	 * @Descrip: 收回菜单权限 
	 * @Title: revokePrivileges   
	 * @param menuIds
	 * @param roleIds      
	 * @return: void      
	 * @throws
	 */
	void revokePrivileges(String menuIds, String roleIds);
}
