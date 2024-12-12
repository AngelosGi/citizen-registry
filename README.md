# Citizen Registry RESTful Service (WIP)

This project implements a **Citizen Registry RESTful service** using **Spring Boot**. The service allows for the management of citizen records, supporting **CRUD** operations such as **create**, **update**, **delete**, and **search**. The service will also provide a future-proof foundation for **CI/CD automation** and **cloud deployments** using tools like **Jenkins**, **GitHub Actions**, and **Terraform**.

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

### Project Structure / Directory Layout

The project is structured as a **multi-module Maven project**:

- **`citizen-registry-domain`**: Contains the `Citizen` entity and validation logic.
- **`citizen-registry-service`**: Contains the RESTful controllers, business logic, and repository integration.
- **`citizen-registry-test`**: Contains all test classes (unit, integration, and exception handling
- **`citizen-registry-client`**: Provides a CLI for interacting with the API, simulating user input.

```
citizen-registry/ (parent)
├── pom.xml
├── domain/
├── service/
├── test/
├── client/ 
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
- **Generic Exception Handler**: Catches any unforeseen exceptions.

---

## Testing

### Test Types

1. **Unit Tests**
   - Validate entity constraints.
   - Ensure business logic correctness.

2. **Integration Tests**
   - Verify database interactions.
   - ~~Test RESTful endpoints.~~ WIP

3. **Exception Tests**
   - Validate exception handling and responses.

### Test Coverage

- **Entity Tests**: Ensures field-level validation.
- **Service Tests**: Validates service layer logic.
- **Repository Tests**: Tests CRUD operations.
- ~~**Controller Tests**: Ensures endpoint behavior.~~ WIP

---

## Development Setup

### Prerequisites
- Java 21
- Maven 3.9+
- IDE, I used IntelliJ IDEA

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


---
## Future Plans

### CI/CD Integration

Future enhancements include automating the build, test, and deployment processes via **Continuous Integration (CI)** and **Continuous Delivery (CD)** pipelines. The planned technologies for this automation are:

2. **GitHub Actions**: For lightweight CI/CD workflows that will automate testing, linting, and Docker builds.
3. **Terraform**: To automate infrastructure provisioning in **AWS** or **Azure**, allowing for cloud deployments and scaling.
   later i will try **Jenkins**

These technologies will help ensure faster development cycles, reduce manual intervention, and make deployments more reliable and consistent.

### Cloud Infrastructure

The service will be deployed in the cloud (likely **AWS** or **Azure**), and I plan to use **Terraform** for infrastructure management. Terraform will define resources such as:

- **App Services**: For hosting the API.
- **Virtual Machines**: If needed
- **Databases**: For storing citizen records.
- **Load Balancers, Networking etc**: For high availability and scalability.

### Automated Testing and Quality Assurance

- **Unit Testing**: Using **JUnit 5** for service layer logic.
- **Integration Testing**: Using **Rest-Assured** to verify the interaction between the API and the database.
