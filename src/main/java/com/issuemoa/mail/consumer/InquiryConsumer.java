package com.issuemoa.mail.consumer;

import com.issuemoa.mail.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InquiryConsumer {
    private static final String TOPIC_NAME = "issuemoa-topic";
    private final EmailService emailService;

    @KafkaListener(topics = TOPIC_NAME, groupId = "group1")
    public void listen(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        log.info("Consumer - Key: " + key + ", Value: " + value);
        emailService.sendSimpleMessage(key, "고객문의가 등록되었습니다.", value);
    }
}
