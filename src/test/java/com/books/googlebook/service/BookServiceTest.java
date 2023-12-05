package com.books.googlebook.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.books.googlebook.kafka.producer.BookEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.books.googlebook.entity.Books;
import com.books.googlebook.model.BooksDto;
import com.books.googlebook.repository.BooksRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookServiceTest {

	@InjectMocks
	BookService bookService;

	@Mock
	BooksRepository booksRepository;

	@Mock
	BookEventProducer bookEventProducer;

//	@BeforeEach()
//	public void setup() {
//		MockitoAnnotations.openMocks(this);
//	}
	@Test
	void updateBook() {
		BooksDto dto = new BooksDto(1, "Core Java", "An Integrated Approach", "Nageswara Rao", 300, "technical", 660, 0,
				"The book is written in such a way that", "Java", false);
		Books book = bookService.mapTOBook(new Books(), dto);
		when(booksRepository.save(any())).thenReturn(book);
		assertEquals(bookService.updateBook(dto).getName(), dto.getName());
	}

	@Test
	void getRecentlyViewedBooks() {
		when(booksRepository.findAll()).thenReturn(getDummyData());
		assertEquals(2, bookService.getRecentlyViewedBooks().size());
	}

	@Test
	void getRecentlyViewedBooks_None() {
		List<BooksDto> list = Stream.of(
				new BooksDto(1, "Core Java", "An Integrated Approach", "Nageswara Rao", 300, "technical", 660, 0,
						"The book is written in such a way that", "Java", false),
				new BooksDto(2, ".Net", "An Integrated Approach", "Nageswara Rao", 55, "technical", 333, 0,
						"The book is written in such a way that", "net", false))
				.collect(Collectors.toList());

		List<Books> data = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Books dto = bookService.mapTOBook(new Books(), list.get(i));
			dto.setViewDate(LocalDate.of(2023, 5, 15));
			data.add(dto);
		}
		when(booksRepository.findAll()).thenReturn(data);
		assertEquals(0, bookService.getRecentlyViewedBooks().size());
	}

	@Test
	void getAllBooks() {
		when(booksRepository.findAll()).thenReturn(getDummyData());
		assertEquals(2, bookService.getAllBooks().size());
	}

	@Test
	void getBookById() throws JsonProcessingException {
		BooksDto dto = new BooksDto(1, "Core Java", "An Integrated Approach", "Nageswara Rao", 300, "technical", 660, 0,
				"The book is written in such a way that", "Java", false);
		Books book = bookService.mapTOBook(new Books(), dto);
		Optional<Books> op = Optional.of(book);
		when(booksRepository.findById(1)).thenReturn(op);
		when(bookEventProducer.sendLibraryEvent(any())).thenReturn(any());
		assertTrue(bookService.getBookById(1).getName().equals(dto.getName()));
	}

	@Test
	void getBookByTextContainingAuthorOrDescriptionOrName() {

		List<Books> op = getDummyData();
		when(booksRepository.findAll()).thenReturn(op);

		assertEquals(bookService.getBookByTextContainingAuthorOrDescriptionOrName("Java").size(), 1);
	}

	private List<Books> getDummyData() {
		List<BooksDto> list = Stream.of(
				new BooksDto(1, "Core Java", "An Integrated Approach", "Nageswara Rao", 300, "technical", 660, 0,
						"The book is written in such a way that", "Java", false),
				new BooksDto(2, ".Net", "An Integrated Approach", "Nageswara Rao", 55, "technical", 333, 0,
						"The book is written in such a way that", "net", false))
				.collect(Collectors.toList());

		List<Books> data = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Books dto = bookService.mapTOBook(new Books(), list.get(i));
			dto.setViewDate(LocalDate.now());
			data.add(dto);
		}
		return data;
	}
}