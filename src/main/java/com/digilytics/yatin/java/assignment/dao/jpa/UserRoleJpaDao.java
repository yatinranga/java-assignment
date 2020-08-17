package com.digilytics.yatin.java.assignment.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.digilytics.yatin.java.assignment.entity.common.UserRoleKey;
import com.digilytics.yatin.java.assignment.entity.user.UserRole;

public interface UserRoleJpaDao extends JpaRepository<UserRole, UserRoleKey> {

	@Modifying
	@Query(value = "insert into user_role(user_id,role_id) values (?1,?2)", nativeQuery = true)
	public int save(Long userId, Long roleId);
	
}
