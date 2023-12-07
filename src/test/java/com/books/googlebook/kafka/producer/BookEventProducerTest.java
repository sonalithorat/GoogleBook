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
import static org.mockito.ArgumentMatchers.*;
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
				"The book is written in such a way that", "Java", false, true);
		Books book = bookService.mapTOBook(new Books(), dto);
		var key = book.getId();
		var value = objectMapper.writeValueAsString(book);
		// when(objectMapper.writeValueAsString(book)).thenReturn(value);
	}

	//@Test
	void testSendLibraryEventas() throws JsonProcessingException {
		// Given
		Books book = new Books();
		book.setId(123);

		String value = objectMapper.writeValueAsString(book);
		when(objectMapper.writeValueAsString(book)).thenReturn(value);
		// String jsonString = "{\"id\":123,\"title\":\"Book Title\"}"; // Use your
		// actual serialization logic

		// Mock the send method to return a CompletableFuture
		CompletableFuture<SendResult<Integer, String>> completableFutureMock = mock(CompletableFuture.class);
		when(kafkaTemplate.send(eq("topic"), eq(123), eq(value))).thenReturn(completableFutureMock);

		// When
		CompletableFuture<SendResult<Integer, String>> completableFutureActual = bookEventProducer
				.sendLibraryEvent(book);

		// Complete the CompletableFuture with a mocked SendResult
		SendResult<Integer, String> sendResult = mock(SendResult.class);
		completableFutureMock.complete(sendResult);

		// Then
		verify(kafkaTemplate, times(1)).send(anyString(), anyInt(), anyString());

		// Additional verification
		assertNotNull(completableFutureActual);
		assertEquals(completableFutureMock, completableFutureActual);
		// Add more assertions based on your specific use case
	}

	//@Test
	void testSendLibraryEvents() throws JsonProcessingException {

		Books book = new Books();
		book.setId(123);
		String jsonString = "{\"id\":123,\"title\":\"Book Title\"}"; // Use your actual serialization logic

		CompletableFuture<SendResult<Integer, String>> completableFuture = mock(CompletableFuture.class);

		when(kafkaTemplate.send("topic", 123, jsonString)).thenReturn(completableFuture);
		SendResult<Integer, String> sendResult = mock(SendResult.class);

		completableFuture = bookEventProducer.sendLibraryEvent(book);
		completableFuture.complete(sendResult);
		verify(kafkaTemplate, times(1)).send(anyString(), anyInt(), anyString());

	}
}