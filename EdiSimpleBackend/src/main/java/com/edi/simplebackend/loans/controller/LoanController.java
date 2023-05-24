package com.edi.simplebackend.loans.controller;

import com.edi.simplebackend.loans.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@PostMapping(params = {"userId", "bookId"})
	public ResponseEntity<Loan> addLoan(@RequestParam final Long userId,
										@RequestParam final Long bookId) {

		return ResponseEntity.ok(this.loanService.addLoan(userId, bookId));
	}

	@GetMapping(params = "userId")
	public ResponseEntity<List<Loan>> getLoansByUserId(

			@RequestParam final Long userId,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if(page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.loanService.getLoansByUserId(userId));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "loanId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.loanService.getLoansByUserId(userId, pageable));
		}
	}

	@GetMapping(params = "bookId")
	public ResponseEntity<List<Loan>> getLoansByBookId(

			@RequestParam final Long bookId,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if(page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.loanService.getLoansByBookId(bookId));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "loanId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.loanService.getLoansByBookId(bookId, pageable));
		}
	}

	@GetMapping(params = "username")
	public ResponseEntity<List<Loan>> getLoansByUsername(

			@RequestParam final String username,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if(page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.loanService.getLoansByUsername(username));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "loanId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.loanService.getLoansByUsername(username, pageable));
		}
	}

}
