package com.digilytics.yatin.java.assignment.view.role;

import com.digilytics.yatin.java.assignment.entity.user.Role;

public class RoleResponse {

	public String name;

	public Long id;

	public Boolean active;

	public RoleResponse(Long id, String name, Boolean active) {
		this.id = id;
		this.name = name;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public static RoleResponse get(Role role) {
		return new RoleResponse(role.getId(), role.getName(), role.getActive());
	}

}
