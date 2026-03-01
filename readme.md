# Capturex - Learning Artifact Management System

A minimal, testable backend API for capturing and managing learning artifacts built with **Test-Driven Development (TDD)** principles and **Vertical Slice Architecture**.

## Overview

Capturex is a Spring Boot application that allows users to create and store "Learning Artifacts" - records of things you've learned with project context. Each artifact captures:
- **Description**: What you learned
- **Lesson Learned**: Key takeaway or lesson
- **Project URL**: Link to the project where you learned it

## Architecture

This project follows a **Vertical Slice Architecture**, organizing code by feature rather than by layer.

## Technology Stack

- Java 17+ with Spring Boot 3.2.2
- Spring Web for REST endpoints
- Spring Data JPA with Hibernate
- H2 file-based database
- JUnit 5 & Mockito for testing
- Maven build tool

## Prerequisites

- Java 17 or higher with `javac` on PATH
- Maven 3.6+

## Running the Application

### Tests (TDD)
```bash
mvn clean test
```

Runs the automated test suite (unit and integration tests).

### Start Server
```bash
mvn spring-boot:run
```

Server runs on `http://localhost:8080`

### Run with Docker
```bash
docker compose up --build
```

Server runs on `http://localhost:8080`

Useful commands:
```bash
# Run in background
docker compose up --build -d

# Stop containers
docker compose down

# Stop and remove persisted H2 data volume
docker compose down -v
```

## API Usage

### Create Learning Artifact

**POST** `/learning-artifacts`

**Request:**
```json
{
  "description": "Learned about async/await",
  "lessonLearned": "Simplifies callback code",
  "projectUrl": "https://github.com/example/repo"
}
```

**Response:** 201 Created
```json
{
  "id": 1,
  "description": "Learned about async/await",
  "lessonLearned": "Simplifies callback code",
  "projectUrl": "https://github.com/example/repo"
}
```

**Errors:**
- 400: Validation error (blank fields, invalid URL, etc.)
- 404: Artifact not found (for delete)
- 500: Unexpected server error

### Get All Learning Artifacts

**GET** `/learning-artifacts`

**Response:** 200 OK
```json
[
  {
    "id": 1,
    "description": "Learned about async/await",
    "lessonLearned": "Simplifies callback code",
    "projectUrl": "https://github.com/example/repo"
  }
]
```

### Delete Learning Artifact

**DELETE** `/learning-artifacts/{id}`

**Response:** 204 No Content

### Generate Description Proposal

**POST** `/learning-artifacts/proposal`

**Request:**
```json
{
  "projectUrl": "https://github.com/example/repo"
}
```

**Response:** 200 OK
```json
{
  "description": "Short project summary proposal..."
}
```

## Project Structure

```
src/main/java/com/capturex/learningartifact/
├── adapters/
│   ├── github/
│   └── rest/
├── application/
├── domain/
│   └── exceptions/
└── infrastructure/
```

## Development Approach

This project uses **Test-Driven Development (TDD)**:
1. Write tests first
2. Run tests (they fail)
3. Implement code to pass tests
4. Refactor while keeping tests green

## Database

H2 file-based database configured in `application.properties`:
- URL: `jdbc:h2:file:./data/capturex;MODE=MySQL`
- Data persists between runs in the local `data/` directory
- Docker setup persists data using the `capturex-data` volume

## Future Features

- PUT for updates
- Search and filtering
- Pagination
- Authentication
- API documentation (Swagger/OpenAPI)

## Troubleshooting

**"Cannot load driver class"** → H2 must be in pom.xml without test scope

**"Bean not found"** → Add @Service, @Repository annotations

**Mockito errors** → Ensure mocked classes are interfaces

## Contributing

When adding features:
1. Follow vertical slice architecture
2. Write tests first (TDD)
3. Keep each slice self-contained
4. Run `mvn clean test` before committing
