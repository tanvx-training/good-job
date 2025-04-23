package com.goodjob.common.application.exception;

import lombok.Getter;

/**
 * Exception thrown when a requested resource is not found.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Constructs a new ResourceNotFoundException with the specified resource details.
     *
     * @param resourceName the name of the resource that was not found
     * @param fieldName the name of the field used to search for the resource
     * @param fieldValue the value of the field used to search for the resource
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String message) {
    }
}