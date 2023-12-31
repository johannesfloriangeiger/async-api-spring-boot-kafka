package org.example;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ResponseConsumer {

    @KafkaListener(topics = "responses")
    public void consume(@Header("id") final String id, @Payload final String text) {
        UppercaseTaskStorage.INSTANCE.update(id, text);
    }
}
