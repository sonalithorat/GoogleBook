package com.books.googlebook.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.books.googlebook.entity.Books;
import com.books.googlebook.repository.BooksRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class BookEventServiceTest {
    @InjectMocks
    private BookEventService bookEventService;
	@Mock
    private ObjectMapper objectMapper;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private Logger log;

    @BeforeEach()
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    public void testProcessBookEvent() throws JsonProcessingException {
        // Mock data
    	String jsonValue = "{\"id\": \"1\"}";
        ConsumerRecord<Integer, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, 1, jsonValue);

        // Mock behavior for ObjectMapper
        Books expectedBooks = new Books();
        when(objectMapper.readValue(jsonValue, Books.class)).thenReturn(expectedBooks);

        // Mock behavior for BooksRepository
        when(booksRepository.findById(expectedBooks.getId())).thenReturn(Optional.of(expectedBooks));
        when(booksRepository.save(expectedBooks)).thenReturn(expectedBooks);

        // Call the method
        bookEventService.processBookEvent(consumerRecord);

        // Verify that the ObjectMapper and BooksRepository methods were called with the expected arguments
        verify(objectMapper, times(1)).readValue(jsonValue, Books.class);
        verify(booksRepository, times(1)).findById(expectedBooks.getId());
        verify(booksRepository, times(1)).save(expectedBooks);

    }
    

    @Test
    public void testValidateThrowsException() throws Exception {
        // Mock data
        String jsonValue = "{\"id\": \"2\"}";
        ConsumerRecord<Integer, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, 1, jsonValue);
        Books expectedBooks = new Books();
        when(objectMapper.readValue(jsonValue, Books.class)).thenReturn(expectedBooks);
        when(booksRepository.findById(expectedBooks.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookEventService.processBookEvent(consumerRecord);
        });
    }
}
