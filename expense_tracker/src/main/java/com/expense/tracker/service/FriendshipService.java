package com.expense.tracker.service;

import java.util.List;

import com.expense.tracker.dto.FriendRequest;
import com.expense.tracker.dto.Response;

public interface FriendshipService {
	

    Response<?> sendFriendStatus(FriendRequest friendRequest);
    
    Response<?> getAllFriends(Long userID);

	Response<?> searchFriendsByName(Long userID, String name);

    Response<?> sendFriendStatus(Long userID1, Long userID2 ,Long status);

	Response<?> getPendingFriendRequests(Long userID);

}
