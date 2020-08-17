package com.digilytics.yatin.java.assignment.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digilytics.yatin.java.assignment.entity.user.User;


public interface UserJpaDao extends JpaRepository<User, Long>{

	public Boolean existsByUsername(String username);

	@Query(value = "select id from User where  email = ?1 and active = ?2")
	public Long findIdByEmailAndActive(String email, Boolean active);


}
