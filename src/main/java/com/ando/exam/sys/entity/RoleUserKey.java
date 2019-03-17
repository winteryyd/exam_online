package com.ando.exam.sys.entity;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class RoleUserKey implements Serializable {
	private static final long serialVersionUID = 1885900869737644404L;
	@Id
	private String empid;
	@Id
	private Long roleId;

	public RoleUserKey() {
	}
	
	public RoleUserKey(String empid,Long roleId) {
		this.empid = empid;
		this.roleId = roleId;
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((empid == null) ? 0 : empid.hashCode());
		result = PRIME * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final RoleUserKey other = (RoleUserKey) obj;
		if (roleId == null ? other.roleId != null : !roleId.equals(other.roleId)) {
			return false;
		}
		if (empid == null ? other.empid != null : !empid.equals(other.empid)) {
			return false;
		}
		return true;
	}
}
