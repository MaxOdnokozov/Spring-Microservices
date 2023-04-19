package com.maxvalencio.twitter.to.kafka.service;

import com.maxvalencio.twitter.to.kafka.service.init.StreamInitializer;
import com.maxvalencio.twitter.to.kafka.service.runner.StreamRunner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@AllArgsConstructor
@ComponentScan(basePackages = "com.maxvalencio")
@SpringBootApplication
public class TwitterToKafkaServiceApp implements CommandLineRunner {

    private final StreamRunner streamRunner;
    private final StreamInitializer streamInitializer;

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Application starts....");
        streamInitializer.init();
        streamRunner.start();
    }
}

