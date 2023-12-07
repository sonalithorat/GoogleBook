package com.books.googlebook.kafka.producer;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.books.googlebook.entity.Books;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookEventProducer {
	private KafkaTemplate<Integer, String> kafkaTemplate;

	private final ObjectMapper objectMapper;

	@Value("${spring.kafka.topic}")
	public String topic;

	public BookEventProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public CompletableFuture<SendResult<Integer, String>> sendLibraryEvent(Books book)
			throws JsonProcessingException {
		int key = book.getId();
		String value = objectMapper.writeValueAsString(book);

		// 1 blocking call- get metadata about the kafka cluster
		// 2. send messgae happens - return completable feature.
		CompletableFuture<SendResult<Integer, String>> completableFture = kafkaTemplate.send(topic, key, value);

		return completableFture.whenComplete((sendResult, throwable) -> {
			if (throwable != null) {
				handleError(key, value, throwable);
			} else {
				handleSuccess(key, value, sendResult);

			}
		});
	}

	

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {
		log.info("message sent successfully for the key: {} and value: {}, partition is :{}, topic: {}", key, value,
				sendResult.getRecordMetadata().partition(), topic);
	}

	private void handleError(Integer key, String value, Throwable ex) {
		log.error("Error sending the message and exception is {}", ex.getMessage(), ex);

	}

}
