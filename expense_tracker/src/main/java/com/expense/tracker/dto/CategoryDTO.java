package com.expense.tracker.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.expense.tracker.model.Category;

public class CategoryDTO {
	private Long id;
	private String name;
	private Date createdAt;

	public CategoryDTO() {
	}

	public CategoryDTO(Long id, String name, Date createdAt) {
		this.id = id;
		this.name = name;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public static CategoryDTO convertEntityToDto(Category entity) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setCreatedAt(entity.getCreatedAt());
		return dto;
	}

}