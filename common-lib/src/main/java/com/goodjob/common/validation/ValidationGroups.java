package com.goodjob.common.validation;

/**
 * Validation groups for bean validation.
 * Provides standard validation groups for different operations.
 */
public final class ValidationGroups {

    private ValidationGroups() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validation group for create operations.
     */
    public interface Create {
    }

    /**
     * Validation group for update operations.
     */
    public interface Update {
    }

    /**
     * Validation group for delete operations.
     */
    public interface Delete {
    }

    /**
     * Validation group for search operations.
     */
    public interface Search {
    }

    /**
     * Validation group for read operations.
     */
    public interface Read {
    }
} 