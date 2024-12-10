package io.anggi.citizen.registry.client;

import io.anggi.citizen.registry.domain.Citizen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@SpringBootApplication
public class SpringBootClientApplication implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/api/citizens";

    public SpringBootClientApplication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createCitizen(scanner);
                case "2" -> getCitizenById(scanner);
                case "3" -> getAllCitizens();
                case "4" -> updateCitizen(scanner);
                case "5" -> deleteCitizen(scanner);
                case "6" -> searchCitizens(scanner);
                case "0" -> exitApplication();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Citizen Management System ===");
        System.out.println("1. Create Citizen");
        System.out.println("2. Get Citizen by ID");
        System.out.println("3. Get All Citizens");
        System.out.println("4. Update Citizen");
        System.out.println("5. Delete Citizen");
        System.out.println("6. Search Citizens");
        System.out.println("0. Exit");
    }

    private void createCitizen(Scanner scanner) {
        System.out.println("\n=== Create Citizen ===");

        String idNumber = promptForValidIdNumber(scanner);
        String firstName = promptForNonEmptyInput(scanner, "Enter First Name: ");
        String lastName = promptForNonEmptyInput(scanner, "Enter Last Name: ");
        Citizen.Gender gender = promptForValidGender(scanner);
        LocalDate birthDate = promptForValidDate(scanner);
        String afm = promptForValidAfm(scanner);
        String address = promptForNonEmptyInput(scanner, "Enter Address: ");

        try {
            Citizen citizen = new Citizen(idNumber, firstName, lastName, gender, birthDate, afm, address);
            Citizen createdCitizen = restTemplate.postForObject(BASE_URL, citizen, Citizen.class);
            System.out.println("Citizen created successfully: " + createdCitizen);
        } catch (Exception e) {
            System.err.println("Error creating citizen: " + e.getMessage());
        }
    }

    private void getCitizenById(Scanner scanner) {
        System.out.println("\n=== Get Citizen by ID ===");
        String idNumber = promptForValidIdNumber(scanner);

        try {
            Citizen citizen = restTemplate.getForObject(BASE_URL + "/" + idNumber, Citizen.class);
            System.out.println("Citizen details: " + citizen);
        } catch (Exception e) {
            System.err.println("Error fetching citizen: " + e.getMessage());
        }
    }

    private void getAllCitizens() {
        System.out.println("\n=== Get All Citizens ===");

        try {
            Citizen[] citizens = restTemplate.getForObject(BASE_URL, Citizen[].class);
            for (Citizen citizen : citizens) {
                System.out.println(citizen);
            }
        } catch (Exception e) {
            System.err.println("Error fetching citizens: " + e.getMessage());
        }
    }

    private void updateCitizen(Scanner scanner) {
        System.out.println("\n=== Update Citizen ===");
        String idNumber = promptForValidIdNumber(scanner);
        String afm = promptForValidAfm(scanner);
        String address = promptForNonEmptyInput(scanner, "Enter New Address: ");

        try {
            Citizen citizen = restTemplate.getForObject(BASE_URL + "/" + idNumber, Citizen.class);
            if (citizen != null) {
                citizen.setAfm(afm);
                citizen.setAddress(address);
                restTemplate.put(BASE_URL + "/" + idNumber, citizen);
                System.out.println("Citizen updated successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error updating citizen: " + e.getMessage());
        }
    }

    private void deleteCitizen(Scanner scanner) {
        System.out.println("\n=== Delete Citizen ===");
        String idNumber = promptForValidIdNumber(scanner);

        try {
            restTemplate.delete(BASE_URL + "/" + idNumber);
            System.out.println("Citizen deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting citizen: " + e.getMessage());
        }
    }

    private void searchCitizens(Scanner scanner) {
        System.out.println("\n=== Search Citizens ===");

        String idNumber = promptForOptionalInput(scanner, "Enter ID Number (optional): ");
        String firstName = promptForOptionalInput(scanner, "Enter First Name (optional): ");
        String lastName = promptForOptionalInput(scanner, "Enter Last Name (optional): ");
        String afm = promptForOptionalInput(scanner, "Enter AFM (optional): ");

        try {
            String queryParams = String.format("?idNumber=%s&firstName=%s&lastName=%s&afm=%s",
                    idNumber, firstName, lastName, afm);
            Citizen[] citizens = restTemplate.getForObject(BASE_URL + "/search" + queryParams, Citizen[].class);
            for (Citizen citizen : citizens) {
                System.out.println(citizen);
            }
        } catch (Exception e) {
            System.err.println("Error searching citizens: " + e.getMessage());
        }
    }

    private void exitApplication() {
        System.out.println("Exiting application. Goodbye!");
        System.exit(0);
    }

    private String promptForValidIdNumber(Scanner scanner) {
        while (true) {
            System.out.print("Enter ID Number (8 characters): ");
            String idNumber = scanner.nextLine();
            if (idNumber.length() == 8) return idNumber;
            System.out.println("Invalid ID Number. Must be exactly 8 characters.");
        }
    }

    private String promptForValidAfm(Scanner scanner) {
        while (true) {
            System.out.print("Enter AFM (9 digits): ");
            String afm = scanner.nextLine();
            if (afm.matches("\\d{9}")) return afm;
            System.out.println("Invalid AFM. Must be exactly 9 digits.");
        }
    }

    private Citizen.Gender promptForValidGender(Scanner scanner) {
        while (true) {
            System.out.print("Enter Gender (MALE, FEMALE, ENBY, OTHER): ");
            String genderInput = scanner.nextLine().toUpperCase();
            try {
                return Citizen.Gender.valueOf(genderInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Gender. Enter one of: MALE, FEMALE, ENBY, OTHER.");
            }
        }
    }

    private LocalDate promptForValidDate(Scanner scanner) {
        while (true) {
            System.out.print("Enter Birth Date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            try {
                return LocalDate.parse(dateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date. Must be in format YYYY-MM-DD.");
            }
        }
    }

    private String promptForNonEmptyInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (!input.isBlank()) return input;
            System.out.println("This field cannot be empty.");
        }
    }

    private String promptForOptionalInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
