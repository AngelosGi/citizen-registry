package io.anggi.citizen.registry.service;

import io.anggi.citizen.registry.domain.Citizen;
import io.anggi.citizen.registry.service.repositories.CitizenRepository;
import io.anggi.citizen.registry.service.services.CitizenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitizenServiceTest {

    @Mock
    private CitizenRepository citizenRepository; // Mocked repository

    @InjectMocks
    private CitizenService citizenService; // Service under test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testCreateCitizen() {
        // Arrange: Create a valid Citizen object and mock repository behavior
        Citizen citizen = new Citizen("12345678", "John", "Doe", Citizen.Gender.MALE,
                LocalDate.of(1990, 1, 1), "123456789", "123 Main Street");

        when(citizenRepository.save(citizen)).thenReturn(citizen);

        // Act: Call the service method
        Citizen createdCitizen = citizenService.createCitizen(citizen);

        // Assert: Verify the behavior and result
        assertNotNull(createdCitizen, "Citizen should not be null.");
        assertEquals("John", createdCitizen.getFirstName(), "First name should match.");

        verify(citizenRepository, times(1)).save(citizen); // Verify repository interaction
    }

    @Test
    void testGetCitizenById() {
        // Arrange: Mock repository behavior
        Citizen citizen = new Citizen("12345678", "John", "Doe", Citizen.Gender.MALE,
                LocalDate.of(1990, 1, 1), "123456789", "123 Main Street");

        when(citizenRepository.findById("12345678")).thenReturn(Optional.of(citizen));

        // Act: Call the service method
        Citizen foundCitizen = citizenService.getCitizenById("12345678");

        // Assert: Verify the result
        assertNotNull(foundCitizen, "Citizen should not be null.");
        assertEquals("John", foundCitizen.getFirstName(), "First name should match.");

        verify(citizenRepository, times(1)).findById("12345678"); // Verify repository interaction
    }

    @Test
    void testDeleteCitizen() {
        // Arrange: Mock repository behavior
        when(citizenRepository.existsById("12345678")).thenReturn(true);

        // Act: Call the service method
        citizenService.deleteCitizen("12345678");

        // Assert: Verify repository interaction
        verify(citizenRepository, times(1)).deleteById("12345678");
    }
}
