package com.edi.simplebackend.loans.controller;

import com.edi.simplebackend.loans.model.Loan;
import java.util.List;

import com.edi.simplebackend.login.UserSessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loans")
public class LoanController {

	private final LoanService loanService;
	private final UserSessionData userSessionData;

	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@PostMapping(params = {"email", "bookId"})
	public ResponseEntity<Loan> addLoan(
			@RequestParam final String email,
			@RequestParam final Long bookId,
			@RequestHeader(value = "Cookie", required = false) String cookie
	) {
		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(this.loanService.addLoan(email, bookId));
	}

	@DeleteMapping(params = "loanId")
	public ResponseEntity<String> deleteLoan(
			@RequestParam final Long loanId,
			@RequestHeader(value = "Cookie", required = false) String cookie
	) {

		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		loanService.deleteLoan(loanId);
		return ResponseEntity.ok("Loan " + loanId + " deleted!");
	}

	@GetMapping(params = "bookId")
	public ResponseEntity<List<Loan>> getLoansByBookId(

			@RequestParam final Long bookId,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy,
			@RequestHeader(value = "Cookie", required = false) String cookie

	) {
		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

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
			@RequestParam(required = false) final String sortBy,
			@RequestHeader(value = "Cookie", required = false) String cookie

	) {
		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

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
			@RequestParam final String email,
			@RequestHeader(value = "Cookie", required = false) String cookie
	) {

		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(this.loanService.getLoansByEmailWithBooks(email));
	}

	@GetMapping(params = "userId")
	public ResponseEntity<List<Loan>> getLoansByUserId(

			@RequestParam final Long userId,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy,
			@RequestHeader(value = "Cookie", required = false) String cookie

	) {
		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

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

	private boolean isUserLoggedIn(String cookie) {
		if (cookie != null && cookie.contains("cookieToken")) {
			// Extract the cookie token from the "cookie" string
			String[] cookieParts = cookie.split(";");
			for (String cookiePart : cookieParts) {
				if (cookiePart.trim().startsWith("cookieToken=")) {
					String token = cookiePart.trim().substring("cookieToken=".length());
					return token.equals(userSessionData.getCookieToken());
				}
			}
		}
		return false;
	}




}
