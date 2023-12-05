package com.books.googlebook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.books.googlebook.entity.Books;
import com.books.googlebook.kafka.producer.BookEventProducer;
import com.books.googlebook.model.BooksDto;
import com.books.googlebook.repository.BooksRepository;
import com.books.googlebook.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BooksController.class)
class BooksControllerTest {
    @MockBean
    BookService bookService;

    @MockBean
    BooksRepository booksRepository;
    
    @MockBean
	BookEventProducer bookEventProducer;
    
    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloTest() throws Exception {
        mockMvc.perform(get("/api/hi")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void fetchAccountDetails() throws Exception {
        List<BooksDto> books = new ArrayList<>();
       // Books book = new Books(1, "Java", "An Integrated Approach","Nageswara Rao",300,"technical")
        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        mockMvc.perform(get("/api/getAllBooks")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void searchBookById() throws Exception {
    	CompletableFuture<SendResult<Integer, String>> result = null;
    	Books book = new Books();
    	 BooksDto books = new BooksDto(1, "Core Java", "An Integrated Approach", "Nageswara Rao", 300,
    			 "technical",660, 0, "The book is written in such a way that", "Java", false);
         // Books book = new Books(1, "Java", "An Integrated Approach","Nageswara Rao",300,"technical")
          Mockito.when(bookService.getBookById(1)).thenReturn(books);
          //Mockito.when(bookEventProducer.sendLibraryEvent(null)).thenReturn(result);
          mockMvc.perform(get("/api/getBook/id/{id}", 1)
                  .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getBookByName() throws Exception {
        List<BooksDto> dto = new ArrayList<BooksDto>();
        Mockito.when(bookService.getBookByTextContainingAuthorOrDescriptionOrName("Java")).thenReturn(dto);
        mockMvc.perform(get("/api/getBook/{keyword}", "Java")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    void getRecentlyVisitedBook() throws Exception {
    	 List<BooksDto> books = new ArrayList<>();
         // Books book = new Books(1, "Java", "An Integrated Approach","Nageswara Rao",300,"technical")
          Mockito.when(bookService.getRecentlyViewedBooks()).thenReturn(books);
          mockMvc.perform(get("/api/getBook/recentlyVisited")
                  .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    void update() throws Exception {
    	Books book = new Books();
   	  BooksDto books = new BooksDto(1, "Core Java", "An Integrated Approach", "Nageswara Rao", 300,
   			 "technical",660, 0, "The book is written in such a way that", "Java", false);
        // Books book = new Books(1, "Java", "An Integrated Approach","Nageswara Rao",300,"technical")
         Mockito.when(bookService.updateBook(books)).thenReturn(books);
         //Mockito.when(bookEventProducer.sendLibraryEvent(null)).thenReturn(result);
         mockMvc.perform(put("/api/update", 1)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(new ObjectMapper().writeValueAsString(books))).andExpect(status().isOk());
    }
}