package com.expense.tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.expense.tracker.model.CredentialMaster;

@Repository
public interface CredentialMasterRepository extends JpaRepository<CredentialMaster, Long> {

	Optional<CredentialMaster> findByEmail(String username);

	@Query(value = "select * from credential_master where email=?1", nativeQuery = true)
	List<CredentialMaster> findAllByPhoneNoEmail(String email);

	@Query(value = "select * from credential_master where user_code=?1", nativeQuery = true)
	Optional<CredentialMaster> getByUserCode(String userCode);

}
