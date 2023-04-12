package com.maxvalencio.twitter.to.kafka.service;

import com.maxvalencio.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.maxvalencio.twitter.to.kafka.service.runner.StreamRunner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
public class TwitterToKafkaServiceApp implements CommandLineRunner {

    private final TwitterToKafkaServiceConfigData configData;
    private final StreamRunner streamRunner;

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Application starts....");
        log.info("Twitter keywords: {}", configData.getTwitterKeywords().toString());
        log.info(configData.getWelcomeMessage());
        streamRunner.start();
    }
}
