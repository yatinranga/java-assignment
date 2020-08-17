package com.digilytics.yatin.java.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digilytics.yatin.java.assignment.dao.jpa.RoleJpaDao;
import com.digilytics.yatin.java.assignment.entity.user.Role;
import com.digilytics.yatin.java.assignment.ex.ValidationException;
import com.digilytics.yatin.java.assignment.service.RoleService;
import com.digilytics.yatin.java.assignment.view.role.RoleRequest;
import com.digilytics.yatin.java.assignment.view.role.RoleResponse;

@Service("roleServiceImpl")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleJpaDao roleDao;

	/**
	 * this method used to validate request
	 *
	 * @param request
	 */
	private void validateRequest(RoleRequest request) {
		if (roleDao.existsByName(request.getName())) {
			throw new ValidationException(String.format(" Role (%s) is already exists", request.getName()));
		}
	}

	@Override
	public RoleResponse save(RoleRequest request) {
		validateRequest(request);
		Role role = request.toEntity();
		roleDao.save(role);
		return roleDao.findResponseById(role.getId());
	}

}
