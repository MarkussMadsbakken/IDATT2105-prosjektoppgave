package edu.ntnu.stud.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidateTest {

    @Test
    void testIsNull() {
        assertTrue(Validate.isNull().test(null));
        assertFalse(Validate.isNull().test("Not null"));
    }

    @Test
    void testIsNotNull() {
        assertTrue(Validate.isNotNull().test("Not null"));
        assertFalse(Validate.isNotNull().test(null));
    }

    @Test
    void testIsNotEmpty() {
        assertTrue(Validate.isNotEmpty().test("Non-empty"));
        assertFalse(Validate.isNotEmpty().test(""));
        assertFalse(Validate.isNotEmpty().test(null));
    }

    @Test
    void testIsNotBlank() {
        assertTrue(Validate.isNotBlank().test("Non-blank"));
        assertFalse(Validate.isNotBlank().test("   "));
        assertFalse(Validate.isNotBlank().test(null));
    }

    @Test
    void testIsNotBlankOrNull() {
        assertTrue(Validate.isNotBlankOrNull().test("Valid"));
        assertFalse(Validate.isNotBlankOrNull().test(""));
        assertFalse(Validate.isNotBlankOrNull().test("   "));
        assertFalse(Validate.isNotBlankOrNull().test(null));
    }

    @Test
    void testIsPositive() {
        assertTrue(Validate.isPositive().test(5));
        assertFalse(Validate.isPositive().test(0));
        assertFalse(Validate.isPositive().test(-5));
        assertFalse(Validate.isPositive().test(null));
    }

    @Test
    void testIsNotPositive() {
        assertTrue(Validate.isNotPositive().test(0));
        assertTrue(Validate.isNotPositive().test(-5));
        assertFalse(Validate.isNotPositive().test(5));
        assertFalse(Validate.isNotPositive().test(null));
    }

    @Test
    void testIsZero() {
        assertTrue(Validate.isZero().test(0));
        assertFalse(Validate.isZero().test(5));
        assertFalse(Validate.isZero().test(-5));
        assertFalse(Validate.isZero().test(null));
    }

    @Test
    void testIsNotZero() {
        assertTrue(Validate.isNotZero().test(5));
        assertTrue(Validate.isNotZero().test(-5));
        assertFalse(Validate.isNotZero().test(0));
        assertFalse(Validate.isNotZero().test(null));
    }

    @Test
    void testIsNegative() {
        assertTrue(Validate.isNegative().test(-5));
        assertFalse(Validate.isNegative().test(0));
        assertFalse(Validate.isNegative().test(5));
        assertFalse(Validate.isNegative().test(null));
    }

    @Test
    void testIsNotNegative() {
        assertTrue(Validate.isNotNegative().test(5));
        assertTrue(Validate.isNotNegative().test(0));
        assertFalse(Validate.isNotNegative().test(-5));
        assertFalse(Validate.isNotNegative().test(null));
    }

    @Test
    void testIsTrue() {
        assertTrue(Validate.isTrue().test(true));
        assertFalse(Validate.isTrue().test(false));
        assertFalse(Validate.isTrue().test(null));
    }

    @Test
    void testIsFalse() {
        assertTrue(Validate.isFalse().test(false));
        assertFalse(Validate.isFalse().test(true));
        assertFalse(Validate.isFalse().test(null));
    }

    @Test
    void testThatWithPredicate() {
        assertDoesNotThrow(() -> Validate.that(5, Validate.isPositive()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> Validate.that(-5, Validate.isPositive()));
        assertEquals("Validation failed for value: -5", exception.getMessage());
    }

    @Test
    void testThatWithCustomMessage() {
        assertDoesNotThrow(() -> Validate.that(5, Validate.isPositive(), "Custom message"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> Validate.that(-5, Validate.isPositive(), "Custom message"));
        assertEquals("Custom message", exception.getMessage());
    }
}