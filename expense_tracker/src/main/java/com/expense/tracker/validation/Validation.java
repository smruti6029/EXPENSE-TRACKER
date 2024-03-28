package com.expense.tracker.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import com.expense.tracker.dto.LoginRequest;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SignUpRequest;

public class Validation {

	public static Response<?> checkSignUpRequest(SignUpRequest signUpRequest) {
		if (signUpRequest.getName() == null || signUpRequest.getName().isBlank()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Name cannot be empty", null);
		} else if (signUpRequest.getEmail() == null || signUpRequest.getEmail().isBlank()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Email cannot be empty", null);
		} else if (!isValidEmail(signUpRequest.getEmail())) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid email format", null);
		} else if (signUpRequest.getPhone() == null || signUpRequest.getPhone().isBlank()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Phone number cannot be empty", null);
		} else if (!isValidPhoneNumber(signUpRequest.getPhone())) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid phone number format", null);
		} else if (signUpRequest.getPassword() == null || signUpRequest.getPassword().isBlank()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Password cannot be empty", null);
		} else {
			return new Response<>(HttpStatus.OK.value(), "success", null);
		}
	}

	public static Response<?> checkLoginRequest(LoginRequest loginRequest) {
		if (loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Email cannot be empty", null);
		} else if (!isValidEmail(loginRequest.getEmail())) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid email format", null);
		} else if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Password cannot be empty", null);
		} else {
			return new Response<>(HttpStatus.OK.value(), "success", null);
		}
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		String phoneRegex = "^[0-9]{10}$";
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
