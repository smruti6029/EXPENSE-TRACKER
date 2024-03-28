package com.expense.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.dto.Response;
import com.expense.tracker.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/userId")
	public ResponseEntity<?> getAllByUserId(@RequestParam("userId") long userId) {
		Response<?> response = notificationService.getAllByUserIdCategorized(userId);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@PostMapping("/markseen")
	public ResponseEntity<?> markSeen(@RequestParam("userId") long userId) {
		notificationService.markNotificationAsSeen(userId);
		return new ResponseEntity<>(new Response<>(), HttpStatus.OK);
	}
}
