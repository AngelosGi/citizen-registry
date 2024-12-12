package io.anggi.citizen.registry.repository;

import io.anggi.citizen.registry.domain.Citizen;
import io.anggi.citizen.registry.service.repositories.CitizenRepository;
import io.anggi.citizen.registry.utils.CitizenUtility;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


//@SpringBootTest(classes = ServiceApplication.class)
@DataJpaTest // Annotation for JPA repository testing.
@ContextConfiguration(classes = io.anggi.citizen.registry.service.ServiceApplication.class)
@ActiveProfiles("test") // Use the test profile
public class CitizenRepositoryIT {

    @Autowired
    private CitizenRepository citizenRepository; // Repository being tested.

    // Test case to ensure saving and retrieving a valid Citizen.
    @Test
    void testSaveAndRetrieveValidCitizen() {
        Citizen validCitizen = CitizenUtility.createValidCitizen();
        citizenRepository.save(validCitizen); // Save to in-memory database.
        Citizen retrievedCitizen = citizenRepository.findById(validCitizen.getIdNumber()).orElse(null);
        assertThat(retrievedCitizen).isNotNull().isEqualTo(validCitizen); // Assert retrieval success.
    }

    // Test case to verify repository rejects invalid Citizens.
    @Test
    void testSaveInvalidCitizen() {
        // Arrange: Use utility to create an invalid Citizen
        Citizen invalidCitizen = CitizenUtility.createInvalidCitizen();

        // Act & Assert: Expect a ConstraintViolationException to be thrown
        assertThrows(
                ConstraintViolationException.class,
                () -> citizenRepository.saveAndFlush(invalidCitizen),
                "Saving an invalid Citizen should throw a ConstraintViolationException."
        );
    }
}
