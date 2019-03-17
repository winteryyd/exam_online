package com.ando.exam.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ando.exam.sys.entity.Menu;
import com.ando.exam.sys.entity.RoleMenu;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.repository.MenuRepository;
import com.ando.exam.sys.repository.RoleMenuRepository;
import com.ando.exam.sys.service.MenuService;
import com.ando.exam.sys.vo.MenuHeadBar;
import com.ando.exam.sys.vo.MenuNavigation;

/**
 * @Descrip: TODO
 * @ClassName:  MenuServiceImpl 
 * @author: ando
 * @date:   2019年2月24日 下午4:08:11    
 */
@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private RoleMenuRepository roleMenuRepository;

	
	public List<MenuHeadBar> getMenuHeadBarList(User user) {
		List<Menu> list = menuRepository.queryMenuHeadBarList(user.getEmpid());
		List<MenuHeadBar> menuHeadBarlist = new ArrayList<MenuHeadBar>();
		for (Menu m : list) {
			menuHeadBarlist.add(new MenuHeadBar(m));
		}
		return menuHeadBarlist;
	}

	
	public List<MenuNavigation> getNavigationList(User user, String module) {
		List<Menu> list = menuRepository.queryMenuByUserAndModule(module, user.getEmpid());
		List<MenuNavigation> menuNavigationlist = new ArrayList<MenuNavigation>();
		for (Menu m : list) {
			menuNavigationlist.add(new MenuNavigation(m));
		}
		return menuNavigationlist;
	}

	
	public List<Menu> getMenus(Long pid) {
		return menuRepository.findByPidOrderByRank(pid);
	}

	
	public void save(Menu menu) {
		menuRepository.save(menu);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long menuId) {
		Set<Long> set = this.getMenuIdAndAllChildMenuId(menuId);
		menuRepository.deleteMenuIds(set);
		roleMenuRepository.deleteMenuIds(set);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public void assignPrivileges(String menuIds, String roleIds) {
		String[] roleIdsArr = roleIds.split(",");
		String[] menuIdsArr = menuIds.split(",");
		List<RoleMenu> list = new ArrayList<RoleMenu>();
		for(String roleId:roleIdsArr){
			for(String menuId:menuIdsArr){
				list.add(new RoleMenu(Long.valueOf(roleId),Long.valueOf(menuId)));
			}
		}
		if(list.size()>0) {
			roleMenuRepository.saveAll(list);
		}
	}

	
	@Transactional(rollbackFor = Exception.class)
	public void revokePrivileges(String menuIds, String roleIds) {
		String[] roleIdsArr = roleIds.split(",");
		String[] menuIdsArr = menuIds.split(",");
		for(int i=0;i<menuIdsArr.length;i++){
			if(i==0) {
				Set<Long> set = this.getMenuIdAndAllChildMenuId(Long.valueOf(menuIdsArr[0]));
				for(String roleId:roleIdsArr){
					roleMenuRepository.deleteRoleMenus(Long.valueOf(roleId), set);
				}
			}else {
				Set<Long> set = this.getChildMenuId(Long.valueOf(menuIdsArr[i]));
				for(String roleId:roleIdsArr){
					List<RoleMenu> list = roleMenuRepository.queryRoleMenus(Long.valueOf(roleId), set);
					if(list.size()==0) {
						roleMenuRepository.delete(new RoleMenu(Long.valueOf(roleId),Long.valueOf(menuIdsArr[i])));
					}
				}
			}
		}
	}
	/**
	 * @Descrip: 获取菜单ID和所有下层层级的子菜单ID 
	 * @Title: getMenuIdAndAllChildMenuId   
	 * @param menuId
	 * @return      
	 * @return: Set<Long>      
	 * @throws
	 */
	public Set<Long> getMenuIdAndAllChildMenuId(Long menuId){
		Set<Long> set = new HashSet<Long>();
		set.add(menuId);
		List<Menu> list = menuRepository.findByPidOrderByRank(menuId);
		while(list.size()>0) {
			List<Long> pids = new ArrayList<Long>();
			for(Menu m:list) {
				set.add(m.getMenuId());
				pids.add(m.getMenuId());
			}
			list = menuRepository.queryMenuIdsByPids(pids);
		}
		return set;
	}
	/**
	 * @Descrip: 获取菜单的子菜单ID 
	 * @Title: getChildMenuId   
	 * @param menuId
	 * @return      
	 * @return: Set<Long>      
	 * @throws
	 */
	public Set<Long> getChildMenuId(Long menuId){
		Set<Long> set = new HashSet<Long>();
		List<Menu> list = menuRepository.findByPidOrderByRank(menuId);
		for(Menu m:list) {
			set.add(m.getMenuId());
		}
		return set;
	}
}
