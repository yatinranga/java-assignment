package com.digilytics.yatin.java.assignment.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.digilytics.yatin.java.assignment.entity.user.User;

public class BaseService {

	public static User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		if (authentication.getPrincipal() instanceof User) {
			return ((User) authentication.getPrincipal());
		}
		return null;
	}

	public static Long getUserId() {
		User user = getUser();
		return user == null ? null : user.getUserId();
	}

}
