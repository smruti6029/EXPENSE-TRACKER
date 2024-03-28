package com.expense.tracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "split_expense")
public class SplitExpenses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "expense_id")
	private Expense expense;

	@Column(name = "total_amount")
	private double totalAmount;

	@Column(name = "split_amount")
	private double splitAmount;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "is_paid")
	private Boolean isPaid;
	
	@Column(name = "isSplit")
	private Boolean isSplit;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "created_by")
	private long createdBy;

	@Column(name = "updated_by")
	private long updatedBy;

	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name="split_users")
	private String splitusers;
	

	public Boolean getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Boolean isSplit) {
		this.isSplit = isSplit;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

	public String getSplitusers() {
		return splitusers;
	}

	public void setSplitusers(String splitusers) {
		this.splitusers = splitusers;
	}

	public SplitExpenses(long id, Expense expense, double totalAmount, double splitAmount, long userId, Boolean isPaid,
			Date createdOn, Date updatedOn, long createdBy, long updatedBy, Boolean isActive) {
		super();
		this.id = id;
		this.expense = expense;
		this.totalAmount = totalAmount;
		this.splitAmount = splitAmount;
		this.userId = userId;
		this.isPaid = isPaid;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
	}

	public SplitExpenses() {
		super();
		// TODO Auto-generated constructor stub
	}

}
