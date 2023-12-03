package com.books.googlebook.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.googlebook.model.Books;
import com.books.googlebook.repository.BooksRepository;
import com.books.googlebook.service.IBookService;

@Service
public class BookServiceImpl implements IBookService{

	@Autowired
	BooksRepository bookRepository; 
	
	@Override
	public Books updateBookStock(Books book) {
		book.setStock(book.getStock());
		updateBook(book);
		return book;
	}

	@Override
	public void updateBook(Books book) {
		bookRepository.save(book);
		
	}

	@Override
	public Optional<List<Books>> getRecentlyViewedBooks() {
		List<Books> books = bookRepository.findAll();
		List<Books> data =  books.stream().filter((a)->{
			System.out.println("book:"+a);
			if(a.getViewDate().equals(LocalDate.now())) {
				return true;
			}else {
				return false;
			}
		}).collect(Collectors.toList());
		
		return Optional.ofNullable(data); 
	}

	@Override
	public List<Books> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Books> getBookById(int id) {
		return bookRepository.findById(id);
	}

	@Override
	public Optional<List<Books>> getBookByTextContainingAuthorOrDescriptionOrName(String keyword) {
		Optional<List<Books>> books = null;
		if (keyword != null) {
			books = bookRepository.findByAuthorContainingOrByNameContainingOrByDescriptionContaining(keyword);

		}
		return books;
		
	}

	

}
