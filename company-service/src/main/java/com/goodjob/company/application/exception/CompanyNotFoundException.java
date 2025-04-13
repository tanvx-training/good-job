package com.goodjob.company.application.exception;

/**
 * Exception thrown when a company is not found.
 */
public class CompanyNotFoundException extends RuntimeException {

    /**
     * Constructs a new CompanyNotFoundException with the specified company ID.
     *
     * @param id the company ID
     */
    public CompanyNotFoundException(Integer id) {
        super("Company not found with ID: " + id);
    }
}