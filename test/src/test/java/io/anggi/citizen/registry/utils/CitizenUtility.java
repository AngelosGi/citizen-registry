
package io.anggi.citizen.registry.utils;

import io.anggi.citizen.registry.domain.Citizen;

import java.time.LocalDate;

// Utility class to create various Citizen instances for testing purposes.
public class CitizenUtility {

    // Creates a valid Citizen with all fields populated
    public static Citizen createValidCitizen() {
        return new Citizen(
                "12345678",        // Valid ID
                "John",            // Valid first name
                "Doe",             // Valid last name
                Citizen.Gender.MALE, // Valid gender (optional)
                LocalDate.of(1990, 1, 1), // Valid birth date
                "987654321",       // Valid tax ID (AFM)
                "123 Main Street"  // Valid address
        );
    }

    // Create an invalid Citizen instance with violations in required fields.
    public static Citizen createInvalidCitizen() {
        return new Citizen(
                "", // Invalid ID number (empty).
                null, // Invalid first name (null).
                "", // Invalid last name (empty).
                null, // Invalid gender (null).
                LocalDate.now().plusDays(1), // Invalid birth date (future date).
                "123", // Invalid tax ID (too short).
                null // Invalid address (null).
        );
    }

    // Creates a Citizen with only mandatory fields (omitting optional gender)
    public static Citizen createCitizenWithOptionalFields() {
        return new Citizen(
                "87654321",             // Valid ID number
                "Jane",                 // Valid first name
                "Smith",                // Valid last name
                null,                   // Gender (optional)
                LocalDate.of(1985, 5, 15), // Valid birth date
                "123456789",            // Valid AFM
                "456 Elm Street"        // Valid address
        );
    }
}