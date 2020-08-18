package com.digilytics.yatin.java.assignment.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.yatin.java.assignment.view.user.DigilyticsUserResponse;
import com.digilytics.yatin.java.assignment.view.user.UserRequest;
import com.digilytics.yatin.java.assignment.view.user.UserResponse;

public interface UserService {

	/**
	 * this method used to save user detail.
	 * 
	 * @param request
	 * @return {@link UserResponse}
	 */
	UserResponse save(UserRequest request);

	/**
	 * this methos is used for saving user from csv file
	 * 
	 * @param file
	 * @return {@link DigilyticsUserResponse}
	 */
	DigilyticsUserResponse registerFromFile(MultipartFile file);

	/**
	 * this method is used for downloading error file
	 * 
	 * @param file
	 * @return {@link Resource}
	 */
	Resource downloadErrorFileAsResource(String file);

}
