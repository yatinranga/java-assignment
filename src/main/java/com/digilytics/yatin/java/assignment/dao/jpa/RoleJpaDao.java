package com.digilytics.yatin.java.assignment.dao.jpa;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.digilytics.yatin.java.assignment.entity.user.Role;
import com.digilytics.yatin.java.assignment.view.role.RoleResponse;

public interface RoleJpaDao extends JpaRepository<Role, Long> {

	public Boolean existsByName(String name);

	public RoleResponse findResponseById(Long id);

	@Query(value = "select id from Role where active=?1")
	public List<Long> getAllIdsByActive(Boolean active);

	@Modifying
	@Query(value = "SELECT  r.id, r.name FROM yatin_java_assignment.role r where r.active=true", nativeQuery = true)
	public Map<Integer, String> findByIdAndNameAndActiveTrue();

	@Query(value = "SELECT  r.name FROM yatin_java_assignment.role r where r.active=true", nativeQuery = true)
	public List<String> findNameAndActiveTrue();

	public List<RoleResponse> findAllByActiveTrue();

	@Query(value = "SELECT r.id  " + "FROM Role r where  " + " r.name in ?1")
	public Set<Long> findIdByNames(List<String> roleNames);

}
