package com.expense.tracker.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.expense.tracker.dto.Response;
import com.expense.tracker.model.ApiEndpoint;
import com.expense.tracker.repository.ApiEndpointRepository;
import com.expense.tracker.service.ApiService;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	private ApiEndpointRepository apiEndpointRepository;

	@Override
	public Response<?> getAll() {
		List<ApiEndpoint> list = apiEndpointRepository.findAll();
		return new Response<>(HttpStatus.OK.value(), "success", list);
	}

}
