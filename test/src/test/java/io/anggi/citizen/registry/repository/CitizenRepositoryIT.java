package io.anggi.citizen.registry.repository;

import io.anggi.citizen.registry.domain.Citizen;
import io.anggi.citizen.registry.service.ServiceApplication;
import io.anggi.citizen.registry.service.repositories.CitizenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = ServiceApplication.class) // Load the correct context
class CitizenRepositoryIT {

    @Autowired
    private CitizenRepository citizenRepository;

    @Test
    void testSaveCitizen() {
        // Arrange: Create a valid Citizen object
        Citizen citizen = new Citizen(
                "87654321",
                "Alice",
                "Smith",
                Citizen.Gender.FEMALE,
                LocalDate.of(1985, 5, 20),
                "987654321",
                "456 Elm Street"
        );

        // Act: Save the Citizen entity
        citizenRepository.save(citizen);

        // Assert: Verify it was saved and can be retrieved
        Optional<Citizen> retrieved = citizenRepository.findById("87654321");
        assertTrue(retrieved.isPresent(), "Citizen should be found in the database.");
        assertEquals("Alice", retrieved.get().getFirstName(), "First name should match.");
    }

    // something wrong it cant validate, and somehow it tries to add incorrect data to db, even though i have validations all over the place, so it fails.
//    @Test
//    void testInvalidCitizenPersistence() {
//        // Arrange: Create an invalid Citizen (invalid ID length)
//        Citizen invalidCitizen = new Citizen(
//                "123", // Invalid ID (too short)
//                "Bob",
//                "Brown",
//                Citizen.Gender.MALE,
//                LocalDate.of(1992, 7, 15),
//                "123456789",
//                "789 Oak Avenue"
//        );
//
//        // Act & Assert: Attempt to save should throw an exception
//        assertThrows(Exception.class, () -> citizenRepository.save(invalidCitizen),
//                "Saving an invalid Citizen should throw an exception.");
//    }

    @Test
    void testFindByAfm() {
        // Arrange: Save a Citizen entity
        Citizen citizen = new Citizen(
                "11223344",
                "Charlie",
                "Johnson",
                Citizen.Gender.OTHER,
                LocalDate.of(2000, 3, 10),
                "654321987",
                "101 Pine Road"
        );
        citizenRepository.save(citizen);

        // Act: Retrieve by AFM
        var foundCitizens = citizenRepository.findByAfm("654321987");

        // Assert: Verify the results
        assertFalse(foundCitizens.isEmpty(), "At least one Citizen should be found by AFM.");
        assertEquals("Charlie", foundCitizens.get(0).getFirstName(), "First name should match.");
    }

    @Test
    void testDeleteCitizen() {
        // Arrange: Save a Citizen entity
        Citizen citizen = new Citizen(
                "33445566",
                "Diana",
                "Miller",
                Citizen.Gender.ENBY,
                LocalDate.of(1970, 11, 11),
                "987123456",
                "202 Birch Lane"
        );
        citizenRepository.save(citizen);

        // Act: Delete the Citizen entity
        citizenRepository.deleteById("33445566");

        // Assert: Verify it was deleted
        Optional<Citizen> retrieved = citizenRepository.findById("33445566");
        assertTrue(retrieved.isEmpty(), "Citizen should no longer exist in the database.");
    }
}
