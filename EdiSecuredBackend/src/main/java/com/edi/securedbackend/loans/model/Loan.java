package com.edi.securedbackend.loans.model;

import com.edi.securedbackend.books.model.Book;
import com.edi.securedbackend.users.model.User;
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
