package com.practice.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping(path="/api/v1/{name}")
    public ResponseEntity<String> sayHelloToUser(@PathVariable String name){
        return ResponseEntity.ok("Hi " + name);
    }
}
