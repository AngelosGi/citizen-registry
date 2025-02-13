# Citizen Registry RESTful Service (WIP)

This project implements a **Citizen Registry RESTful service** using **Spring Boot**. The service allows for the management of citizen records, supporting **CRUD** operations such as **create**, **update**, **delete**, and **search**. The service will also provide a future-proof foundation for **CI/CD automation** and **cloud deployments** using tools like **GitHub Actions**, **Terraform**, and **Jenkins**.

---

## Project Overview

The Citizen Registry is designed to manage citizen records with the following attributes:

- **ID Number (ΑΤ)**: A unique, mandatory identification number (8 characters).
- **First Name**: Mandatory.
- **Last Name**: Mandatory.
- **Gender**: Optional, with enumerated values (`MALE`, `FEMALE`, `ENBY`, `OTHER`).
- **Birth Date**: Mandatory and must be a valid past date.
- **Tax ID (ΑΦΜ)**: Mandatory 9-digit number.
- **Residential Address**: Mandatory.

### Features

- **Create Citizens**: Add new citizen records.
- **Update Citizens**: Modify existing records.
- **Delete Citizens**: Remove a citizen by ID.
- **Retrieve Citizens**: Fetch details of a citizen by ID.
- **Search Citizens**: Query citizens based on combinations of attributes.
- **Exception Handling**: User-friendly error responses for invalid operations.

---

## Project Structure / Directory Layout

The project is structured as a **multi-module Maven project**:

- **`citizen-registry-domain`**: Contains the `Citizen` entity and validation logic.
- **`citizen-registry-service`**: Contains the RESTful controllers, business logic, and repository integration.
- **`citizen-registry-test`**: Contains all test classes (unit, integration, and exception handling
- **`citizen-registry-client`**: Provides a CLI for interacting with the API, simulating user input.

```plaintext
citizen-registry/ (parent)
├── client/
│   └── pom.xml
├── domain/
│   └── pom.xml
├── service/
│   └── pom.xml
├── test/
│   └── pom.xml
├── README.md
└── pom.xml
```

---

## Key Technologies

- **Spring Boot 3.4.0**: Simplifies RESTful service creation.
- **Spring Data JPA**: ORM with database integration.
- **Hibernate Validator**: Ensures data integrity via annotations.
- **H2 Database**: In-memory database for local development and testing.
- **JUnit 5**: Unit testing framework.
- **Mockito**: Mocking framework for service and repository testing.
- **Rest-Assured**: Integration testing for RESTful APIs.
- **Swagger/OpenAPI**: Interactive API documentation.
- **Lombok**: Reduces boilerplate code.
- **GitHub Actions**: For CI/CD automation.
- **Terraform**: For infrastructure provisioning. **(Future)**
  
---

## API Endpoints

### Base URL

`http://localhost:8080/api/citizens`

### Endpoints

1. **Create a Citizen**

   - **Method**: `POST`
   - **Request**:
     ```json
     {
       "idNumber": "AB123456",
       "firstName": "Γιάννης",
       "lastName": "Ντο",
       "birthDate": "1990-01-01",
       "gender": "MALE",
       "afm": "123456789",
       "address": "Λεωφόρος Σφενδάμου 456, Θεσσαλονίκη, 54629"
     }
     ```
   - **Response**:
     ```json
     {
       "message": "Citizen created successfully."
     }
     ```

2. **Retrieve a Citizen by ID**

   - **Method**: `GET`
   - **URL**: `/api/citizens/{id}`

3. **Update a Citizen**

   - **Method**: `PUT`
   - **Request Body**: Similar to `POST`.

4. **Delete a Citizen**

   - **Method**: `DELETE`
   - **URL**: `/api/citizens/{id}`

5. **Search Citizens**

   - **Method**: `GET`
   - **Query Parameters**:
     - `firstName`, `lastName`, `idNumber`, `afm`
   - **Example**:
     `GET /api/citizens?firstName=Γιάννης&lastName=Ντο`

---

## Exception Handling

Custom exception handling ensures user-friendly responses:

- **ConstraintViolationException**: For validation errors.
- **EntityNotFoundException**: For missing records.
- **IllegalArgumentException**: For invalid arguments.
- **MethodArgumentNotValidException**: For request body validation failures.
- **Generic Exception Handler**: Catches unforeseen exceptions.

---

## Testing

### Test Types

1. **Unit Tests**
   - Validate entity constraints.
   - Ensure business logic correctness.

2. **Integration Tests**
   - Verify database interactions.
   - Test RESTful endpoints using **Rest-Assured**. (WIP)

3. **Exception Tests**
   - Validate exception handling and responses.

### Test Coverage

- **Entity Tests**: Ensures field-level validation.
- **Service Tests**: Validates service layer logic.
- **Repository Tests**: Tests CRUD operations.
- **Controller Tests**: Ensures endpoint behavior. (WIP)

---

## CI/CD Integration with GitHub Actions

### CI/CD Workflow Overview

The project includes a **GitHub Actions pipeline** to automate the testing and merging process. The workflow:

1. **Builds and Tests the Code**:
   - Runs unit and integration tests on every push to `feature/*` or `fix/*` branches.
2. **Manages Branches**:
   - Automatically merges changes into the `develop` branch after successful tests.
3. **Uploads Test Reports**:
   - Provides artifacts for debugging and review.

### Workflow Configuration

The pipeline is defined in `.github/workflows/pipeline.yml`. Key steps include:

- **Build and Package**:
  - Uses `mvn clean package` to compile the project.
- **Unit Tests**:
  - Executes `mvn test` to validate business logic.
- **Integration Tests**:
  - Executes `mvn verify` to test the interaction between components.
- **Merge Process**:
  - Automatically merges feature or fix branches into `develop` upon successful completion of tests.

---

## Development Setup

### Prerequisites

- **Java 21**
- **Maven 3.9+**
- **IDE**

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/AngelosGi/citizen-registry.git
   ```
2. Navigate to the project directory:
   ```bash
   cd citizen-registry
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Future Enhancements

### Cloud Deployment

- Use **Terraform** to provision infrastructure in **AWS** or **Azure**:
  - **App Services**: For hosting the API.
  - **Database Resources**: For citizen records.
  - **Networking**: Load balancers, security groups, etc.

### Advanced Testing

- Improve test coverage.
- Add end-to-end tests for a complete testing pipeline.

---

