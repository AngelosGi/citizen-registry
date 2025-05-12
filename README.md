# Citizen Registry RESTful Service

This project implements a **Citizen Registry RESTful service** using **Spring Boot**. The service allows for the management of citizen records, supporting **CRUD** operations. It includes configurations for containerization and deployment using Docker, Kubernetes, and Terraform.

---

## Project Overview

Manages citizen records with attributes like ID Number, Name, Gender, Birth Date, Tax ID, and Address.

**Core Features:**
- Create, Retrieve, Update, Delete (CRUD) operations for citizens.
- Search citizens based on various attributes.
- Custom exception handling for clear error feedback.

---

## Project Structure

A multi-module Maven project:
- **`domain`**: Core entity (`Citizen.java`) and validation.
- **`service`**: REST controllers, business logic, repository integration.
- **`client`**: Simple CLI client for API interaction.
- **`test`**: Unit, integration, and exception tests.

Key configuration directories:
- `kubernetes/`: Manifests for Kubernetes deployment.
- `terraform/`: Infrastructure as Code for AWS deployment.

---

## Key Technologies

- **Backend**: Java 21, Spring Boot 3.4, Spring Data JPA, PostgreSQL
- **Testing**: JUnit 5, Mockito, Rest-Assured
- **API Docs**: Swagger/OpenAPI (via Springdoc)
- **Containerization**: Docker, Docker Compose
- **Orchestration**: Kubernetes
- **Infrastructure**: Terraform (AWS)
- **CI/CD**: GitHub Actions

---

## API Endpoints

The service exposes RESTful endpoints under the base path `/api/citizens`.

**Main Endpoints:**
- `POST /api/citizens`: Create a new citizen.
- `GET /api/citizens/{id}`: Retrieve a citizen by ID.
- `PUT /api/citizens/{id}`: Update an existing citizen.
- `DELETE /api/citizens/{id}`: Delete a citizen by ID.
- `GET /api/citizens`: Search for citizens (supports query parameters like `firstName`, `lastName`, etc.).

*Refer to the Swagger UI (usually at `/swagger-ui.html` when running) for detailed request/response schemas.*

---

## Development Setup

### Prerequisites

- Java 21
- Maven 3.9+
- Docker Desktop (or Docker Engine + Compose CLI)

### 1. Local Build & Run (Maven)

1.  Clone: `git clone https://github.com/AngelosGi/citizen-registry.git && cd citizen-registry`
2.  Build: `./mvnw clean install`
3.  Run: `./mvnw spring-boot:run -pl service`
    *(App available at `http://localhost:8080`)*

### 2. Running with Docker Compose

Starts the application and a PostgreSQL database.

1.  **Create `.env` file** in the project root (see `docker-compose.yml` for expected variables like `DB_NAME`, `DB_USER`, `DB_PASSWORD`). Example:
    ```env
    DB_NAME=citizens
    DB_USER=admin
    DB_PASSWORD=adminpass
    ```
2.  **Run**: `docker-compose up -d --build`
    *(App available at `http://localhost/api/citizens`)*
3.  **Stop**: `docker-compose down`

---

## Deployment

Detailed deployment instructions for Docker, Kubernetes, and Terraform (AWS) can be found in [DEPLOYMENT.md](./DEPLOYMENT.md).

---

Information about the testing strategy and future roadmap can be found in [CONTRIBUTING.md] WIP (./CONTRIBUTING.md)
