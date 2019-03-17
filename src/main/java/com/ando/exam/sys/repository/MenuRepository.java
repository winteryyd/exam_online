package com.ando.exam.sys.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ando.exam.base.BaseRepository;
import com.ando.exam.sys.entity.Menu;

public interface MenuRepository extends BaseRepository<Menu, Long> {
	
	@Query("select distinct m from Menu m "
			+ "inner join RoleMenu rm on m.menuId = rm.menuId "
			+ "inner join Role r on r.roleId = rm.roleId "
			+ "inner join RoleUser ur on ur.roleId = r.roleId "
			+ "inner join User u on u.empid = ur.empid "
			+ "where m.pid = 1 and u.empid = ?1 order by m.rank")
	List<Menu> queryMenuHeadBarList(String empid);
	
	@Query("select distinct m from Menu m "
			+ "inner join RoleMenu rm on m.menuId = rm.menuId "
			+ "inner join Role r on r.roleId = rm.roleId "
			+ "inner join RoleUser ur on ur.roleId = r.roleId "
			+ "inner join User u on u.empid = ur.empid "
			+ "where m.menuHref = ?1 and u.empid = ?2 order by m.rank")
	List<Menu> queryMenuByUserAndModule(String module,String empid);
	
	@Query("select distinct m from Menu m where pid in (?1)")
	List<Menu> queryMenuIdsByPids(List<Long> pids);
	
	List<Menu> findByPidOrderByRank(Long pid);
	@Modifying
	@Query("DELETE FROM Menu m where menuId in (?1)")
	void deleteMenuIds(Set<Long> pids);
}
