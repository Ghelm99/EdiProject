package com.edi.simplebackend.books.model;

import com.edi.simplebackend.loans.model.LoanData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class BookData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;
	private String title;
	private String author;
	private String isbn;
	private String publisher;
	@Column(name = "publication_date")
	private String publicationDate;
	@OneToMany(mappedBy = "bookData")
	private List<LoanData> loanDataList;

	public Book transferToBook() {

		final Book book = new Book();

		book.setBookId(this.bookId);
		book.setTitle(this.title);
		book.setAuthor(this.author);
		book.setIsbn(this.isbn);
		book.setPublisher(this.publisher);
		book.setPublicationDate(this.publicationDate);

		return book;
	}

}
