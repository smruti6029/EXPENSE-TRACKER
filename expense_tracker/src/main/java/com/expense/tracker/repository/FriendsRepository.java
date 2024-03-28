package com.expense.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expense.tracker.model.Friends;

public interface FriendsRepository extends JpaRepository<Friends, Long> {

	@Query("SELECT f FROM Friends f WHERE (f.userID1 = :userID OR f.userID2 = :userID) AND f.status = :status")
    List<Friends> findByUserID1OrUserID2AndStatus(long userID, Long status);
	
	
	//Friends findByUserID1AndUserID2(long userID1, long userID2);
	
	 @Query(value = "SELECT * FROM friends WHERE (user_id1 = :userID1 AND user_id2 = :userID2) OR (user_id1 = :userID2 AND user_id2 = :userID1)", nativeQuery = true)
	 Friends findByUserID1AndUserID2(@Param("userID1") long userID1, @Param("userID2") long userID2);

	List<Friends> findByUserID1AndStatus(long userID1, Long id);

}
