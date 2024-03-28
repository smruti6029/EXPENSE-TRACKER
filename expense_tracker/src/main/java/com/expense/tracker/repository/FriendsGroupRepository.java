package com.expense.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.tracker.dto.Response;
import com.expense.tracker.model.Groups;

public interface FriendsGroupRepository extends JpaRepository<Groups ,Long> {

	List<Groups> findBygroupCreatedByid(Integer createdByUserId);

	List<Groups>findByFriendsListsContaining(String userId);

	Groups findBygroupName(String groupName);

}
