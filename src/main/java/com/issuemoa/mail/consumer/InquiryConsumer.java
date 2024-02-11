package com.issuemoa.mail.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InquiryConsumer {
    private static final String TOPIC_NAME = "issuemoa-topic";

    @KafkaListener(topics = TOPIC_NAME, groupId = "group1")
    public void consumeMessage(String message) {
        System.out.println("Consumed message: " + message);
    }
}
