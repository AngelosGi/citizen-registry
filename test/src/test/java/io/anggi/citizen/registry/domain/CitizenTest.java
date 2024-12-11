package io.anggi.citizen.registry.domain;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

class CitizenTest {

    // Validator instance used to validate Citizen objects
    private Validator validator;

    @BeforeEach
    void setUp() {
        // Use try-with-resources to safely close the ValidatorFactory (was causing issues without it)
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void testValidCitizen() {
        // Create a Citizen object with valid data
        Citizen citizen = new Citizen(
                "AB123456",         // Valid ID
                "John",             // Valid First Name
                "Doe",              // Valid Last Name
                Citizen.Gender.MALE, // Valid Gender
                LocalDate.of(1990, 1, 1), // Valid Birth Date
                "123456789",        // Valid AFM
                "123 Main Street"   // Valid Address
        );

        // Validate the Citizen object and check for violations
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);

        // Assert that there are no violations for a valid Citizen
        assertTrue(violations.isEmpty(), "Citizen should have no validation errors.");
    }

    @Test
    void testInvalidCitizenId() {
        // Create a Citizen object with an invalid ID (not 8 characters)
        Citizen citizen = new Citizen(
                "123",              // Invalid ID
                "John",
                "Doe",
                Citizen.Gender.MALE,
                LocalDate.of(1990, 1, 1),
                "123456789",
                "123 Main Street"
        );

        // Validate and check for violations
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);

        // Assert that there is at least one violation
        assertFalse(violations.isEmpty(), "Citizen with invalid ID should have validation errors.");

        // Print out the validation errors for debugging
        violations.forEach(violation -> System.out.println(violation.getMessage()));
    }

    @Test
    void testInvalidBirthDate() {
        // Create a Citizen object with a future birth date
        Citizen citizen = new Citizen(
                "12345678",
                "John",
                "Doe",
                Citizen.Gender.MALE,
                LocalDate.of(3000, 1, 1), // Invalid Birth Date (future date)
                "123456789",
                "123 Main Street"
        );

        // Validate and check for violations
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);

        // Assert that there is at least one violation
        assertFalse(violations.isEmpty(), "Citizen with future birth date should have validation errors.");

        // Print out the validation errors for debugging
        violations.forEach(violation -> System.out.println(violation.getMessage()));
    }

    @Test
    void testMissingFirstName() {
        // Create a Citizen object with a missing first name
        Citizen citizen = new Citizen(
                "12345678",
                null,                // Missing First Name
                "Doe",
                Citizen.Gender.MALE,
                LocalDate.of(1990, 1, 1),
                "123456789",
                "123 Main Street"
        );

        // Validate and check for violations
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);

        // Assert that there is at least one violation
        assertFalse(violations.isEmpty(), "Citizen with missing first name should have validation errors.");

        // Print out the validation errors for debugging
        violations.forEach(violation -> System.out.println(violation.getMessage()));
    }

    @Test
    void testInvalidAfm() {
        // Create a Citizen object with an invalid AFM (not 9 digits)
        Citizen citizen = new Citizen(
                "12345678",
                "John",
                "Doe",
                Citizen.Gender.MALE,
                LocalDate.of(1990, 1, 1),
                "12345",            // Invalid AFM
                "123 Main Street"
        );

        // Validate and check for violations
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);

        // Assert that there is at least one violation
        assertFalse(violations.isEmpty(), "Citizen with invalid AFM should have validation errors.");

        // Print out the validation errors for debugging
        violations.forEach(violation -> System.out.println(violation.getMessage()));
    }

    @Test
    void testAllFieldsMissing() {
        // Create a Citizen object with all fields missing
        Citizen citizen = new Citizen(
                null,                // Missing ID
                null,                // Missing First Name
                null,                // Missing Last Name
                null,                // Missing Gender
                null,                // Missing Birth Date
                null,                // Missing AFM
                null                 // Missing Address
        );

        // Validate and check for violations
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);

        // Assert that there are multiple violations
        assertFalse(violations.isEmpty(), "Citizen with all fields missing should have validation errors.");

        // Print out the validation errors for debugging
        violations.forEach(violation -> System.out.println(violation.getMessage()));
    }
}
