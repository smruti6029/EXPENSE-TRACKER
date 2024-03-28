package com.expense.tracker.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.model.Friends;
import com.expense.tracker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String name);
	
	List<User> findByusernameContaining(String name);
	
//	@Query("SELECT u FROM user u WHERE (u.id = :userID OR u.id = :userID)")
//    List<User> findByUserIdFriends(long userID);

	@Query(value = "select * from user where phone=?1 or email=?2", nativeQuery = true)
	Optional<User> findAllByPhoneNoEmail(String phoneNo, String email);

	List<User> findAll(Specification<User> specification);

	default List<User> findByUserName(SearchDto serachDto) {

		Specification<User> specification = (root, query, criteriaBuilder) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (serachDto.getSerchKeys() != null) {
				predicates.add(criteriaBuilder
						.or(criteriaBuilder.like(root.get("username"), "%" + serachDto.getSerchKeys() + "%")));
			}
			// query.orderBy(criteriaBuilder(root.get("createdOn")));

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		return findAll(specification);
	}
}