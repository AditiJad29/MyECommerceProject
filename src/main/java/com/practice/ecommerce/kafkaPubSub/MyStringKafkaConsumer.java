package com.practice.ecommerce.kafkaPubSub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyStringKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyStringKafkaConsumer.class);

    @KafkaListener(topics = "${com.ecommerce.kafka.topics.category.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMsg(String message){
            LOGGER.info(String.format("Category Message consumed: %s", message));
    }
}
