package com.expense.tracker.service;

import java.util.List;

import com.expense.tracker.dto.Response;

public interface FriendGroupService {

	
	Response<?>  getAllGroups(Integer createdByUserId);

	Response<?> createGroupAndAddFriends(String groupName, Integer createdByUserId, List<Integer> friendIds);

	Response<?> getUserGroups(Long userID);
	
	

}
