package com.edi.simplebackend.books.controller;

import com.edi.simplebackend.books.model.Book;
import java.util.List;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;
	@Autowired
	private HttpServletRequest request;

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks(

			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.bookService.getBooks());
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "bookId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.bookService.getBooks(pageable));
		}
	}

	@GetMapping(params = "id")
	public ResponseEntity<Book> getBookById(@RequestParam final Long id) {
		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(this.bookService.getBookById(id));
	}

	@GetMapping(params = "author")
	public ResponseEntity<List<Book>> getBooksByAuthor(
			@RequestParam final String author,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {

		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.bookService.getBooksByAuthor(author));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "bookId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.bookService.getBooksByAuthor(author, pageable));
		}
	}

	@GetMapping(params = "isbn")
	public ResponseEntity<List<Book>> getBooksByIsbn(

			@RequestParam final String isbn,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {

		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.bookService.getBooksByIsbn(isbn));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "bookId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.bookService.getBooksByIsbn(isbn, pageable));
		}
	}

	@GetMapping(params = "publisher")
	public ResponseEntity<List<Book>> getBooksByPublisher(

			@RequestParam final String publisher,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {

		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.bookService.getBooksByPublisher(publisher));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "bookId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.bookService.getBooksByPublisher(publisher, pageable));
		}
	}

	@GetMapping(params = "title")
	public ResponseEntity<List<Book>> getBooksByTitle(

			@RequestParam final String title,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.bookService.getBooksByTitle(title));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "bookId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.bookService.getBooksByTitle(title, pageable));
		}
	}

	private boolean isUserLoggedIn() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("cookieToken".equals(cookie.getName())) {
					//currently just checking if the names are equal
					return true;
				}
			}
		}
		return false;
	}


}
