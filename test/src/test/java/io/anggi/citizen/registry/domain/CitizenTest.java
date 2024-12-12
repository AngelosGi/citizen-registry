package io.anggi.citizen.registry.domain;

import io.anggi.citizen.registry.utils.CitizenUtility;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CitizenTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void testValidCitizen() {
        Citizen validCitizen = CitizenUtility.createValidCitizen();
        Set<ConstraintViolation<Citizen>> violations = validator.validate(validCitizen);
        assertTrue(violations.isEmpty(), "Ένας έγκυρος πολίτης δεν πρέπει να έχει σφάλματα επικύρωσης.");
    }

    @Test
    void testMissingFirstName() {
        Citizen invalidCitizen = CitizenUtility.createValidCitizen();
        invalidCitizen.setFirstName(null);
        Set<ConstraintViolation<Citizen>> violations = validator.validate(invalidCitizen);
        assertFalse(violations.isEmpty());
        assertEquals("Το Ονομα δεν πρέπει να είναι κενό", violations.iterator().next().getMessage());
    }

    @Test
    void testMissingLastName() {
        Citizen invalidCitizen = CitizenUtility.createValidCitizen();
        invalidCitizen.setLastName(null);
        Set<ConstraintViolation<Citizen>> violations = validator.validate(invalidCitizen);
        assertFalse(violations.isEmpty());
        assertEquals("Το Επίθετο δεν πρέπει να είναι κενό", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidIDNumber() {
        Citizen invalidCitizen = CitizenUtility.createValidCitizen();
        invalidCitizen.setIdNumber("123"); // Too short
        Set<ConstraintViolation<Citizen>> violations = validator.validate(invalidCitizen);
        assertFalse(violations.isEmpty());
        assertEquals("Ο αριθμός ταυτότητας (ΑΤ) πρέπει να έχει ακριβώς 8 χαρακτήρες.", violations.iterator().next().getMessage());
    }

    @Test
    void testBlankAddress() {
        Citizen invalidCitizen = CitizenUtility.createValidCitizen();
        invalidCitizen.setAddress(null);
        Set<ConstraintViolation<Citizen>> violations = validator.validate(invalidCitizen);
        assertFalse(violations.isEmpty());
        assertEquals("Η Διεύθυνση δεν πρέπει να είναι κενή.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidAFM() {
        Citizen invalidCitizen = CitizenUtility.createValidCitizen();
        invalidCitizen.setAfm("123"); // Too short
        Set<ConstraintViolation<Citizen>> violations = validator.validate(invalidCitizen);
        assertFalse(violations.isEmpty());
        assertEquals("Ο ΑΦΜ πρέπει να έχει ακριβώς 9 ψηφία.", violations.iterator().next().getMessage());
    }

    @Test
    void testFutureBirthDate() {
        Citizen invalidCitizen = CitizenUtility.createValidCitizen();
        invalidCitizen.setBirthDate(LocalDate.now().plusDays(1)); // Future date
        Set<ConstraintViolation<Citizen>> violations = validator.validate(invalidCitizen);
        assertFalse(violations.isEmpty());
        assertEquals("Η Ημερομηνία γέννησης πρέπει να είναι στο παρελθόν.", violations.iterator().next().getMessage());
    }

    @Test
    void testCitizenWithoutGender() {
        Citizen citizenWithoutGender = CitizenUtility.createCitizenWithOptionalFields();
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizenWithoutGender);
        assertTrue(violations.isEmpty(), "Ένας Πολίτης χωρίς φύλο δεν θα πρέπει να έχει σφάλματα επικύρωσης.");
    }
}
