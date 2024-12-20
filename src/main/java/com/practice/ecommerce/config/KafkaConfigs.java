package com.practice.ecommerce.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(value = "kafka.enabled", matchIfMissing = true)
public class KafkaConfigs {

    @Value("${com.ecommerce.kafka.topics.category.name}")
    private String categoryTopic;

    @Value("${com.ecommerce.kafka.topics.user.name}")
    private String userTopic;

    @Bean
    public NewTopic categoryTopic(){
        return TopicBuilder.name(categoryTopic).build();
    }

    @Bean
    public NewTopic userTopic(){
        return TopicBuilder.name(userTopic).build();
    }
}
