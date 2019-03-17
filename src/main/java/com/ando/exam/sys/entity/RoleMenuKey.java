package com.ando.exam.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class RoleMenuKey implements Serializable {
	private static final long serialVersionUID = 6354683061675750848L;
	@Id
	private Long roleId;
	@Id
	private Long menuId;

	public RoleMenuKey() {
	}

	public RoleMenuKey(Long roleId, Long menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
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
		final RoleMenuKey other = (RoleMenuKey) obj;
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
