package org.example;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RequestConsumer {

    private static final Random RANDOM = new Random();

    private final KafkaTemplate<String, String> kafkaTemplate;

    public RequestConsumer(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "requests")
    public void consume(@Header("id") final String id, @Payload final String text) {
        try {
            Thread.sleep(RANDOM.nextLong(10000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final var recordHeader = new RecordHeader("id", id.getBytes());
        final var producerRecord = new ProducerRecord<>("responses", 0, 0L, id, text.toUpperCase(), List.of(recordHeader));
        this.kafkaTemplate.send(producerRecord);
    }
}
