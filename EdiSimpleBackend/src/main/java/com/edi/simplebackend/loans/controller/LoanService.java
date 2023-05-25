package com.edi.simplebackend.loans.controller;

import com.edi.simplebackend.books.exceptions.BookNotFoundException;
import com.edi.simplebackend.books.model.Book;
import com.edi.simplebackend.books.model.BookData;
import com.edi.simplebackend.books.repository.BookRepository;
import com.edi.simplebackend.loans.model.Loan;
import com.edi.simplebackend.loans.model.LoanData;
import com.edi.simplebackend.loans.repository.LoanRepository;
import com.edi.simplebackend.users.exceptions.UserNotFoundException;
import com.edi.simplebackend.users.model.UserData;
import com.edi.simplebackend.users.repository.UserRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
class LoanService {

	private final LoanRepository loanRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	Loan addLoan(final String userEmail, final long bookId) {

		Loan loanToBeAdded = new Loan();

		Optional<BookData> bookDataOptional = bookRepository.findById(bookId);
		Optional<UserData> userDataOptional = userRepository.findByEmail(userEmail);

		if (bookDataOptional.isEmpty()) {
			throw new BookNotFoundException("The book: " + bookId + "does not exist!");
		}

		if (userDataOptional.isEmpty()) {
			throw new UserNotFoundException("The user: " + userEmail + "does not exist!");
		}

		loanToBeAdded.setBookId(bookDataOptional.get()
				.getBookId());
		loanToBeAdded.setUserId(userDataOptional.get()
				.getUserId());

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

		LoanData loanDataToBeAdded = loanToBeAdded.transferToLoanData(
				bookDataOptional.get()
						.transferToBook(),
				userDataOptional.get()
						.transferToUser()
		);

		this.loanRepository.save(loanDataToBeAdded);
		return loanDataToBeAdded.transferToLoan();
	}

	void deleteLoan(final Long loanId) {

		this.loanRepository.deleteById(loanId);
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

	List<Loan> getLoansByUserEmail(final String userEmail, final Pageable pageable) {

		return this.loanRepository.findByUserData_Email(userEmail, pageable)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

	List<Loan> getLoansByUserEmail(final String userEmail) {

		return this.loanRepository.findByUserData_Email(userEmail)
				.stream()
				.map(LoanData::transferToLoan)
				.collect(Collectors.toList());
	}

	List<Object> getLoansByUserEmailWithBooks(final String email) {

		List<Object> output = new ArrayList<>();
		List<Loan> loanList = this.loanRepository.findByUserData_Email(email)
				.stream()
				.map(LoanData::transferToLoan)
				.toList();

		loanList.forEach(loan -> {
			Book loanBook;
			Optional<BookData> loanBookDataOptional = this.bookRepository.findById(loan.getBookId());
			Map<String, Object> loanWithBook = new HashMap<>();

			if (loanBookDataOptional.isPresent()) {
				loanBook = loanBookDataOptional.get()
						.transferToBook();
				loanWithBook.put("loan", loan);
				loanWithBook.put("book", loanBook);
				output.add(loanWithBook);
			}
		});
		return output;
	}

	List<Object> getLoansByUserEmailWithBooks(final String email, Pageable pageable) {

		List<Object> output = new ArrayList<>();
		List<Loan> loanList = this.loanRepository.findByUserData_Email(email, pageable)
				.stream()
				.map(LoanData::transferToLoan)
				.toList();

		loanList.forEach(loan -> {
			Book loanBook;
			Optional<BookData> loanBookDataOptional = this.bookRepository.findById(loan.getBookId());
			Map<String, Object> loanWithBook = new HashMap<>();

			if (loanBookDataOptional.isPresent()) {
				loanBook = loanBookDataOptional.get()
						.transferToBook();
				loanWithBook.put("loan", loan);
				loanWithBook.put("book", loanBook);
				output.add(loanWithBook);
			}
		});
		return output;
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

}
