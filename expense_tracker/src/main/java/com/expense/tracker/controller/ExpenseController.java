package com.expense.tracker.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.dto.ExpenseDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.model.Expense;
import com.expense.tracker.repository.SplitExpenseRepository;
import com.expense.tracker.service.ExpenseService;
import com.expense.tracker.service.SplitExpenseService;
import com.expense.tracker.serviceImpl.SplitServiceImp;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
	
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private SplitExpenseService splitExpenseService;
	
    @Autowired
	private SplitExpenseRepository splitExpenseRepository;

	@PostMapping("/save")
	public ResponseEntity<?> addExpense(@RequestBody ExpenseDTO expenseDTO) {
		Response<?> response = expenseService.saveExpense(expenseDTO);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}
	
	@GetMapping(value = "/getbyid")
	public ResponseEntity<?> getAllExpense(@RequestParam("userID") Long userID) {
		Response<?> response = expenseService.getExpenseByUserID(userID);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}
	
	@GetMapping(value = "/getTodayExpense")
	public ResponseEntity<?> getAllExpenseToday(@RequestParam("userId") Long userId) {
		Response<?> response = expenseService.getExpenseByUserIDToday(userId);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}
	
//	@GetMapping(value = "/get/dashboard")
//	public ResponseEntity<?> getAllExpenseDashBoard(@RequestParam("userId") Long userId ,
//			@RequestParam("fromDate") Date fromDate 
//			,@RequestParam("toDate") Date toDate) {
//		Response<?> response = (Response<?>) expenseService.getExpensesForUser(userId);
//		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
//	}
	
	
//	@GetMapping(value = "/get/dashboard")
//	public ResponseEntity<?> getAllExpenseDashBoard(@RequestParam("userId") Long userId,
//	                                                 @RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
//	                                                 @RequestParam("toDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate) {
//	    
//	    Double totalExpenseLast7Days = splitExpenseRepository.getTotalExpenseForLast7Days(userId, fromDate, toDate);
//	    
//	    
//	    return new ResponseEntity<>(totalExpenseLast7Days, HttpStatus.OK);
//	}
	
	
	@GetMapping(value = "/get/dashboard")
	public ResponseEntity<?> getAllExpenseDashBoard(
	    @RequestParam("userId") Long userId,
	    @RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate,
	    @RequestParam("toDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate) {

	    Response<?> expensesByDate = splitExpenseService.getTotalExpenseByDate(userId, fromDate, toDate);

	    return new ResponseEntity<>(expensesByDate, HttpStatus.valueOf(expensesByDate.getResponseCode()));
	}
	

}
