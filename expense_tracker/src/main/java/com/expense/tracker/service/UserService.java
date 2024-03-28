package com.expense.tracker.service;

import com.expense.tracker.dto.LoginRequest;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.dto.SignUpRequest;

public interface UserService {

	Response<?> login(LoginRequest loginRequest) throws Exception;

	Response<?> registerUser(SignUpRequest signUpRequest);

	Response<?> searchByName(String userName);

	Response<?> getById(long userId);
	
	Response<?> getAllUsers(SearchDto searchDto);
}
