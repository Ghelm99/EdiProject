package com.edi.simplebackend.books.repository;

import com.edi.simplebackend.books.model.BookData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookData, Long> {

	@Override
	Page<BookData> findAll(Pageable pageable);

	Page<BookData> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

	List<BookData> findByAuthorContainingIgnoreCase(String author);

	@Override
	Optional<BookData> findById(Long id);

	Page<BookData> findByIsbnContainingIgnoreCase(String isbn, Pageable pageable);

	List<BookData> findByIsbnContainingIgnoreCase(String isbn);

	Page<BookData> findByPublisherContainingIgnoreCase(String publisher, Pageable pageable);

	List<BookData> findByPublisherContainingIgnoreCase(String publisher);

	Page<BookData> findByTitleContainingIgnoreCase(String title, Pageable pageable);

	List<BookData> findByTitleContainingIgnoreCase(String title);

}
