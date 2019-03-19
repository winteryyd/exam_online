package com.ando.exam.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @Descrip: 平台系统权限用户
 * @ClassName:  User 
 * @author: ando
 * @date:   2019年2月1日 上午10:24:47    
 */
@Data
@Entity
@Table(name="sys_user")
@Proxy(lazy = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {
	private static final long serialVersionUID = 1885900869737644404L;
	@Id
	@Column(length=32)
	private String empid;
	@Column(length=32)
	private String username;
	@Column(length=128)
	private String department;
	@Column(length=64)
	private String title;
	@Column(length=32)
	private String phone;
	@Column(length=128)
	private String email;
	@Column(length=64)
	private String password;
	@Column(length=4)
	private int active;
	@Transient
	private boolean auth=false;
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		if (empid != null ? !empid.equals(user.empid) : user.empid != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return empid != null ? empid.hashCode() : 0;
	}
}
