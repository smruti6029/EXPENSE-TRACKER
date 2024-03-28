package com.expense.tracker.controller;

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

import com.expense.tracker.dto.FriendRequest;
import com.expense.tracker.dto.Response;
import com.expense.tracker.service.FriendshipService;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestBody FriendRequest friendRequest) {
        Response<?> response = friendshipService.sendFriendStatus(friendRequest);
        return new ResponseEntity<>(response,  HttpStatus.valueOf(response.getResponseCode()));
    }

    @GetMapping(value = "/pending-requests" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPendingFriendRequests(@RequestParam long userID) {
        Response<?> response = friendshipService.getPendingFriendRequests(userID);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }

    @GetMapping(value = "/confirmed-friends",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFriends(@RequestParam long userID) {
        Response<?> response = friendshipService.getAllFriends(userID);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }
    
    @GetMapping(value = "/search-friends",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFriendsSearch(@RequestParam long userID, @RequestParam(required = false) String name) {
        Response<?> response;
        if (name != null && !name.isEmpty()) {
            response = friendshipService.searchFriendsByName(userID, name);
        } else {
            response = friendshipService.getAllFriends(userID);
        }
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }
    
}