package com.digilytics.yatin.java.assignment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digilytics.yatin.java.assignment.service.RoleService;
import com.digilytics.yatin.java.assignment.view.role.RoleRequest;
import com.digilytics.yatin.java.assignment.view.role.RoleResponse;

@RestController
@RequestMapping("/api/")
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * save the role and return the same if authority not exist ,it will return
	 * empty list
	 *
	 * @param request [Key value pair which we want to save that role]
	 * @return RoleResponse
	 */

	@PostMapping(value = "role", consumes = { "application/json" }, produces = { "application/json" })
	public RoleResponse save(@Valid @RequestBody RoleRequest request) {
		return roleService.save(request);
	}

}
