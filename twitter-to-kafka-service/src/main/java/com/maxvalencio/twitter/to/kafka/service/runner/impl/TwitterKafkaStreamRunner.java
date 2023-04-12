package com.maxvalencio.twitter.to.kafka.service.runner.impl;

import com.maxvalencio.config.TwitterToKafkaServiceConfigData;
import com.maxvalencio.twitter.to.kafka.service.listener.TwitterToKafkaStatusListener;
import com.maxvalencio.twitter.to.kafka.service.runner.StreamRunner;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;

@Slf4j
@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "false")
public class TwitterKafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaServiceConfigData configData;

    private final TwitterToKafkaStatusListener statusListener;

    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfigData configData,
                                    TwitterToKafkaStatusListener statusListener) {
        this.configData = configData;
        this.statusListener = statusListener;
    }

    @Override
    public void start() {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(statusListener);
        addFilter();
    }

    private void addFilter() {
        String[] keywords = configData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        log.info("Started filtering twitter stream for keywords{}", Arrays.asList(keywords));
    }

    @PreDestroy
    public void shutdown() {
        if (twitterStream != null) {
            log.info("Closing twitter stream!");
            twitterStream.shutdown();
        }
    }
}
