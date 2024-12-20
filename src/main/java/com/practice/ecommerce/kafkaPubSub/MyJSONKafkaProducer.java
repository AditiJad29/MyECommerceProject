package com.practice.ecommerce.kafkaPubSub;

import com.practice.ecommerce.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MyJSONKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyJSONKafkaProducer.class);
    private KafkaTemplate<String, User> kafkaTemplate;
    @Value("${com.ecommerce.kafka.topics.user.name}")
    private String userTopic;

    public MyJSONKafkaProducer(KafkaTemplate<String,User> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(User userData){
        LOGGER.info("Sending user data: ", userData.toString());

        Message<User> message = MessageBuilder.withPayload(userData).setHeader(KafkaHeaders.TOPIC,userTopic).build();

        kafkaTemplate.send(message);

    }
}
