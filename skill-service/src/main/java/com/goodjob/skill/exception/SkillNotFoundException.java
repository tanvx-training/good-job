package com.goodjob.skill.exception;

/**
 * Exception thrown when a skill is not found.
 */
public class SkillNotFoundException extends RuntimeException {

    /**
     * Constructs a new SkillNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public SkillNotFoundException(String message) {
        super(message);
    }
} 