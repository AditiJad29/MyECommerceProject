package com.practice.ecommerce.controller;

import com.practice.ecommerce.dto.HelloResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/welcome")
public class HelloController {

    @PostMapping(path="/hello")
    public HelloResponse printWelcomeMsg(@RequestBody String name){
        return new HelloResponse("Hello, welcome to my E-Commerce Project dashboard, " + name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String greetAdmin(){
        return "Welcome, Admin";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{name}")
    public String greetUser(@PathVariable String name){
        return "Welcome to my E-Commerce Project dashboard, " + name;
    }
}
