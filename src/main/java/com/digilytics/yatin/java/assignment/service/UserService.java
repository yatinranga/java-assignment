package com.digilytics.yatin.java.assignment.service;

import org.springframework.web.multipart.MultipartFile;

import com.digilytics.yatin.java.assignment.ex.ValidationException;
import com.digilytics.yatin.java.assignment.view.user.DigilyticsUserResponse;
import com.digilytics.yatin.java.assignment.view.user.UserRequest;
import com.digilytics.yatin.java.assignment.view.user.UserResponse;

public interface UserService {

	/**
	 * this method used to save user detail.
	 *
	 * @param request
	 * @return {@Link UserResponse}
	 * @throws ValidationException if role ids are not valid or username or email or
	 *                             contact number already exist
	 */
	UserResponse save(UserRequest request);

	DigilyticsUserResponse registerFromFile(MultipartFile file);

}
