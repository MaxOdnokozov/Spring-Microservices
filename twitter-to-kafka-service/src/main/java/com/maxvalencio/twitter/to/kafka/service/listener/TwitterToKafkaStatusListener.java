package com.maxvalencio.twitter.to.kafka.service.listener;

import com.maxvalencio.config.KafkaConfigData;
import com.maxvalencio.kafka.avro.model.TwitterAvroModel;
import com.maxvalencio.kafka.producer.config.service.KafkaProducer;
import com.maxvalencio.twitter.to.kafka.service.transformer.TwitterStatusToAvroTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Slf4j
@Component
@AllArgsConstructor
public class TwitterToKafkaStatusListener extends StatusAdapter {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, TwitterAvroModel>  kafkaProducer;
    private final TwitterStatusToAvroTransformer transformer;

    @Override
    public void onStatus(Status status) {
        log.info("Received status text {} sending to kafka topic {}", status.getText(), kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = transformer.getTwitterAvroModel(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
