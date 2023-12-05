package com.books.googlebook.service;


import java.time.LocalDate;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.googlebook.entity.Books;
import com.books.googlebook.repository.BooksRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookEventService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	BooksRepository booksRepository;

	
	public void processBookEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonMappingException, JsonProcessingException {
		Books booksEvent = objectMapper.readValue(consumerRecord.value(), Books.class);
		//validate(booksEvent);
		Optional<Books> event = booksRepository.findById(booksEvent.getId());
		if (!event.isPresent()) {
			throw new IllegalArgumentException("data is not present in db");
		}
		log.info("validated successfully");
		//save(booksEvent);
		booksEvent.setViewDate(LocalDate.now());
		Books bookss= booksRepository.save(booksEvent);
		log.info("successfully saved");
	}
}
