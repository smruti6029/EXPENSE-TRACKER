package com.expense.tracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Friends")
public class Friends {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friends_id")
    private long id;

    @Column(name = "user_id1")
    private long userID1;

    @Column(name = "user_id2")
    private long userID2;

    @Column(name = "status")
    private long status;
    
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

	public long getUserID1() {
		return userID1;
	}

	public void setUserID1(long userID1) {
		this.userID1 = userID1;
	}

	public long getUserID2() {
		return userID2;
	}

	public void setUserID2(long userID2) {
		this.userID2 = userID2;
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

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	
	

    
}
