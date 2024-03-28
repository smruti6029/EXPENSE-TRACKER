package com.expense.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.service.SplitExpenseService;

@RestController
@RequestMapping("/split/expense")
public class SplitExpenseController {

	@Autowired
	private SplitExpenseService splitExpenseService;

	@PostMapping("/daily/basic")
	public ResponseEntity<?> splitDailyBasicExpense(@RequestBody SearchDto searchDto) {

		Response<?> response = splitExpenseService.getDallyBAsicExpense(searchDto);

		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

}
