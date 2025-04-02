package edu.ntnu.stud.util;

import java.util.function.Predicate;

/**
 * Utility class for validating objects.
 */
public class Validate {

  public <T> Predicate<T> isNotNull() {
    return value -> value != null;
  }

  public Predicate<String> isNotEmpty() {
    return value -> value != null && !value.isEmpty();
  }

  public Predicate<String> isNotBlank() {
    return value -> value != null && !value.isBlank();
  }

  public Predicate<String> isNotEmptyOrBlank() {
    return value -> value != null && !value.isEmpty() && !value.isBlank();
  }

  public Predicate<String> isNotEmptyOrBlankOrNull() {
    return value -> value != null && !value.isEmpty() && !value.isBlank();
  }

  public Predicate<Number> isPositive() {
    return value -> value != null && value.doubleValue() > 0;
  }

  public Predicate<Number> isZero() {
    return value -> value != null && value.doubleValue() == 0;
  }

  public Predicate<Number> isNotZero() {
    return value -> value != null && value.doubleValue() != 0;
  }

  public Predicate<Number> isNegative() {
    return value -> value != null && value.doubleValue() < 0;
  }

  /**
   * Validates that a given value by the provided predicate and throws an exception with the 
   * provided message if validation fails.
   *
   * @param <T> the type of the value to validate
   * @param value the value to validate
   * @param predicate the predicate to validate the value against
   * @param message the message to include in the exception if validation fails
   * @return the validated value
   */
  public <T> T that(T value, Predicate<T> predicate, String message) {
    if (!predicate.test(value)) {
      throw new IllegalArgumentException(message);
    }
    return value;
  }

  public <T> T that(T value, Predicate<T> predicate) {
    return that(value, predicate, "Validation failed for value: " + value);
  }
}
