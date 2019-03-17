package com.ando.exam.sys.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ando.exam.base.BaseRepository;
import com.ando.exam.sys.entity.RoleMenu;
import com.ando.exam.sys.entity.RoleMenuKey;


public interface RoleMenuRepository extends BaseRepository<RoleMenu, RoleMenuKey> {
	@Modifying
	@Query("DELETE FROM RoleMenu rm where menuId in (?1)")
	void deleteMenuIds(Set<Long> set);
	
	@Modifying
	@Query("DELETE FROM RoleMenu rm where roleId = ?1")
	void deleteRoleId(Long roleId);
	
	@Modifying
	@Query("DELETE FROM RoleMenu rm where roleId = ?1 and menuId in (?2)")
	void deleteRoleMenus(Long roleId,Set<Long> set);
	
	@Query("SELECT rm FROM RoleMenu rm where roleId = ?1 and menuId in (?2)")
	List<RoleMenu> queryRoleMenus(Long roleId,Set<Long> set);
}
