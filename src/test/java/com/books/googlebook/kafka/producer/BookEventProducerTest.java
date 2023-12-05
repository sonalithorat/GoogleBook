package com.books.googlebook.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.springframework.util.concurrent.ListenableFuture;
import com.books.googlebook.entity.Books;
import com.books.googlebook.model.BooksDto;
import com.books.googlebook.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class BookEventProducerTest {

	@Value("${spring.kafka.topic}")
	public String topic;

	@InjectMocks
	BookEventProducer bookEventProducer;

	@Mock
	ObjectMapper objectMapper;

	@Mock
	BookService bookService;

	@Mock
	KafkaTemplate<Integer, String> kafkaTemplate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	// @Test
	void sendLibraryEvent() throws JsonProcessingException {
		BooksDto dto = new BooksDto(1, "Core Java", "An Integrated Approach", "Nageswara Rao", 300, "technical", 660, 0,
				"The book is written in such a way that", "Java", false);
		Books book = bookService.mapTOBook(new Books(), dto);
		var key = book.getId();
		var value = objectMapper.writeValueAsString(book);
		// when(objectMapper.writeValueAsString(book)).thenReturn(value);
	}
	
	//@Test
    void testSendLibraryEvent() throws JsonProcessingException, ExecutionException, InterruptedException {
        // Arrange
        Books book = new Books();
        book.setId(123);
        String jsonString = "{\"id\":123,\"title\":\"Book Title\"}";  // Use your actual serialization logic

        CompletableFuture<SendResult<Integer, String>> future = new CompletableFuture<SendResult<Integer,String>>();
		RecordMetadata recordMetadata = new RecordMetadata(null, 0, 0, 0L, 0L, 0, 0);

		SendResult<Integer, String> sendResult = new SendResult<>(new ProducerRecord<>("topic", 123, jsonString), recordMetadata);
		//future.set(sendResult);
        // Mock KafkaTemplate send method
        when(kafkaTemplate.send(anyString(), anyInt(), anyString())).thenReturn(future);

		 CompletableFuture<SendResult<Integer, String>> completableFuture = bookEventProducer.sendLibraryEvent(book);

        future.get();
        //completableFuture.get(); // This will throw an exception if the CompletableFuture completes exceptionally
        // Add further assertions as needed
        verify(kafkaTemplate, times(1)).send("topic", 123, jsonString);
        // Add more verifications or assertions based on your requirements
    }
    
    //@Test
    void testSendLibraryEvents() throws JsonProcessingException {
        // Arrange
        Books book = new Books();
        book.setId(123);
		var completableFture =mock(CompletableFuture.class);
        String jsonString = "{\"id\":123,\"title\":\"Book Title\"}";  // Use your actual serialization logic

        CompletableFuture<SendResult<Integer, String>> completableFuture = mock(CompletableFuture.class);
        when(kafkaTemplate.send(anyString(), anyInt(), anyString())).thenReturn(completableFuture);
		SendResult<Integer, String> sendResult = mock(SendResult.class);
		//CompletableFuture<SendResult<Integer, String>> completableFuture = mock(CompletableFuture.class);
        completableFuture = bookEventProducer.sendLibraryEvent(book);

        // Act
        // Simulate completion with success

        completableFuture.complete(sendResult);

        // Assert
        verify(kafkaTemplate, times(1)).send(anyString(), anyInt(), anyString());
     
       
    }
}