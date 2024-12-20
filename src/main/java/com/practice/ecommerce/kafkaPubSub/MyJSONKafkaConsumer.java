package com.practice.ecommerce.kafkaPubSub;

import com.practice.ecommerce.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.stereotype.Service;

@Service
public class MyJSONKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyJSONKafkaConsumer.class);

    @KafkaListener(topics = "${com.ecommerce.kafka.topics.user.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUser(User user) {
        LOGGER.info("Consumed User Event: " + user.toString());
    }

    @Bean
    public KafkaListenerErrorHandler myTopicErrorHandler() {
        return (m, e) -> {
            LOGGER.error("Got an error {}", e.getMessage());
            return "some info about the failure";
        };
    }
}
