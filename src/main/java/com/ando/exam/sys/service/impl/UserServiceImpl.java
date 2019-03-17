package com.ando.exam.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.util.StringUtils;

import com.ando.exam.base.BasePage;
import com.ando.exam.sys.entity.Role;
import com.ando.exam.sys.entity.RoleUser;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.repository.RoleUserRepository;
import com.ando.exam.sys.repository.UserRepository;
import com.ando.exam.sys.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleUserRepository roleUserRepository;
	
	
	public Page<User> getPage(final BasePage<User> page) {
		Pageable pageable = null;
		if(null!=page.getDirection()&&null!=page.getProperty()) {
			Sort sort = Sort.by(Sort.Direction.fromString(page.getDirection()), page.getProperty());
			pageable = PageRequest.of(page.getPage(), page.getLimit(), sort);
		}else {
			pageable = PageRequest.of(page.getPage(), page.getLimit());
		}
		Specification<User> specification = null;
		if (null != page.getQuery() && !"".equals(page.getQuery().trim())) {
			specification = new Specification<User>() {
				private static final long serialVersionUID = -235756344723313266L;

				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<Predicate>();
					String[] props = {"empid","username","department","title"};
					for(String prop:props) {
						list.add(criteriaBuilder.like(root.get(prop).as(String.class),"%"+page.getQuery()+"%"));
					}
					if(list.size()>0) {
						Predicate[] pre = new Predicate[list.size()];
						Predicate predicate= criteriaBuilder.or(list.toArray(pre));
						query.where(predicate);
					}
					return query.getRestriction();
				}
			};
		}
		return userRepository.findAll(specification, pageable);
	}

	
	public void save(User user) {
		userRepository.save(user);
	}

	
	public void delete(String empid) {
		userRepository.deleteById(empid);
	}

	
	public Optional<User> loginUser(String empid) {
		return userRepository.findById(empid);
	}

	
	public void resetPassword(String empid, String password) {
		Optional<User> optional = userRepository.findById(empid);
		if(optional.isPresent()) {
			User user = optional.get();
			user.setPassword(password);
			userRepository.save(user);
		}
	}

	/**
	 * @Descrip: 角色管理->用户管理，获取角色分配的用户关系    
	 * @Title: getRoleUserPage   
	 * @param role
	 * @param basePage
	 * @return        
	 * @throws 
	 * @see com.yunji.plat.sys.service.UserService#getRoleUserPage(com.yunji.plat.sys.entity.Role, com.yunji.plat.base.BasePage)
	 */
	
	public Page<User> getRoleUserPage(Role role, BasePage<User> page) {
		List<RoleUser> roleUserList = this.getRoleUserByRole(role);
		Page<User> userPage = this.getPage(page);
		//获取角色主键关系
		Map<String,User> map = this.getKeyMap(userPage);
		//处理权限逻辑
		for(RoleUser ru:roleUserList) {
			String key = ru.getEmpid();
			if(map.containsKey(key)) {
				map.get(key).setAuth(true);//设置拥有权限
			}
		}
		return userPage;
	}

	
	/**
	 * @Descrip: 获取角色数据主键关系 
	 * @Title: getKeyMap   
	 * @param rolePage
	 * @return      
	 * @return: Map<Long,Role>      
	 * @throws
	 */
	public Map<String,User> getKeyMap(Page<User> userPage){
		Map<String,User> map = new HashMap<String, User>();
		for(User user:userPage.getContent()) {
			map.put(user.getEmpid(), user);
		}
		return map;
	}
	
	/**
	 * @Descrip: 根据角色查询所有角色用户关系  
	 * @Title: getRoleUserByRole   
	 * @param role
	 * @return      
	 * @return: List<RoleUser>      
	 * @throws
	 */
	private List<RoleUser> getRoleUserByRole(final Role role) {
		Specification<RoleUser> specification = null;
		if (role != null) {
			specification = new Specification<RoleUser>() {
				private static final long serialVersionUID = -235756344723313266L;
				public Predicate toPredicate(Root<RoleUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
						Predicate p = criteriaBuilder.equal(root.get("roleId").as(Long.class),role.getRoleId());
					return query.where(p).getRestriction();
				}
			};
			return roleUserRepository.findAll(specification);
		}
		return Collections.emptyList();
	}

	/**
	 * @Descrip: 角色管理->用户管理，保存角色分配的用户关系     
	 * @Title: saveRoleUser   
	 * @param roleId
	 * @param empids        
	 * @throws 
	 * @see com.yunji.plat.sys.service.UserService#saveRoleUser(java.lang.Long, java.lang.String)
	 */
	
	public void saveRoleUser(Long roleId, String empids) {
		if(roleId>0 && !StringUtils.isEmpty(empids)) {
			List<RoleUser> list = this.newRoleUser(roleId, empids);
			if(list.size()>0) {
				roleUserRepository.saveAll(list);
			}
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
	
	public void deleteUserRoles(Long roleId, String empids) {
		if(roleId>0 && !StringUtils.isEmpty(empids)) {
			List<RoleUser> list = this.newRoleUser(roleId, empids);
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
	private List<RoleUser> newRoleUser(Long roleId, String empids){
		List<RoleUser> list = new ArrayList<RoleUser>();
		if(roleId>0 && !StringUtils.isEmpty(empids)) {
			String[] empidsArr = empids.trim().split(",");
			for(String empid:empidsArr){
				if(!StringUtils.isEmpty(empid)) {
					list.add(new RoleUser(empid,roleId));
				}
			}
		}
		return list;
	}
}
