package com.edi.simplebackend.books.controller;

import com.edi.simplebackend.books.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks(

			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if(page == null && size == null && sortBy == null) {
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
		return ResponseEntity.ok(this.bookService.getBookById(id));
	}

	@GetMapping(params = "title")
	public ResponseEntity<List<Book>> getBooksByTitle(

			@RequestParam final String title,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if(page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.bookService.getBooksByTitle(title));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "bookId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.bookService.getBooksByTitle(title, pageable));
		}
	}

	@GetMapping(params = "author")
	public ResponseEntity<List<Book>> getBooksByAuthor(

			@RequestParam final String author,
			@RequestParam(required = false) final Integer page,
			@RequestParam(required = false) final Integer size,
			@RequestParam(required = false) final String sortBy

	) {
		if(page == null && size == null && sortBy == null) {
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
		if(page == null && size == null && sortBy == null) {
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
		if(page == null && size == null && sortBy == null) {
			return ResponseEntity.ok(this.bookService.getBooksByPublisher(publisher));
		} else {

			final int pageNumber = page != null ? page : 0;
			final int pageSize = size != null ? size : 10;
			final String sortField = sortBy != null ? sortBy : "bookId";

			final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
			return ResponseEntity.ok(this.bookService.getBooksByPublisher(publisher, pageable));
		}
	}

}
