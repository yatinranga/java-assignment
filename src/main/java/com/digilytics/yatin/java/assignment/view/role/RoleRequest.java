package com.digilytics.yatin.java.assignment.view.role;

import javax.validation.constraints.NotNull;

import com.digilytics.yatin.java.assignment.entity.user.Role;

public class RoleRequest {

	@NotNull(message = "Role name can't be null")
	private String name;

	public Role toEntity() {
		Role role = new Role();
		role.setName(name);
		return role;
	}

	public String getName() {
		return name;
	}

}
