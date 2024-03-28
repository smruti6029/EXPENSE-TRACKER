package com.expense.tracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.expense.tracker.dto.ExpenseDTO;

@Entity
@Table(name = "expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "description")
	private String description;

	@Column(name = "amount")
	private double amount;

	@Column(name = "category_id")
	private long categoryId;

	@Column(name = "is_split")
	private Boolean isSplit;

	@Column(name = "expense_date")
	private Date expenseDate;

	@Column(name = "expense_by")
	private long expenseBy;

	@Column(name = "created_by")
	private long createdBy;

	@Column(name = "updated_by")
	private long updatedBy;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "is_active")
	private Boolean isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getExpenseBy() {
		return expenseBy;
	}

	public void setExpenseBy(long expenseBy) {
		this.expenseBy = expenseBy;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Expense(long id, String description, double amount, long categoryId, Boolean isSplit, Date expenseDate,
			long expenseBy, long createdBy, long updatedBy, Date createdOn, Date updatedOn, Boolean isActive) {
		super();
		this.id = id;
		this.description = description;
		this.amount = amount;
		this.categoryId = categoryId;
		this.isSplit = isSplit;
		this.expenseDate = expenseDate;
		this.expenseBy = expenseBy;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.isActive = isActive;
	}

	public Expense() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public static ExpenseDTO fromEntityToDto(Expense expense) {
	    ExpenseDTO expenseDTO = new ExpenseDTO();
	    expenseDTO.setId(expense.getId());
	    expenseDTO.setAmount(expense.getAmount());
	    expenseDTO.setCategoryId(expense.getCategoryId());
	    expenseDTO.setDescription(expense.getDescription());
	    expenseDTO.setIsSplit(expense.getIsSplit());
	    expenseDTO.setExpenseDate(expense.getExpenseDate());
	    return expenseDTO;
	}

	
}
