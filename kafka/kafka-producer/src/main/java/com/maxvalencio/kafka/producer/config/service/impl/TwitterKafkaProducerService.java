package com.maxvalencio.kafka.producer.config.service.impl;

import com.maxvalencio.kafka.avro.model.TwitterAvroModel;
import com.maxvalencio.kafka.producer.config.service.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterKafkaProducerService implements KafkaProducer<Long, TwitterAvroModel> {

    private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        log.info("Sending message='{}' to topic='{}'", message, topicName);

        CompletableFuture<SendResult<Long, TwitterAvroModel>> future = kafkaTemplate.send(topicName, key, message);

        future.thenAccept(result -> {
            RecordMetadata recordMetadata = result.getRecordMetadata();
            log.debug("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.timestamp(),
                    System.nanoTime());

        }).exceptionally(ex -> {
            log.error("Error while sending message {} to topic {}", message.toString(), topicName, ex);
            return null;
        });

        log.info("Message is sent");
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
