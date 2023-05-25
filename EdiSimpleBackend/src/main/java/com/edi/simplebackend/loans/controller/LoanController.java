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

	@PostMapping(params = {"email", "bookId"})
	public ResponseEntity<Loan> addLoan(
			@RequestParam final String email,
			@RequestParam final Long bookId
	) {

		return ResponseEntity.ok(this.loanService.addLoan(email, bookId));
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

	// @GetMapping(params = "email")
	public ResponseEntity<List<Loan>> getLoansByEmail(

			@RequestParam final String email,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.loanService.getLoansByEmail(email));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "loanId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.loanService.getLoansByEmail(email, pageable));
		}
	}

	@GetMapping(params = "email")
	public ResponseEntity<List<Object>> getLoansByEmailSimplifiedWithBooks(
			@RequestParam final String email
	) {

		return ResponseEntity.ok(this.loanService.getLoansByEmailWithBooks(email));
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
