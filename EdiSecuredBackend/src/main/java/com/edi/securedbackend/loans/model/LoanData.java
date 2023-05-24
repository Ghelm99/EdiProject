package com.edi.securedbackend.loans.model;

import com.edi.securedbackend.users.model.UserData;
import com.edi.securedbackend.books.model.BookData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class LoanData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserData userData;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private BookData bookData;
	
	private String startingDate;
	private String returnDate;

	public Loan transferToLoan() {

		final Loan loan = new Loan();

		loan.setLoanId(this.loanId);
		loan.setUserId(this.userData.getUserId());
		loan.setBookId(this.bookData.getBookId());
		loan.setStartingDate(this.startingDate);
		loan.setReturnDate(this.returnDate);

		return loan;
	}

}
