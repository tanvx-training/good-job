package com.goodjob.company.application.exception;

/**
 * Exception thrown when a company with the same name already exists.
 */
public class CompanyAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new CompanyAlreadyExistsException with the specified company name.
     *
     * @param name the company name
     */
    public CompanyAlreadyExistsException(String name) {
        super("Company already exists with name: " + name);
    }
} 