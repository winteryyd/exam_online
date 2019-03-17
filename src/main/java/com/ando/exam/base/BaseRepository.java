package com.ando.exam.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Descrip: repository 基类，封装自定义查询方法
 * @ClassName:  BaseRepository 
 * @author: ando
 * @date:   2019�?2�?2�? 下午9:53:01  
 * @param <T>
 * @param <ID>  
 */
@NoRepositoryBean // 该注解表�? spring 容器不会创建该对�?
public interface BaseRepository<T, ID extends Serializable> extends  JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	/**
	 * @Descrip: sql查询 
	 * @Title: findAllByParams   
	 * @param: sql
	 * @param: args
	 * @return: List<Map<String,Object>>      
	 * @throws
	 */
	List<Map<String, Object>> findAllByParams(String sql, Object... args);

	/**
	 * @Descrip: sql查询总数 
	 * @Title: findCount   
	 * @param: sql
	 * @param: args
	 * @return: Long      
	 * @throws
	 */
	Long findCount(String sql, Object... args);
	/**
	 * @Descrip: sql分页查询 
	 * @Title: findPageByParams   
	 * @param: sql
	 * @param: pageable
	 * @param: args
	 * @return: Page<Map<String,Object>>      
	 * @throws
	 */
	Page<Map<String, Object>> findPageByParams(String sql, Pageable pageable, Object... args);

	
}
