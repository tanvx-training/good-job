package com.goodjob.skill.exception;

/**
 * Exception thrown when a skill already exists.
 */
public class SkillAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new SkillAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public SkillAlreadyExistsException(String message) {
        super(message);
    }
} 