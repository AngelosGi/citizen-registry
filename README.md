# Citizen Registry RESTful Service (WIP)

This project implements a **Citizen Registry RESTful service** using **Spring Boot**. The service allows for the management of citizen records, supporting **CRUD** operations such as **create**, **update**, **delete**, and **search**. The service will also provide a future-proof foundation for **CI/CD automation** and **cloud deployments** using tools like **Jenkins**, **GitHub Actions**, and **Terraform**.

## Project Overview

The Citizen Registry service allows the management of citizen records, with the following key data points:

- **ID Number (ΑΤ)**: A unique mandatory identification number.
- **First Name**: A mandatory field.
- **Last Name**: A mandatory field.
- **Gender**: A mandatory field.
- **Birth Date**: A mandatory field in the format `DD-MM-YYYY`.
- **Tax ID (ΑΦΜ)**: An optional field.
- **Residential Address**: An optional field.

### Supported Operations

The RESTful service supports the following CRUD operations:

- **Create**: Add a new citizen record.
- **Update**: Modify existing records.
- **Delete**: Remove a citizen record by ID.
- **Search**: Find citizen records based on any combination of fields. (WIP)
- **Retrieve**: Get details of a citizen by ID.

### Service Architecture

The project is structured as a **multi-module Maven project**:

- **`citizen-registry-domain`**: Contains the citizen entity and domain logic.
- **`citizen-registry-service`**: Implements the service layer and API endpoints.
- **`citizen-registry-test`**: Unit and integration tests for ensuring the correctness of the service.
- **`citizen-registry-client`**: Provides a CLI for interacting with the API, simulating user input.

## Key Technologies

- **Spring Boot**: A framework for building Java-based applications.
- **Spring Data JPA**: For managing the persistence of citizen records.
- **Swagger**: API documentation for easy exploration of endpoints.
- **JUnit 5**: Framework for writing unit and integration tests.
- **Mockito**: For mocking dependencies in unit tests.
- **Rest-Assured**: For integration testing the RESTful APIs.
- **H2 Database**: In-memory database for testing and development.
- **Lombok**: To reduce boilerplate code for entities and DTOs.

## Current State

At the moment, the project is focused on:

1. **Service Layer**: Implementation of the core business logic for citizen data management.
2. **API Layer**: RESTful controllers for handling client requests.
3. **Unit and Integration Tests**: Basic tests for verifying the correctness of logic and API interactions. (WIP)
4. **Command-Line Interface (CLI)**: A basic client to interact with the API.

The project is fully functional for local development and testing.

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

### Future Security Enhancements

- **OAuth2 Authentication**: For secure API access.
- open to other things.

