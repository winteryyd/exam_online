package com.ando.exam.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ando.exam.base.BasePage;
import com.ando.exam.sys.entity.Menu;
import com.ando.exam.sys.entity.MenuNode;
import com.ando.exam.sys.entity.Role;
import com.ando.exam.sys.entity.RoleMenu;
import com.ando.exam.sys.entity.RoleUser;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.repository.MenuRepository;
import com.ando.exam.sys.repository.RoleMenuRepository;
import com.ando.exam.sys.repository.RoleRepository;
import com.ando.exam.sys.repository.RoleUserRepository;
import com.ando.exam.sys.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private RoleMenuRepository roleMenuRepository;
	@Autowired
	private RoleUserRepository roleUserRepository;
	
	
	public Page<Role> getPage(final BasePage<Role> page) {
		Pageable pageable = null;
		if(null!=page.getDirection()&&null!=page.getProperty()) {
			Sort sort = Sort.by(Sort.Direction.fromString(page.getDirection()), page.getProperty());
			pageable = PageRequest.of(page.getPage(), page.getLimit(), sort);
		}else {
			pageable = PageRequest.of(page.getPage(), page.getLimit());
		}
		Specification<Role> specification = null;
		if (null != page.getQuery() && !"".equals(page.getQuery().trim())) {
			specification = new Specification<Role>() {
				private static final long serialVersionUID = -235756344723313266L;
				public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<Predicate>();
					list.add(criteriaBuilder.like(root.get("enRoleName").as(String.class),"%"+page.getQuery()+"%"));
					list.add(criteriaBuilder.like(root.get("chRoleName").as(String.class),"%"+page.getQuery()+"%"));
					
					Predicate[] pre = new Predicate[list.size()];
					Predicate predicate= criteriaBuilder.or(list.toArray(pre));
					return query.where(predicate).getRestriction();
				}
			};
		}
		return roleRepository.findAll(specification, pageable);
	}
	
	public void save(Role role) {
		roleRepository.save(role);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long roleId) {
		roleRepository.deleteById(roleId);
		roleMenuRepository.deleteRoleId(roleId);
	}
	
	/**
	 * @Descrip: 角色管理->菜单管理，获取角色菜关系   
	 * @Title: getRoleMenuList   
	 * @param roleId
	 * @return        
	 * @throws 
	 * @see com.yunji.plat.sys.service.RoleService#getRoleMenuList(java.lang.Long)
	 */
	
	public List<RoleMenu> getRoleMenuList(final Long roleId) {
		Specification<RoleMenu> specification = null;
		if (roleId != null) {
			specification = new Specification<RoleMenu>() {
				private static final long serialVersionUID = -235756344723313266L;
				public Predicate toPredicate(Root<RoleMenu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						Predicate p = criteriaBuilder.equal(root.get("roleId").as(Long.class),roleId);
						query.where(p);
					return query.getRestriction();
				}
			};
		}
		return roleMenuRepository.findAll(specification);
	}
	
	/**
	 * @Descrip: 角色管理->菜单管理，保存角色菜单关系   
	 * @Title: saveRoleMenus   
	 * @param roleId
	 * @param menuIds        
	 * @throws 
	 * @see com.yunji.plat.sys.service.RoleService#saveRoleMenus(java.lang.Long, java.lang.String)
	 */
	
	@Transactional(rollbackFor = Exception.class)
	public void saveRoleMenus(Long roleId, String menuIds) {
		roleMenuRepository.deleteRoleId(roleId);
		if(menuIds.trim().length()>0) {
			String[] menuIdsArr = menuIds.trim().split(",");
			List<RoleMenu> list = new ArrayList<RoleMenu>();
			for(String menuId:menuIdsArr){
				list.add(new RoleMenu(roleId,Long.valueOf(menuId)));
			}
			if(list.size()>0) {
				roleMenuRepository.saveAll(list);
			}
		}
	}
	
	/**
	 * @Descrip: 根据用户查询所有角色用户关系 
	 * @Title: getRoleUserByUser   
	 * @param user
	 * @return      
	 * @return: List<RoleUser>      
	 * @throws
	 */
	public List<RoleUser> getRoleUserByUser(final User user){
		Specification<RoleUser> specification = null;
		if (user != null) {
			specification = new Specification<RoleUser>() {
				private static final long serialVersionUID = -235756344723313266L;
				public Predicate toPredicate(Root<RoleUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						Predicate p = criteriaBuilder.equal(root.get("empid").as(String.class),user.getEmpid());
						query.where(p);
					return query.getRestriction();
				}
			};
			return roleUserRepository.findAll(specification);
		}
		return Collections.emptyList();
	}
	
	/**
	 * @Descrip: 根据菜单查询所有角色菜单关系 
	 * @Title: getRoleMenuByMenu   
	 * @param menu
	 * @return      
	 * @return: List<RoleMenu>      
	 * @throws
	 */
	public List<RoleMenu> getRoleMenuByMenu(final Menu menu){
		Specification<RoleMenu> specification = null;
		if (menu != null) {
			specification = new Specification<RoleMenu>() {
				private static final long serialVersionUID = -235756344723313266L;
				public Predicate toPredicate(Root<RoleMenu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						Predicate p = criteriaBuilder.equal(root.get("menuId").as(Long.class),menu.getMenuId());
					return query.where(p).getRestriction();
				}
			};
			return roleMenuRepository.findAll(specification);
		}
		return Collections.emptyList();
	}
	
	/**
	 * @Descrip: 获取角色数据主键关系 
	 * @Title: getKeyMap   
	 * @param rolePage
	 * @return      
	 * @return: Map<Long,Role>      
	 * @throws
	 */
	public Map<Long,Role> getKeyMap(Page<Role> rolePage){
		Map<Long,Role> map = new HashMap<Long, Role>();
		for(Role role:rolePage.getContent()) {
			map.put(role.getRoleId(), role);
		}
		return map;
	}
	
	
	
	/**
	 * @Descrip: 用户管理->权限管理，查询用户角色权限   
	 * @Title: getUserRolePage   
	 * @param page
	 * @param user
	 * @return        
	 * @throws 
	 * @see com.yunji.plat.sys.service.RoleService#getUserRolePage(com.yunji.plat.base.BasePage, com.yunji.plat.sys.entity.User)
	 */
	
	public Page<Role> getUserRolePage(User user,BasePage<Role> page){
		List<RoleUser> roleUserList = this.getRoleUserByUser(user);
		Page<Role> rolePage = this.getPage(page);
		//获取角色主键关系
		Map<Long,Role> map = this.getKeyMap(rolePage);
		//处理权限逻辑
		for(RoleUser ru:roleUserList) {
			Long key = ru.getRoleId();
			if(map.containsKey(key)) {
				map.get(key).setAuth(true);//设置拥有权限
			}
		}
		return rolePage;
	}
	
	/**
	 * @Descrip: 保存用户分配的角色信息  
	 * @Title: saveRoleUser   
	 * @param empid
	 * @param roleIds        
	 * @throws 
	 * @see com.yunji.plat.sys.service.RoleService#saveRoleUser(java.lang.String, java.lang.String)
	 */
	
	public void saveUserRole(String empid, String roleIds) {
		if(!StringUtils.isEmpty(empid) && !StringUtils.isEmpty(roleIds)) {
			List<RoleUser> list = this.newRoleUser(empid, roleIds);
			if(list.size()>0) {
				roleUserRepository.saveAll(list);
			}
		}
	}
	/**
	 * @Descrip: 删除用户角色关系 
	 * @Title: deleteUserRoles   
	 * @param empid
	 * @param roleIds        
	 * @throws 
	 * @see com.yunji.plat.sys.service.RoleService#deleteUserRoles(java.lang.String, java.lang.String)
	 */
	
	public void deleteUserRoles(String empid, String roleIds) {
		if(!StringUtils.isEmpty(empid) && !StringUtils.isEmpty(roleIds)) {
			List<RoleUser> list = this.newRoleUser(empid, roleIds);
			if(list.size()>0) {
				roleUserRepository.deleteAll(list);
			}
		}
	}
	
	/**
	 * @Descrip: 把字符串封装成对象 
	 * @Title: newRoleUser   
	 * @param empid
	 * @param roleIds
	 * @return      
	 * @return: List<RoleUser>      
	 * @throws
	 */
	private List<RoleUser> newRoleUser(String empid, String roleIds){
		List<RoleUser> list = new ArrayList<RoleUser>();
		if(!StringUtils.isEmpty(empid) && !StringUtils.isEmpty(roleIds)) {
			String[] roleIdsArr = roleIds.trim().split(",");
			for(String roleId:roleIdsArr){
				if(!StringUtils.isEmpty(roleId)) {
					list.add(new RoleUser(empid,Long.valueOf(roleId)));
				}
			}
		}
		return list;
	}
	
	/**
	 * @Descrip: 角色管理->菜单管理，根据角色获取菜单树 
	 * @Title: getRoleMenuTree   
	 * @param roleId
	 * @return        
	 * @throws 
	 * @see com.yunji.plat.sys.service.RoleService#getRoleMenuTree(java.lang.Long)
	 */
	
	public MenuNode getRoleMenuTree(Long roleId) {
		List<RoleMenu> roleMenuList = this.getRoleMenuList(roleId);
		List<Menu> menuList = menuRepository.findAll();
		Map<Long,MenuNode> map = new HashMap<Long, MenuNode>();
		for(Menu m:menuList) {
			map.put(m.getMenuId(), new MenuNode(m));
		}
		for(RoleMenu rm:roleMenuList) {
			Long key = rm.getMenuId();
			if(map.containsKey(key)) {
				map.get(key).setChecked(true);
			}
		}
		for(MenuNode mn:map.values()) {
			Long pid = mn.getPid();
			if(map.containsKey(pid)) {
				map.get(pid).addChildren(mn);
			}
		}
		return map.get(1L);
	}
	
	/**
	 * @Descrip: 菜单管理->角色管理，获取菜单的角色关系  
	 * @Title: getMenuRolePage   
	 * @param menu
	 * @param page
	 * @return        
	 * @throws 
	 * @see com.yunji.plat.sys.service.RoleService#getMenuRolePage(com.yunji.plat.sys.entity.Menu, com.yunji.plat.base.BasePage)
	 */
	
	public Page<Role> getMenuRolePage(Menu menu, BasePage<Role> page) {
		List<RoleMenu> roleMenuList = this.getRoleMenuByMenu(menu);
		Page<Role> rolePage = this.getPage(page);
		//获取角色主键关系
		Map<Long,Role> map = this.getKeyMap(rolePage);
		//处理权限逻辑
		for(RoleMenu rm:roleMenuList) {
			Long key = rm.getRoleId();
			if(map.containsKey(key)) {
				map.get(key).setAuth(true);//设置拥有权限
			}
		}
		return rolePage;
	}
}
