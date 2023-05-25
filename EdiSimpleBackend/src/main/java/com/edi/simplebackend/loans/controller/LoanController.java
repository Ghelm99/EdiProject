package com.edi.simplebackend.loans.controller;

import com.edi.simplebackend.loans.model.Loan;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loans")
public class LoanController {

	private final LoanService loanService;

	@PostMapping(params = {"userEmail", "bookId"})
	public ResponseEntity<Loan> addLoan(
			@RequestParam final String userEmail,
			@RequestParam final Long bookId
	) {

		return ResponseEntity.ok(this.loanService.addLoan(userEmail, bookId));
	}

	@DeleteMapping(params = "loanId")
	public ResponseEntity<String> deleteLoan(
			@RequestParam final Long loanId
	) {

		loanService.deleteLoan(loanId);
		return ResponseEntity.ok("Loan " + loanId + " deleted!");
	}

	@GetMapping(params = "bookId")
	public ResponseEntity<List<Loan>> getLoansByBookId(

			@RequestParam final Long bookId,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.loanService.getLoansByBookId(bookId));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "loanId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.loanService.getLoansByBookId(bookId, pageable));
		}
	}

	@GetMapping(params = "userEmail")
	public ResponseEntity<List<Loan>> getLoansByUserEmail(

			@RequestParam final String userEmail,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.loanService.getLoansByUserEmail(userEmail));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "loanId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.loanService.getLoansByUserEmail(userEmail, pageable));
		}
	}

	@GetMapping(params = "userEmail")
	public ResponseEntity<List<Object>> getLoansByUserEmailSimplifiedWithBooks(
			@RequestParam final String userEmail
	) {

		return ResponseEntity.ok(this.loanService.getLoansByUserEmailWithBooks(userEmail));
	}

	@GetMapping(params = "userId")
	public ResponseEntity<List<Loan>> getLoansByUserId(

			@RequestParam final Long userId,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.loanService.getLoansByUserId(userId));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "loanId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.loanService.getLoansByUserId(userId, pageable));
		}
	}

}
