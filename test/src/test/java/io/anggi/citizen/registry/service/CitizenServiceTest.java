package io.anggi.citizen.registry.service;

import io.anggi.citizen.registry.domain.Citizen;
import io.anggi.citizen.registry.service.repositories.CitizenRepository;
import io.anggi.citizen.registry.service.services.CitizenService;
import io.anggi.citizen.registry.utils.CitizenUtility;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// with CitizenUtility for creating reusable Citizen instances.
public class CitizenServiceTest {

    @Mock
    private CitizenRepository citizenRepository;

    @InjectMocks
    private CitizenService citizenService;

    private Validator validator; // Validator for testing Bean Validation constraints.

    // Initialize ValidatorFactory and Validator before each test.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator(); // Initialize the validator
        }
    }


    // Test case to validate a correct Citizen entity.
    @Test
    void testValidCitizen() {
        Citizen validCitizen = CitizenUtility.createValidCitizen(); // Generate a valid Citizen.
        Set<ConstraintViolation<Citizen>> violations = validator.validate(validCitizen); // Validate constraints.
        assertTrue(violations.isEmpty(), "Citizen should be valid."); // Assert no violations.
    }

    // Test case to validate an incorrect Citizen entity.
    @Test
    void testInvalidCitizen() {
        Citizen invalidCitizen = CitizenUtility.createInvalidCitizen(); // Generate an invalid Citizen.
        Set<ConstraintViolation<Citizen>> violations = validator.validate(invalidCitizen); // Validate constraints.
        assertFalse(violations.isEmpty(), "Citizen should have validation errors."); // Assert violations exist.
    }

    // Test case to validate a Citizen entity with optional fields only.
    @Test
    void testCitizenWithOptionalFields() {
        Citizen partialCitizen = CitizenUtility.createCitizenWithOptionalFields(); // Generate partial Citizen.
        Set<ConstraintViolation<Citizen>> violations = validator.validate(partialCitizen); // Validate constraints.
        assertTrue(violations.isEmpty(), "Citizen with optional fields only should be valid."); // Assert no violations.
    }


    @Test
    void testSearchByIdNumber() {
        String idNumber = "AB123456";
        when(citizenRepository.findByIdNumber(idNumber)).thenReturn(List.of(new Citizen()));
        List<Citizen> result = citizenService.searchCitizens(idNumber, null, null, null);
        assertEquals(1, result.size());
        verify(citizenRepository, times(1)).findByIdNumber(idNumber);
    }

    @Test
    void testSearchByFirstNameAndLastName() {
        String firstName = "Γιάννης";
        String lastName = "Γεωργίου";
        when(citizenRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(List.of(new Citizen()));
        List<Citizen> result = citizenService.searchCitizens(null, firstName, lastName, null);
        assertEquals(1, result.size());
        verify(citizenRepository, times(1)).findByFirstNameAndLastName(firstName, lastName);
    }
}
