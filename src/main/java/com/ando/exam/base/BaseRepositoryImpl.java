package com.ando.exam.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Descrip: 自定义查询实现基�?
 * @ClassName:  BaseRepositoryImpl 
 * @author: ando
 * @date:   2019�?2�?2�? 下午9:55:27  
 * @param <T>
 * @param <ID>  
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    
    /**
     * 实体管理类，对持久化实体做增删改查，自动义sq操作模板�?�?要的核心�?
     */
    public final EntityManager entityManager;
 
    /**
     * @Descrip: 构�?�方�?    
     * @Title:  BaseRepositoryImpl  
     * @param entityInformation
     * @param entityManager  
     * @throws
     */
    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }
 
    /**
     * @Descrip: 分页查询 
     * @Title: findPageByParams   
     * @param sql
     * @param pageable
     * @param args
     * @return        
     * @throws 
     * @see com.yunji.plat.base.BaseRepository#findPageByParams(java.lang.String, org.springframework.data.domain.Pageable, java.lang.Object[])
     */
    @SuppressWarnings({ "unchecked" })
	@Transactional(rollbackFor = Exception.class)
    public Page<Map<String,Object>> findPageByParams(String sql, Pageable pageable, Object... args) {
        Session session = (Session) entityManager.getDelegate();
        NativeQuery<Map<String,Object>> query = session.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        this.setParameters(query, args);
        long totalCount = this.findCount(sql, args);
        query.setFirstResult(pageable.getPageSize() * (pageable.getPageNumber() - 1));
        query.setMaxResults(pageable.getPageSize());
        List<Map<String,Object>> pageCount = query.list();
        Page<Map<String,Object>> pages = new PageImpl<Map<String,Object>>(pageCount, pageable, totalCount);
        return pages;
    }

    /**
     * @Descrip: sql查询 
     * @Title: findAllByParams   
     * @param sql
     * @param args
     * @return        
     * @throws 
     * @see com.yunji.plat.base.BaseRepository#findAllByParams(java.lang.String, java.lang.Object[])
     */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> findAllByParams(String sql, Object... args) {
        Session session = (Session) entityManager.getDelegate();
        NativeQuery<Map<String,Object>> query = session.createNativeQuery(sql);
         //查询结果转map        
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        this.setParameters(query, args);
        return query.list();
	}

	
	/**
	 * @Descrip: 查询sql查询的�?�数，用于分�? 
	 * @Title: findCount   
	 * @param sql
	 * @param args
	 * @return        
	 * @throws 
	 * @see com.yunji.plat.base.BaseRepository#findCount(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Long findCount(String sql, Object... args) {
		Session session = (Session) entityManager.getDelegate();
		sql = "select count(*) from ("+sql+") t";
        NativeQuery<Number> query = session.createNativeQuery(sql);
        this.setParameters(query, args);
        return query.list().get(0).longValue();
	}
	
	
	/**
	 * @Descrip: 设置查询参数  
	 * @Title: setParameters   
	 * @param query
	 * @param args      
	 * @return: void      
	 * @throws
	 */
	public void setParameters(NativeQuery<?> query,Object... args) {
		if(args!=null&&args.length>0) {
        	int i = 1;
        	for (Object arg : args) {
        		query.setParameter(i++, arg);
        	}
        }
	}

}
