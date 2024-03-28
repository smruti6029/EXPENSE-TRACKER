package com.expense.tracker.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.expense.tracker.dto.CategoryDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.model.Category;
import com.expense.tracker.repository.CategoryRepository;
import com.expense.tracker.service.CategoryService;
import com.expense.tracker.setter.EntitySetter;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Autowired
	private EntitySetter entitySetter;

	@Override
	public Response<?> saveCategory(CategoryDTO categoryDTO) {
		try {
			Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
			if (existingCategory.isPresent()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Category already exists", null);
			}
			if ( categoryDTO.getName().isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Please Give Catagory Name...", null);

			}
			Category category = entitySetter.toEntity(categoryDTO);
			Category savedCategory = categoryRepository.save(category);
			CategoryDTO savedCategoryDTO = entitySetter.toDTO(savedCategory);
			return new Response<>(HttpStatus.OK.value(), "Category saved successfully", savedCategoryDTO);
		} catch (Exception e) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Unable to saving category", null);
		}
	}

	@Override
	public Response<?> getAllCategories(SearchDto searchDto) {
		List<Category> categories = categoryRepository.findByCatagoryName(searchDto);
		if (categories.isEmpty()) {
			return new Response<>(200, "No categories found", null);
		}
		List<CategoryDTO> categoryDTOs = entitySetter.toDTOList(categories);
		return new Response<>(HttpStatus.OK.value(), "Categories retrieved successfully", categoryDTOs);
	}

	@Override
	public Response<?> getCategoryById(Long id) {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if (optionalCategory.isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Category not found", null);
		}
		CategoryDTO categoryDTO = entitySetter.toDTO(optionalCategory.get());
		return new Response<>(HttpStatus.OK.value(), "Category Lists...", categoryDTO);
	}

	@Override
	public Response<?> updateCategory(Long id, CategoryDTO categoryDTO) {
		try {
			Optional<Category> optionalCategory = categoryRepository.findById(id);
			if (optionalCategory.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Category not found", null);
			}

			Category existingCategory = optionalCategory.get();
			if (categoryDTO.getName() != null) {
				existingCategory.setName(categoryDTO.getName());
			}
			if (categoryDTO.getCreatedAt() != null) {
				existingCategory.setCreatedAt(categoryDTO.getCreatedAt());
			}

			Category updatedCategory = categoryRepository.save(existingCategory);
			CategoryDTO updatedCategoryDTO = entitySetter.toDTO(updatedCategory);
			return new Response<>(HttpStatus.OK.value(), "Category updated successfully", updatedCategoryDTO);
		} catch (Exception e) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Error updating category", null);
		}
	}

	@Override
	public Response<?> deleteCategory(Long id) {
		try {
			Optional<Category> optionalCategory = categoryRepository.findById(id);
			if (optionalCategory.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Category not found", null);
			}

			categoryRepository.deleteById(id);
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Category deleted successfully", null);
		} catch (Exception e) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Error deleting category", null);
		}
	}
}