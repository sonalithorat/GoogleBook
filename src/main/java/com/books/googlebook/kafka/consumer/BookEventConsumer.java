package com.books.googlebook.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.books.googlebook.service.BookEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookEventConsumer {
	
	@Autowired
	BookEventService bookEventService;

	@KafkaListener(topics = {"book-events"})
	public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		log.info("consumed {}",consumerRecord);
		bookEventService.processBookEvent(consumerRecord);
	}
}