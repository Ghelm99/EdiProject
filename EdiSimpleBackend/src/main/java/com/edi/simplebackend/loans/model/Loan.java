package com.edi.simplebackend.loans.model;

import com.edi.simplebackend.users.model.User;
import com.edi.simplebackend.books.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Loan {

	private Long loanId;
	private Long userId;
	private Long bookId;
	private String startingDate;
	private String returnDate;

	public LoanData transferToLoanData(final Book book, final User user) {

		final LoanData loanData = new LoanData();

		loanData.setLoanId(this.loanId);
		loanData.setUserData(user.transferToUserData());
		loanData.setBookData(book.transferToBookData());
		loanData.setStartingDate(this.startingDate);
		loanData.setReturnDate(this.returnDate);

		return loanData;
	}

}
