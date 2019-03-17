package com.ando.exam.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
/**
 * @Descrip: 菜单
 * @ClassName:  Menu 
 * @author: ando
 * @date:   2019年2月20日 下午11:27:01    
 */
@Data
@Entity
@Table(name="sys_menu")
@Proxy(lazy = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Menu implements Serializable {
	private static final long serialVersionUID = 6354683061675750848L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long menuId;
	private Long pid;
	@Column(length=64)
	private String iconCls;
	@Column(length=64)
	private String rowCls;
	@Column(length=32)
	private String ui;
	@Column(length=64)
	private String menuHref;
	@Column(length=64)
	private String hrefTarget;
	@Column(length=128)
	private String text;
	@Column(length=128)
	private String tooltip;
	@Column(length=64)
	private String viewType;
	private Long rank;
}
