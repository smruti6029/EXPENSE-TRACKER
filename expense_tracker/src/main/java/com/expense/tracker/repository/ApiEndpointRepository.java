package com.expense.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense.tracker.model.ApiEndpoint;

@Repository
public interface ApiEndpointRepository extends JpaRepository<ApiEndpoint, Long> {
	
}
