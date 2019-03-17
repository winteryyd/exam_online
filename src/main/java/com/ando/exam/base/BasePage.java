package com.ando.exam.base;

import lombok.Data;

/**
 * @Description:基础分页�?
 * @ClassName:  BasePage 
 * @author: ando
 * @date:   2019�?1�?31�? 下午9:37:36  
 * @param <T>  
 */
@Data
public class BasePage<T> {
	private int page; //当前�?
	private int start; //�?始位�?
	private int limit; //每页数据条数
	private String direction; // 排序�? ASC,DESC
	private String property; // 排序字段名称 匹配entity的属性名�?
	private String query; //查询�?
	private T entity;        //根据该对象条件分页查�?
	
	public int getPage() {
		return page-1;
	}
}
