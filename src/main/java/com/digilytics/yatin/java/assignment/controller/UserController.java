package com.digilytics.yatin.java.assignment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.yatin.java.assignment.service.UserService;
import com.digilytics.yatin.java.assignment.view.user.DigilyticsUserResponse;
import com.digilytics.yatin.java.assignment.view.user.UserRequest;
import com.digilytics.yatin.java.assignment.view.user.UserResponse;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(produces = { "application/json" }, consumes = { "application/json" }, value = "/user")
	public UserResponse save(@Valid @RequestBody UserRequest request) {
		return userService.save(request);
	}

	@PostMapping(value = "/register")
	public DigilyticsUserResponse registerFromFile(@RequestParam("file") MultipartFile file) {
		return userService.registerFromFile(file);
	}

}
