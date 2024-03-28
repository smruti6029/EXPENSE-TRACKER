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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.dto.LoginRequest;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.dto.SignUpRequest;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
    @Autowired
	private UserRepository userRepository;

	@PostMapping("/signIn")
	public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) throws Exception {

		Response<?> loginResponse = userService.login(loginRequest);

		return new ResponseEntity<>(loginResponse, HttpStatus.valueOf(loginResponse.getResponseCode()));

	}

	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {

		Response<?> customResponse = userService.registerUser(signUpRequest);

		return new ResponseEntity<>(customResponse, HttpStatus.valueOf(customResponse.getResponseCode()));

	}
	
	@GetMapping(value = "/search" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser(@RequestParam("userName") String userName) throws Exception {

		Response<?> customResponse = userService.searchByName(userName);

		return new ResponseEntity<>(customResponse, HttpStatus.OK);

	}
	
	
	
	
	 @GetMapping("/getAll")
	    public ResponseEntity<Response<?>> getAllCategories(@RequestParam(required = false) String searchKeys) {
	        SearchDto searchDto = new SearchDto();
	        searchDto.setSerchKeys(searchKeys);

	        Response<?> response = userService.getAllUsers(searchDto);
	        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
	    }
}
