package com.books.googlebook.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.googlebook.entity.Books;
import com.books.googlebook.kafka.producer.BookEventProducer;
import com.books.googlebook.model.BooksDto;
import com.books.googlebook.repository.BooksRepository;

@Service
public class BookService {

	@Autowired
	BooksRepository bookRepository;

	@Autowired
	BookEventProducer bookEventProducer;

	public BooksDto updateBook(BooksDto booksDto) {
		Books book = mapTOBook(new Books(), booksDto);
		bookRepository.save(book);
		return booksDto;
	}
	
	public List<BooksDto> getRecentlyViewedBooks() {
		List<Books> books = bookRepository.findAll();
		List<Books> data = books.stream().filter((a) -> {
			System.out.println("book:" + a);
			if (a.getViewDate().equals(LocalDate.now())) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());

		List<BooksDto> booksDto = new ArrayList<BooksDto>();
		for (int i = 0; i < data.size(); i++) {
			BooksDto dto = mapTOBookDto(data.get(i), new BooksDto());
			booksDto.add(dto);
		}

		return booksDto;
	}

	public List<BooksDto> getCartItems() {
		List<Books> books = bookRepository.findAll();
		List<Books> data = books.stream().filter((a) -> {
			System.out.println("book:" + a);
			if (a.isAddedToCart()) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());

		List<BooksDto> booksDto = new ArrayList<BooksDto>();
		for (int i = 0; i < data.size(); i++) {
			BooksDto dto = mapTOBookDto(data.get(i), new BooksDto());
			booksDto.add(dto);
		}

		return booksDto;
	}
	public List<BooksDto> getAllBooks() {

		List<Books> data = bookRepository.findAll();

		List<BooksDto> booksDto = new ArrayList<BooksDto>();
		for (int i = 0; i < data.size(); i++) {
			BooksDto dto = mapTOBookDto(data.get(i), new BooksDto());
			booksDto.add(dto);
		}

		return booksDto;
	}

	public BooksDto getBookById(int id) {
		BooksDto dto = null;
		Optional<Books> books = bookRepository.findById(id);
		try {
			bookEventProducer.sendLibraryEvent(books.get());
			dto = mapTOBookDto(books.get(), new BooksDto());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error sending the message");
		}

		return dto;
	}

	public List<BooksDto> getBookByTextContainingAuthorOrDescriptionOrName(String keyword) {
		List<Books> books = null;

		books = bookRepository.findAll();

		List<BooksDto> booksDto = new ArrayList<BooksDto>();
		Predicate<Books> nameContains = e -> e.getName().toLowerCase().contains(keyword.toLowerCase());
		Predicate<Books> authorContains = e -> e.getAuthor().toLowerCase().contains(keyword.toLowerCase());
		Predicate<Books> categoryContains = e -> e.getCategory().toLowerCase().contains(keyword.toLowerCase());

		// Predicate<BooksDto> predicate = nameContains.
		booksDto = books.stream().filter(nameContains.or(authorContains).or(categoryContains))
				.map(e -> mapTOBookDto(e, new BooksDto())).collect(Collectors.toList());
		return booksDto;

	}

	public BooksDto mapTOBookDto(Books book, BooksDto booksDto) {
		booksDto.setId(book.getId());
		booksDto.setAuthor(book.getAuthor());
		booksDto.setAbout(book.getAbout());
		booksDto.setBorrowed(book.isBorrowed());
		booksDto.setCategory(book.getCategory());
		booksDto.setDescription(book.getDescription());
		booksDto.setName(book.getName());
		booksDto.setPageCount(book.getPageCount());
		booksDto.setPrice(book.getPrice());
		booksDto.setPublisher(book.getPublisher());
		booksDto.setStock(book.getStock());
		booksDto.setViewDate(book.getViewDate());
		booksDto.setAddedToCart(book.isAddedToCart());
		return booksDto;
	}

	public Books mapTOBook(Books book, BooksDto booksDto) {
		book.setId(booksDto.getId());
		book.setAbout(booksDto.getAbout());
		book.setAuthor(booksDto.getAuthor());
		book.setBorrowed(booksDto.isBorrowed());
		book.setCategory(booksDto.getCategory());
		book.setDescription(booksDto.getDescription());
		book.setName(booksDto.getName());
		book.setPageCount(booksDto.getPageCount());
		book.setPrice(booksDto.getPrice());
		book.setPublisher(booksDto.getPublisher());
		book.setStock(booksDto.getStock());
		book.setViewDate(booksDto.getViewDate());
		book.setAddedToCart(booksDto.isAddedToCart());
		return book;
	}

}
