package com.expense.tracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friends_group")
public class Groups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Group_id")
	private long id;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "group_createdby_id")
	private int groupCreatedByid;

	@Column(name = "List_of_Friends")
	private String friendsLists;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}



	public int getGroupCreatedByid() {
		return groupCreatedByid;
	}

	public void setGroupCreatedByid(int groupCreatedByid) {
		this.groupCreatedByid = groupCreatedByid;
	}

	public String getFriendsLists() {
		return friendsLists;
	}

	public void setFriendsLists(String friendsLists) {
		this.friendsLists = friendsLists;
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

}

