package com.edi.simplebackend.books.controller;

import com.edi.simplebackend.books.exceptions.BookNotFoundException;
import com.edi.simplebackend.books.repository.BookRepository;
import com.edi.simplebackend.books.model.Book;
import com.edi.simplebackend.books.model.BookData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Component
class BookService {

	@Autowired
	BookRepository bookRepository;

	List<Book> getBooks(final Pageable pageable) {

		return this.bookRepository.findAll(pageable)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooks() {

		return this.bookRepository.findAll()
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	Book getBookById(final Long id) {

		if (this.bookRepository.findById(id).isEmpty()) {
			throw new BookNotFoundException("The book with id: " + id + " does not exist!");
		}

		return this.bookRepository.findById(id).get().transferToBook();
	}

	List<Book> getBooksByTitle(final String title, final Pageable pageable) {

		return this.bookRepository.findByTitleContainingIgnoreCase(title, pageable)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooksByTitle(final String title) {

		return this.bookRepository.findByTitleContainingIgnoreCase(title)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooksByAuthor(final String author, final Pageable pageable) {

		return this.bookRepository.findByAuthorContainingIgnoreCase(author, pageable)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooksByAuthor(final String author) {

		return this.bookRepository.findByAuthorContainingIgnoreCase(author)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooksByIsbn(final String isbn, final Pageable pageable) {

		return this.bookRepository.findByIsbnContainingIgnoreCase(isbn, pageable)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooksByIsbn(final String isbn) {

		return this.bookRepository.findByIsbnContainingIgnoreCase(isbn)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooksByPublisher(final String publisher, final Pageable pageable) {

		return this.bookRepository.findByPublisherContainingIgnoreCase(publisher, pageable)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	List<Book> getBooksByPublisher(final String publisher) {

		return this.bookRepository.findByPublisherContainingIgnoreCase(publisher)
				.stream()
				.map(BookData::transferToBook)
				.collect(Collectors.toList());
	}

	public Book addBook(Book book) {
		bookRepository.save(book.transferToBookData());
		return book;
	}

}
