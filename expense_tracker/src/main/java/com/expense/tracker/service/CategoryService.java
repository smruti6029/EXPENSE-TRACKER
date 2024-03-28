package com.expense.tracker.service;

import com.expense.tracker.dto.CategoryDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;

public interface CategoryService {

    Response<?> getAllCategories(SearchDto searchDto);

    Response<?> getCategoryById(Long id);

    Response<?> saveCategory(CategoryDTO categoryDTO);

    Response<?> updateCategory(Long id, CategoryDTO categoryDTO);

    Response<?> deleteCategory(Long id);

}
