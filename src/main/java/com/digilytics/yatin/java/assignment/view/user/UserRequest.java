package com.digilytics.yatin.java.assignment.view.user;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.digilytics.yatin.java.assignment.entity.user.User;

public class UserRequest {

	@NotNull(message = "User's name can't be null")
	private String name;

	@Email(message = "Email pattern isn't correct")
	@NotNull(message = "User's email can't be null")
	private String email;

	@NotEmpty(message = "Role ids can't be null or empty")
	private Set<Long> roleIds;

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Set<Long> getRoleIds() {
		if (roleIds != null) {
			return roleIds.stream().map(id -> id).collect(Collectors.toSet());
		}
		return roleIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public User toEntity() {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setUsername(email);
		return user;
	}

}
