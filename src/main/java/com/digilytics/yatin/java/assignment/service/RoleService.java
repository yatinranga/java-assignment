package com.digilytics.yatin.java.assignment.service;

import javax.validation.ValidationException;

import com.digilytics.yatin.java.assignment.view.role.RoleRequest;
import com.digilytics.yatin.java.assignment.view.role.RoleResponse;


public interface RoleService {


    /**
     * this method used to save role details. If role already exist in database
     * then it throws exception otherwise it return saved role details.
     *
     * @param request
     * @return {@Link RoleResponse}
     * @throws ValidationException if role already exist in database or some of the authority
     *                             ids are not valid
     */
	RoleResponse save(RoleRequest request);

}
