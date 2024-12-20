package com.practice.ecommerce.exceptions;

public class APIException extends RuntimeException{

    String message;

    APIException(){}

    public APIException(String message){
        super(message);
    }
}
