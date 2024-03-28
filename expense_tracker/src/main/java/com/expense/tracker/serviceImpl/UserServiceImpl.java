package com.expense.tracker.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.expense.tracker.dto.LoginRequest;
import com.expense.tracker.dto.LoginResponse;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.dto.SignUpRequest;
import com.expense.tracker.model.CredentialMaster;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.CredentialMasterRepository;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.security.JwtTokenUtil;
import com.expense.tracker.security.JwtUserDetailsService;
import com.expense.tracker.service.UserService;
import com.expense.tracker.validation.Validation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private CredentialMasterRepository credentialMasterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public Response<?> login(LoginRequest loginRequest) throws Exception {
		try {
			Response<?> validationResponse = Validation.checkLoginRequest(loginRequest);
			if (validationResponse.getResponseCode() == HttpStatus.OK.value()) {
				LoginResponse loginResponse = new LoginResponse();

				UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

				if (userDetails != null) {

					Optional<CredentialMaster> credentialMasterOptional = credentialMasterRepository
							.findByEmail(loginRequest.getEmail());

					if (credentialMasterOptional.isPresent()) {

						CredentialMaster credentialMaster = credentialMasterOptional.get();

						if (credentialMaster.passwordMatches(loginRequest.getPassword())) {
							loginResponse.setId(credentialMaster.getUser().getId());
							loginResponse.setEmail(credentialMaster.getEmail());
							loginResponse.setToken(jwtTokenUtil.generateToken(userDetails));
							return new Response<>(HttpStatus.OK.value(), "Login success.", loginResponse);
						}
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "Password do not match", null);
					} else {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "INVALID CREDENTIALS", null);
					}
				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "INVALID CREDENTIALS", null);
				}
			} else {
				return validationResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> registerUser(SignUpRequest signUpRequest) {
		try {
			Response<?> validationResponse = Validation.checkSignUpRequest(signUpRequest);
			if (validationResponse.getResponseCode() == HttpStatus.OK.value()) {
				Optional<User> existingUser = userRepository.findAllByPhoneNoEmail(signUpRequest.getPhone(),
						signUpRequest.getEmail());
				if (existingUser.isPresent() && (existingUser.get().getEmail().equals(signUpRequest.getEmail())
						|| existingUser.get().getPhone().equals(signUpRequest.getPhone()))) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(),
							"Email and phone number cannot be duplicate !!!", null);
				}
				User user = new User();
				user.setEmail(signUpRequest.getEmail());
				user.setPhone(signUpRequest.getPhone());
				user.setUsername(signUpRequest.getName());
				user.setCreatedOn(new Date());
				user.setUpdatedOn(new Date());
				user.setIsActive(true);
				userRepository.save(user);

				String userCode = UUID.randomUUID().toString().replaceAll("-", "");

				CredentialMaster credentialMaster = new CredentialMaster();
				credentialMaster.setUser(user);
				credentialMaster.setEmail(signUpRequest.getEmail());
				credentialMaster.setCreatedOn(new Date());
				credentialMaster.setUpdatedOn(new Date());
				credentialMaster.setIsActive(true);
				credentialMaster.setPassword(signUpRequest.getPassword());
				credentialMaster.setUserCode(userCode);
				CredentialMaster savedCredentialMaster = credentialMasterRepository.save(credentialMaster);

				if (savedCredentialMaster != null) {
					return new Response<>(HttpStatus.OK.value(), "User Registered Succefully  !!!",
							savedCredentialMaster);
				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Failed in User Registeration!!!", null);
				}
			} else {
				return validationResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Register user service goes wrong.", null);
		}
	}

	@Override
	public Response<?> getById(long userId) {
		try {
			Optional<User> user = userRepository.findById(userId);
			if (user != null && user.isPresent()) {
				return new Response<>(HttpStatus.OK.value(), "success", user.get());
			}
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong", null);
		}
	}
	@Override
	public Response<?> getAllUsers(SearchDto searchDto) {

		List<User> users = userRepository.findByUserName(searchDto);
		if (users.isEmpty()) {

			return new Response<>(HttpStatus.BAD_REQUEST.value(), "No Users found", null);
		}
		// users.stream().map(UserDto::toDTO).collect(Collectors.toList());
		return new Response<>(HttpStatus.OK.value(), "Users Lists...", users);

	}

    @Override
    public Response<?> searchByName(String userName) {
    	
        List<User> users = userRepository.findByusernameContaining(userName);
        
        if (users.isEmpty()) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "No users found with the given name", null);
        } else {
            return new Response<>(HttpStatus.OK.value(), "Users found with the given name", users);
        }
    }

}
