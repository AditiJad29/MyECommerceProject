package com.practice.ecommerce.kafkaPubSub;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyStringKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyStringKafkaProducer.class);
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private NewTopic categoryTopic;

    public MyStringKafkaProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendCategoryInfo(String categoryInfo){
        kafkaTemplate.send(categoryTopic.name(),categoryInfo);
    }
}
