package com.maxvalencio.kafka.producer.config.service.impl;

import com.maxvalencio.kafka.avro.model.TwitterAvroModel;
import com.maxvalencio.kafka.producer.config.service.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

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

        kafkaTemplate.send(topicName, key, message)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error("Error while sending message {} to topic {}", message, topicName, throwable);
                    } else {
                        RecordMetadata metadata = result.getRecordMetadata();
                        log.debug("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                                metadata.topic(),
                                metadata.partition(),
                                metadata.offset(),
                                metadata.timestamp(),
                                System.nanoTime());
                    }
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
