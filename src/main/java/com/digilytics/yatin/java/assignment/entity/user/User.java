package com.digilytics.yatin.java.assignment.entity.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.digilytics.yatin.java.assignment.entity.common.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity implements Serializable {

	@NotNull(message = "name can't be null")
	private String name;

	@NotNull(message = "username can't be null")
	@Pattern(regexp = "^[@A-Za-z0-9._]{3,50}$", message = "username should contains only alphabets/digit/@ and length should be in between 4 to 20")
	private String username;

	@NotNull(message = "password can't be null")
	private String password;

	private Boolean active;

	private String generatedPassword;

	private String email;

	@Size(min = 10, max = 10)
	@Pattern(regexp = "^[0-9]*$", message = "Contact no should contain only digit")
	private String contactNo;

	@Transient
	private Long userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getGeneratedPassword() {
		return generatedPassword;
	}

	public void setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
