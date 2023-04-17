package com.maxvalencio.kafka.admin.config.exception;

public class KafkaClientException extends RuntimeException {

    public KafkaClientException(String message) {
        super(message);
    }

    public KafkaClientException(String message, Throwable e) {
        super(message, e);
    }
}
