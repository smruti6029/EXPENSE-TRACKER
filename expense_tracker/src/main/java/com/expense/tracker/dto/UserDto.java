package com.expense.tracker.dto;

import java.util.Date;

import com.expense.tracker.model.User;

public class UserDto {

	private long id;

	private String name;

	private String email;

	private String phone;

	private Date createdOn;

	private Boolean isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public static UserDto toDTO(User user) {
		if (user == null) {
			return null;
		}
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		dto.setCreatedOn(user.getCreatedOn());
		return dto;
	}

	public static User toEntity(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		User user = new User();
		user.setUsername(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getEmail());
		return user;
	}

}
