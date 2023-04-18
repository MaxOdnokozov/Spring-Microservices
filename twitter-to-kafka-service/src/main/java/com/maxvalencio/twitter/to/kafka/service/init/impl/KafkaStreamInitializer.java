package com.maxvalencio.twitter.to.kafka.service.init.impl;

import com.maxvalencio.config.KafkaConfigData;
import com.maxvalencio.kafka.admin.config.client.KafkaAdminClient;
import com.maxvalencio.twitter.to.kafka.service.init.StreamInitializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaStreamInitializer implements StreamInitializer {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate());
    }
}
