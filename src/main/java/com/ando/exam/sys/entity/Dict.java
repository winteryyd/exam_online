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
 * @Descrip: 字典
 * @ClassName:  Dictionary 
 * @author: ando
 * @date:   2019年2月20日 下午11:26:39    
 */
@Data
@Entity
@Table(name="sys_dict")
@Proxy(lazy = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Dict implements Serializable{
	private static final long serialVersionUID = 2455511089924738404L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dictId;
	@Column(length=64)
	private String dictType;
	@Column(length=64)
	private String dictValue;
	private String dictLabel;
	private Long rank;
}
