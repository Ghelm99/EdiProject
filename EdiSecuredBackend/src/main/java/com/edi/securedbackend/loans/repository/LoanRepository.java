package com.edi.securedbackend.loans.repository;

import com.edi.securedbackend.loans.model.LoanData;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanData, Long> {

	Page<LoanData> findByBookData_BookId(Long bookId, Pageable pageable);

	List<LoanData> findByBookData_BookId(Long bookId);

	Page<LoanData> findByUserData_Email(String email, Pageable pageable);

	List<LoanData> findByUserData_Email(String email);

	Page<LoanData> findByUserData_UserId(Long userId, Pageable pageable);

	List<LoanData> findByUserData_UserId(Long userId);

}
