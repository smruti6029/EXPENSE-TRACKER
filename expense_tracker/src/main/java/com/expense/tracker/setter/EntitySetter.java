package com.expense.tracker.setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.expense.tracker.dto.CategoryDTO;
import com.expense.tracker.model.Category;
@Component
public class EntitySetter {
	public  CategoryDTO toDTO(Category category) {
		return (category == null) ? null
				: new CategoryDTO(category.getId(), category.getName(),
						(category.getCreatedAt() != null) ? category.getCreatedAt() : new Date());
	}

	public  Category toEntity(CategoryDTO categoryDTO) {
		return (categoryDTO == null) ? null
				: new Category(categoryDTO.getId(), categoryDTO.getName(),
						(categoryDTO.getCreatedAt() != null) ? categoryDTO.getCreatedAt() : new Date());
	}
	
	public  List<CategoryDTO> toDTOList(List<Category> categories) {
        return categories.stream().map(new EntitySetter()::toDTO).collect(Collectors.toList());
    }
}