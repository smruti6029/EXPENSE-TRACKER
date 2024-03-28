package com.expense.tracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.expense.tracker.constant.NotificationType;

@Entity
@Table(name = "notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "text")
	private String text;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "split_expense_id")
	private long splitExpenseId;

	@Column(name = "notification_type")
	private NotificationType notificationType;

	@Column(name = "is_seen")
	private Boolean isSeen;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getSplitExpenseId() {
		return splitExpenseId;
	}

	public void setSplitExpenseId(long splitExpenseId) {
		this.splitExpenseId = splitExpenseId;
	}

	public Boolean getIsSeen() {
		return isSeen;
	}

	public void setIsSeen(Boolean isSeen) {
		this.isSeen = isSeen;
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

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public Notification(long id, String text, long userId, long splitExpenseId, NotificationType notificationType,
			Boolean isSeen, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.text = text;
		this.userId = userId;
		this.splitExpenseId = splitExpenseId;
		this.notificationType = notificationType;
		this.isSeen = isSeen;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

}
