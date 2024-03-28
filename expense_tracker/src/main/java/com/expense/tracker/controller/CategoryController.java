package com.expense.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.dto.CategoryDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {

		Response<?> response = categoryService.saveCategory(categoryDTO);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@GetMapping(value = "/getAll")
	public ResponseEntity<?> getAllCategories(@RequestParam(required = false) String searchKeys) {
		SearchDto searchDto = new SearchDto();
		searchDto.setSerchKeys(searchKeys);

		Response<?> response = categoryService.getAllCategories(searchDto);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
		Response<?> response = categoryService.getCategoryById(id);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
		Response<?> response = categoryService.updateCategory(id, categoryDTO);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		Response<?> response = categoryService.deleteCategory(id);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}
}