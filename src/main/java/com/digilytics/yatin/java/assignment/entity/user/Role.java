package com.digilytics.yatin.java.assignment.entity.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.digilytics.yatin.java.assignment.entity.common.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "role")
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class Role extends BaseEntity implements Serializable {

	@NotEmpty(message = "name can't be empty")
	@Column(name = "name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	private List<UserRole> roleUsers;

	public Role(Long id, String name) {
		super();
		this.setId(id);
		this.name = name;
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public Role() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserRole> getRoleUsers() {
		return roleUsers;
	}

	public void setRoleUsers(List<UserRole> roleUsers) {
		this.roleUsers = roleUsers;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ((roleUsers == null) ? 0 : roleUsers.hashCode());
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Role other = (Role) obj;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//
//		if (roleUsers == null) {
//			if (other.roleUsers != null)
//				return false;
//		} else if (!roleUsers.equals(other.roleUsers))
//			return false;
//		return true;
//	}

}
