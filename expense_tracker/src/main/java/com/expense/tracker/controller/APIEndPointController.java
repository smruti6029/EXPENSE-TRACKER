package com.expense.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.expense.tracker.dto.Requestdto;
import com.expense.tracker.dto.Response;
import com.expense.tracker.service.ApiService;

@RestController
@RequestMapping("/api")
public class APIEndPointController {

	@Autowired
	private ApiService apiService;

	@GetMapping("/")
	public ResponseEntity<?> getAll() {
		Response<?> response = apiService.getAll();
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	}

	@PostMapping("/post")
	public ResponseEntity<?> postRequest(@RequestParam("files") List<MultipartFile> files ,
			@RequestBody Requestdto dto) {
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}
