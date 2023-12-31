package org.example;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UppercaseTaskService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UppercaseTaskService(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String create(final String text) {
        final String id = UppercaseTaskStorage.INSTANCE.create(text);

        final var recordHeader = new RecordHeader("id", id.getBytes());
        final var producerRecord = new ProducerRecord<>("requests", 0, 0L, id, text, List.of(recordHeader));
        this.kafkaTemplate.send(producerRecord);

        return id;
    }

    public Optional<UppercaseTask> read(final String id) {
        return UppercaseTaskStorage.INSTANCE.read(id);
    }
}