package com.expense.tracker.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import com.expense.tracker.model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseDTO {

	private Long id;

	private String description;

	private double amount;

	private long categoryId;
	
	private CategoryDTO category;

	private Boolean isSplit;

	private Date expenseDate;

	private Long userId;

	private List<Long> splitUsers;
	
	List<SplitExpensesDTO> splitExpensesDTOs;
	
	

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public Boolean getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Boolean isSplit) {
		this.isSplit = isSplit;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getSplitUsers() {
		return splitUsers;
	}

	public void setSplitUsers(List<Long> splitUsers) {
		this.splitUsers = splitUsers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<SplitExpensesDTO> getSplitExpensesDTOs() {
		return splitExpensesDTOs;
	}

	public void setSplitExpensesDTOs(List<SplitExpensesDTO> splitExpensesDTOs) {
		this.splitExpensesDTOs = splitExpensesDTOs;
	}

	
	public static Expense fromDtoToEntity(ExpenseDTO expenseDTO) {
		Expense obj = new Expense();
		obj.setAmount(expenseDTO.getAmount());
		obj.setCategoryId(expenseDTO.getCategoryId());
		obj.setDescription(expenseDTO.getDescription());
		obj.setIsSplit(expenseDTO.getIsSplit());
		obj.setExpenseDate(expenseDTO.getExpenseDate());
		return obj;
	}

}
