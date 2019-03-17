package com.ando.exam.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_role")
@Proxy(lazy = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Role implements Serializable {
	private static final long serialVersionUID = 1885900869737644404L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	@Column(length = 32)
	private String enRoleName;
	@Column(length = 64)
	private String chRoleName;
	@Column(length = 128)
	private String remark;
	@Transient
	private boolean auth=false;
	

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Role role = (Role) o;
		if (roleId != null ? !roleId.equals(role.roleId) : role.roleId != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return roleId != null ? roleId.hashCode() : 0;
	}
}
