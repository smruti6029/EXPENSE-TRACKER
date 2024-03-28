package com.expense.tracker.dto;

import java.util.ArrayList;
import java.util.List;

import com.expense.tracker.model.Groups;
import com.expense.tracker.model.User;


public class GroupDTO {


	private Long id;
    
    private String groupName;
    
    private Integer groupCreatedBy;
    
    private List<Long> friendsList;
    
    private List<User> userList;
    
   
	public List<Long> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<Long> friendsList) {
		this.friendsList = friendsList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

    


	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public static GroupDTO convertToDTO(Groups group) {
	    GroupDTO groupDTO = new GroupDTO();
	    // Map group attributes to groupDTO
	    groupDTO.setId(group.getId());
	    groupDTO.setGroupName(group.getGroupName());
	    groupDTO.setGroupCreatedBy(group.getGroupCreatedByid());
	    List<Long> friendsList = convertStringToList(group.getFriendsLists());
	    groupDTO.setFriendsList(friendsList);
	    return groupDTO;
	}

	
	public static List<Long> convertStringToList(String friendsLists) {
	    List<Long> friendsList = new ArrayList<>();
	    if (friendsLists != null && !friendsLists.isEmpty()) {
	        String[] friendIdsArray = friendsLists.split(",");
	        for (String friendId : friendIdsArray) {
	            friendsList.add((long)Integer.parseInt(friendId.trim()));
	        }
	    }
	    return friendsList;
	}

    
    
}