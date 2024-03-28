package com.expense.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.dto.CreateGroupRequest;
import com.expense.tracker.dto.Response;
import com.expense.tracker.service.FriendGroupService;

@RestController
@RequestMapping("/groups")
public class FriendGroupController {

	@Autowired
	private FriendGroupService friendGroupService;

	@PostMapping("/create")
	public ResponseEntity<?> createGroupAndAddFriends(@RequestBody CreateGroupRequest request) {
		Response<?> response = friendGroupService.createGroupAndAddFriends(request.getGroupName(),
				request.getGroupCreatedBy(), request.getFriendsList());
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@GetMapping(value = "/get")
	public ResponseEntity<?> getAllGroups(@RequestParam("createdByUserId") Integer createdByUserId) {
		Response<?> response = friendGroupService.getAllGroups(createdByUserId);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}
	
	@GetMapping(value = "/user-groups" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserGroups(@RequestParam("userID") long userID) {
	    Response<?> response = friendGroupService.getUserGroups(userID);
	    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}
	
	
}
