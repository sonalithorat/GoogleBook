package com.books.googlebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.books.googlebook.model.Books;


public interface IBookService {

	Books updateBookStock(Books book);
	void updateBook(Books book);
	Optional<List<Books>> getRecentlyViewedBooks();
	List<Books> getAllBooks();
	Optional<Books> getBookById(int id);
	Optional<List<Books>> getBookByTextContainingAuthorOrDescriptionOrName(String keyword);
}
