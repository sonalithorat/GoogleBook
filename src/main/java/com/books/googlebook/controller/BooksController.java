package com.books.googlebook.controller;

import java.util.List;

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

import com.books.googlebook.model.BooksDto;
import com.books.googlebook.repository.BooksRepository;
import com.books.googlebook.service.BookService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BooksController {

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	BookService bookService;

	@GetMapping("/hi")
	public String hello() {
		return "Hello";
	}
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<BooksDto>> fetchAccountDetails() {
		List<BooksDto> books =bookService.getAllBooks();
		return ResponseEntity.status(HttpStatus.OK).body(books);
	}
	@GetMapping("/getBook/id/{id}")
	public ResponseEntity<BooksDto> searchBookById(@PathVariable int id) {
		BooksDto book = bookService.getBookById(id);
		return  ResponseEntity.status(HttpStatus.OK).body(book);
	}
	@GetMapping("/getBook/{keyword}")
	public ResponseEntity<List<BooksDto>> getBookByName(@PathVariable String keyword) {
		List<BooksDto> books =  bookService.getBookByTextContainingAuthorOrDescriptionOrName(keyword);
		return ResponseEntity.status(HttpStatus.OK).body(books);
	}
	@GetMapping("/getBook/recentlyVisited")
	public ResponseEntity<List<BooksDto>> getRecentlyVisitedBook() {
		List<BooksDto> books = bookService.getRecentlyViewedBooks();
		return ResponseEntity.status(HttpStatus.OK).body(books);
	}
	
	@GetMapping("/getBook/cart")
	public ResponseEntity<List<BooksDto>> getCartItems() {
		List<BooksDto> books = bookService.getCartItems();
		return ResponseEntity.status(HttpStatus.OK).body(books);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BooksDto> update(@RequestBody BooksDto book) {
		// Optional<List<Books>> books = booksRepository.g(name);
		BooksDto updatedBook = bookService.updateBook(book);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
	}
}
