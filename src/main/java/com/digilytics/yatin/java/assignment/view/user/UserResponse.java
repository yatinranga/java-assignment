package com.digilytics.yatin.java.assignment.view.user;

import java.util.HashSet;
import java.util.Set;

import com.digilytics.yatin.java.assignment.entity.user.Role;
import com.digilytics.yatin.java.assignment.entity.user.User;
import com.digilytics.yatin.java.assignment.view.role.RoleResponse;

public class UserResponse {

	private Long id;
	private String name;
	private Boolean active;
	private String username;
	private String email;
	private Set<RoleResponse> roles;

	public UserResponse() {

	}

	public UserResponse(Long id, String name, Boolean active, String username, String email, String contactNo) {
		super();
		this.id = id;
		this.active = active;
		this.name = name;
		this.username = username;
		this.email = email;
	}

	public static UserResponse get(User user) {
		if (user != null) {
			UserResponse response = new UserResponse(user.getId() == null ? user.getUserId() : user.getId(),
					user.getName(), user.getActive(), user.getUsername(), user.getEmail(), user.getContactNo());
			if (user.getRoles() != null) {
				response.roles = new HashSet<>();
				for (Role role : user.getRoles()) {
					response.roles.add(RoleResponse.get(role));
				}
			}
			return response;
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleResponse> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleResponse> roles) {
		this.roles = roles;
	}

}
