package com.edi.simplebackend.books.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Book {

	private Long bookId;
	private String title;
	private String author;
	private String isbn;
	private String publisher;
	private String publicationDate;

	public BookData transferToBookData() {

		final BookData bookData = new BookData();

		bookData.setBookId(this.bookId);
		bookData.setTitle(this.title);
		bookData.setAuthor(this.author);
		bookData.setIsbn(this.isbn);
		bookData.setPublisher(this.publisher);
		bookData.setPublicationDate(publicationDate);
		bookData.setLoanDataList(new ArrayList<>());

		return bookData;
	}

}
