package com.edi.simplebackend.loans.controller;

import com.edi.simplebackend.loans.repository.LoanRepository;
import com.edi.simplebackend.users.exceptions.UserNotFoundException;
import com.edi.simplebackend.users.model.UserData;
import com.edi.simplebackend.users.repository.UserRepository;
import com.edi.simplebackend.books.exceptions.BookNotFoundException;
import com.edi.simplebackend.books.model.BookData;
import com.edi.simplebackend.books.repository.BookRepository;
import com.edi.simplebackend.loans.model.Loan;
import com.edi.simplebackend.loans.model.LoanData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Component
class LoanService {

	@Autowired
	LoanRepository loanRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;

	Loan addLoan(final Long userId, final long bookId) {

		Loan loanToBeAdded = new Loan();

		Optional<BookData> bookDataOptional = bookRepository.findById(bookId);
		Optional<UserData> userDataOptional = userRepository.findById(userId);

		if (bookDataOptional.isEmpty()) {
			throw new BookNotFoundException("The book: " + bookId + "does not exist!");
		}

		if(userDataOptional.isEmpty()) {
			throw new UserNotFoundException("The user: " + userId + "does not exist!");
		}

		loanToBeAdded.setBookId(bookDataOptional.get().getBookId());
		loanToBeAdded.setUserId(userDataOptional.get().getUserId());

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		loanToBeAdded.setStartingDate(dateString);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		Date oneWeekLater = calendar.getTime();
		String oneWeekLaterString = dateFormat.format(oneWeekLater);
		loanToBeAdded.setReturnDate(oneWeekLaterString);

		LoanData loanDataToBeAdded = loanToBeAdded.transferToLoanData(bookDataOptional.get().transferToBook(), userDataOptional.get().transferToUser());

		this.loanRepository.save(loanDataToBeAdded);
		return loanDataToBeAdded.transferToLoan();
	}

	List<Loan> getLoansByUserId(final Long userId, final Pageable pageable) {

		return this.loanRepository.findByUserData_UserId(userId, pageable)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

	List<Loan> getLoansByUserId(final Long userId) {

		return this.loanRepository.findByUserData_UserId(userId)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

	List<Loan> getLoansByBookId(final Long bookId, final Pageable pageable) {

		return this.loanRepository.findByBookData_BookId(bookId, pageable)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

	List<Loan> getLoansByBookId(final Long bookId) {

		return this.loanRepository.findByBookData_BookId(bookId)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

	List<Loan> getLoansByUsername(final String username, final Pageable pageable) {

		return this.loanRepository.findByUserData_Username(username, pageable)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

	List<Loan> getLoansByUsername(final String username) {

		return this.loanRepository.findByUserData_Username(username)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

}
