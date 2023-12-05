package com.books.googlebook.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import com.books.googlebook.entity.Books;
import com.books.googlebook.service.BookEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookEventConsumerTest {

    @InjectMocks
    BookEventConsumer bookEventConsumer;
    
    @Mock
    BookEventService bookEventService;

    @Mock
    KafkaTemplate<Integer, String> kafkaTemplate;

    @BeforeEach()
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
    
    @Test
    void onMessageTest() throws InterruptedException, JsonMappingException, JsonProcessingException {
    	String jsonValue = "{\"id\": \"1\"}";
        ConsumerRecord<Integer, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, 1, jsonValue);
        //when(bookEventService.processBookEvent(any)).
        bookEventConsumer.onMessage(consumerRecord);
        verify(bookEventService, times(1)).processBookEvent(consumerRecord);
     
    }
}