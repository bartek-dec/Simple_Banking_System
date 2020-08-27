package com.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardNumberValidatorTest {

    private final String validNumber = "4000008449433403";
    private final String invalidNumber_1 = "4000008449433405";
    private final String invalidNumber_2 = "";
    private final String invalidNumber_3 = "400000844";
    private final String invalidNumber_4 = "40000084494334aa";

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new CardNumberValidator();
    }

    @Test
    void validateTrue() {
        assertTrue(validator.validate(validNumber));
    }

    @Test
    void validateFalseFirstCase() {
        assertFalse(validator.validate(invalidNumber_1));
    }

    @Test
    void validateFalseSecondCase() {
        assertFalse(validator.validate(invalidNumber_2));
    }

    @Test
    void validateFalseThirdCase() {
        assertFalse(validator.validate(invalidNumber_3));
    }

    @Test
    void validateFalseFourthCase() {
        assertFalse(validator.validate(invalidNumber_4));
    }
}