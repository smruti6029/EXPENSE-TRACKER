package com.expense.tracker.dto;

import java.util.List;

public class CreateGroupRequest {
	
    private Integer id;
    
    private String groupName;
    
    private Integer groupCreatedBy;
    
    private List<Integer> friendsList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupCreatedBy() {
		return groupCreatedBy;
	}

	public void setGroupCreatedBy(Integer groupCreatedBy) {
		this.groupCreatedBy = groupCreatedBy;
	}

	public List<Integer> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<Integer> friendsList) {
		this.friendsList = friendsList;
	}
    
    

}
