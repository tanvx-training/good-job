package com.goodjob.common.util;

import com.goodjob.common.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Utility class for validation operations.
 * Provides methods for common validation operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {

    /**
     * Validates that an object is not null.
     *
     * @param object the object to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a string is not null or empty.
     *
     * @param str the string to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a string is not null, empty, or contains only whitespace.
     *
     * @param str the string to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a collection is not null or empty.
     *
     * @param collection the collection to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a map is not null or empty.
     *
     * @param map the map to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that an array is not null or empty.
     *
     * @param array the array to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a condition is true.
     *
     * @param condition the condition to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a condition is false.
     *
     * @param condition the condition to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void isFalse(boolean condition, String message) {
        if (condition) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a value is greater than a minimum value.
     *
     * @param value the value to validate
     * @param min the minimum value
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void greaterThan(int value, int min, String message) {
        if (value <= min) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a value is less than a maximum value.
     *
     * @param value the value to validate
     * @param max the maximum value
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void lessThan(int value, int max, String message) {
        if (value >= max) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a value is between a minimum and maximum value.
     *
     * @param value the value to validate
     * @param min the minimum value
     * @param max the maximum value
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void between(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a string matches a regular expression.
     *
     * @param str the string to validate
     * @param regex the regular expression
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void matches(String str, String regex, String message) {
        if (str == null || !str.matches(regex)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that an email address is valid.
     *
     * @param email the email address to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void validEmail(String email, String message) {
        if (!StringUtils.isValidEmail(email)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates that a phone number is valid.
     *
     * @param phone the phone number to validate
     * @param message the error message if validation fails
     * @throws BadRequestException if validation fails
     */
    public static void validPhone(String phone, String message) {
        if (!StringUtils.isValidPhone(phone)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * Validates a condition and returns a value if valid.
     *
     * @param condition the condition to validate
     * @param valueSupplier the supplier of the value to return if valid
     * @param message the error message if validation fails
     * @param <T> the type of the value
     * @return the value if valid
     * @throws BadRequestException if validation fails
     */
    public static <T> T validateAndGet(boolean condition, Supplier<T> valueSupplier, String message) {
        if (!condition) {
            throw new BadRequestException(message);
        }
        return valueSupplier.get();
    }
} 