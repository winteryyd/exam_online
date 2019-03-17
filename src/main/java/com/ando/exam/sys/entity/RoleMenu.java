package com.ando.exam.sys.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_role_menu")
@Proxy(lazy = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@IdClass(RoleMenuKey.class)
public class RoleMenu implements Serializable {
	private static final long serialVersionUID = 6354683061675750848L;
	@Id
	private Long roleId;
	@Id
	private Long menuId;

	@Transient
	private Role role;
	@Transient
	private Menu menu;

	public RoleMenu() {
	}

	public RoleMenu(Long roleId, Long menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}
	
	public RoleMenu(Long roleId, Menu menu) {
		this.roleId = roleId;
		this.menu = menu;
	}

	public RoleMenu(Long menuId, Role role) {
		this.menuId = menuId;
		this.role = role;
	}

	public RoleMenu(Role role, Menu menu) {
		this.role = role;
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = PRIME * result + ((menuId == null) ? 0 : menuId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RoleMenu other = (RoleMenu) obj;
		if (roleId == null) {
			if (other.roleId != null) {
				return false;
			}
		} else if (!roleId.equals(other.roleId)) {
			return false;
		}
		if (menuId == null) {
			if (other.menuId != null) {
				return false;
			}
		} else if (!menuId.equals(other.menuId)) {
			return false;
		}
		return true;
	}
}
