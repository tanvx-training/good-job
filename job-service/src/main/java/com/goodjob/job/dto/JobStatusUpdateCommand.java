package com.goodjob.job.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for job status update operations.
 */
public class JobStatusUpdateCommand {
    
    @NotBlank(message = "Status is required")
    private String status;
    
    public JobStatusUpdateCommand() {
    }
    
    public JobStatusUpdateCommand(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}