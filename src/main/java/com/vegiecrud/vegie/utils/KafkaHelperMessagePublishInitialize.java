package com.vegiecrud.vegie.utils;

import com.vegiecrud.vegie.dto.KafkaHelperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.kafka.clients.producer.RecordMetadata;

@Component
public class KafkaHelperMessagePublishInitialize {

    private static final Logger log = LogManager.getLogger(KafkaHelperMessagePublishInitialize.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaHelperMessagePublishInitialize(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public KafkaHelperResponse<String> publish(String topic, String message) {
        KafkaHelperResponse<String> response = new KafkaHelperResponse<>();

        try {
            // Mengirim pesan secara sinkron
            RecordMetadata metadata = kafkaTemplate.send(topic, message).get().getRecordMetadata();
            response.setStatus(0);
            response.setInfo("Message sent successfully to Kafka topic: " + metadata.topic());
        } catch (Exception e) {
            response.setStatus(-1);
            response.setInfo("Failed to send message to Kafka topic: " + topic + ". Exception: " + e.getMessage());
            log.error("Exception during sending message", e);
        }

        return response;
    }
}
