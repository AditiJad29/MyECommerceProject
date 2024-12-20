package com.practice.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String field;
    String fieldName;
    int fieldID;

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String field, int fieldID) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldID));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldID = fieldID;
    }
}
