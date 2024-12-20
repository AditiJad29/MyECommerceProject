package com.practice.ecommerce.controller;

import com.practice.ecommerce.kafkaPubSub.MyJSONKafkaProducer;
import com.practice.ecommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/public")
public class UserController {
    @Autowired
    private MyJSONKafkaProducer jsonKafkaProducer;

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody User user){
        jsonKafkaProducer.sendMessage(user);
        return ResponseEntity.ok("User Info Saved in Kafka");
    }
}
