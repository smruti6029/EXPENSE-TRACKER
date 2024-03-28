package com.expense.tracker.dto;

import java.util.Date;
import java.util.List;

import com.expense.tracker.model.SplitExpenses;

public class SplitExpensesDTO {

	private long id;
	private long expenseId;
	private double totalAmount;
	private double splitAmount;

	private boolean isPaid;
	private Date createdOn;
	private Date updatedOn;
	private long createdBy;
	private long updatedBy;
	private boolean isActive;
	
	private CategoryDTO categoryDTO;
	
	private ExpenseDTO expenseDTO;
	
	private List<UserDto> splitUsers;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(long expenseId) {
		this.expenseId = expenseId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getSplitAmount() {
		return splitAmount;
	}
	public void setSplitAmount(double splitAmount) {
		this.splitAmount = splitAmount;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<UserDto> getSplitUsers() {
		return splitUsers;
	}
	public void setSplitUsers(List<UserDto> splitUsers) {
		this.splitUsers = splitUsers;
	}
	
	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}
	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}
	public ExpenseDTO getExpenseDTO() {
		return expenseDTO;
	}
	public void setExpenseDTO(ExpenseDTO expenseDTO) {
		this.expenseDTO = expenseDTO;
	}
	public static SplitExpensesDTO toDTO(SplitExpenses splitExpenses) {
        SplitExpensesDTO dto = new SplitExpensesDTO();
        dto.setId(splitExpenses.getId());
        dto.setExpenseId(splitExpenses.getExpense().getId());
        dto.setTotalAmount(splitExpenses.getTotalAmount());
        dto.setSplitAmount(splitExpenses.getSplitAmount());
        dto.setPaid(splitExpenses.getIsPaid());
        dto.setCreatedOn(splitExpenses.getCreatedOn());
        dto.setUpdatedOn(splitExpenses.getUpdatedOn());
        dto.setCreatedBy(splitExpenses.getCreatedBy());
        dto.setUpdatedBy(splitExpenses.getUpdatedBy());
        dto.setActive(splitExpenses.getIsActive());
        
        return dto;
    }
	
	
	

}
