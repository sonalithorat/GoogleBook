package com.books.googlebook.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.books.googlebook.model.Books;
import com.books.googlebook.repository.BooksRepository;
import com.books.googlebook.service.IBookService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BooksController {

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	IBookService iBookService;

	@GetMapping("/hi")
	public String hello() {
		return "Hello";
	}

	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Books>> fetchAccountDetails() {
		List<Books> books =iBookService.getAllBooks();
		return ResponseEntity.status(HttpStatus.OK).body(books);

	}

	@GetMapping("/getBook/id/{id}")
	public ResponseEntity<Optional<Books>> searchBookById(@PathVariable int id) {
		Optional<Books> book = iBookService.getBookById(id);
		return  ResponseEntity.status(HttpStatus.OK).body(book);
	}

	@GetMapping("/getBook/{keyword}")
	public ResponseEntity<Optional<List<Books>>> getBookByName(@PathVariable String keyword) {
		Optional<List<Books>> books =  iBookService.getBookByTextContainingAuthorOrDescriptionOrName(keyword);
		return ResponseEntity.status(HttpStatus.OK).body(books);
	}

	@GetMapping("/getBook/recentlyVisited")
	public ResponseEntity<Optional<List<Books>>> getRecentlyVisitedBook() {
		Optional<List<Books>> books = iBookService.getRecentlyViewedBooks();
		return ResponseEntity.status(HttpStatus.OK).body(books);
		
	}

	@GetMapping("/test/{name}")
	public Optional<List<Books>> sendMessage(@PathVariable String name) {
		Optional<List<Books>> books = booksRepository
				.findByAuthorContainingOrByNameContainingOrByDescriptionContaining(name);
		return books;
	}

	@PutMapping("/update")
	public ResponseEntity<Books> update(@RequestBody Books book) {
		// Optional<List<Books>> books = booksRepository.g(name);
		Books updatedBook = iBookService.updateBookStock(book);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
	}

}
