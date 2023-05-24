package com.edi.simplebackend.loans.repository;

import com.edi.simplebackend.loans.model.LoanData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanData, Long> {

	Page<LoanData> findByUserData_UserId(Long userId, Pageable pageable);

	List<LoanData> findByUserData_UserId(Long userId);

	Page<LoanData> findByUserData_Username(String username, Pageable pageable);

	List<LoanData> findByUserData_Username(String username);

	Page<LoanData> findByBookData_BookId(Long bookId, Pageable pageable);

	List<LoanData> findByBookData_BookId(Long bookId);

}
