Capturex

Minimal vertical-slice demo for creating a LearningArtifact via POST.

Purpose
- Demonstrate a small, testable backend flow: HTTP request → business logic → persistence.

Key behavior
- POST /learning-artifacts: accepts a JSON with `descripcion`, `leccionAprendida`, `url`.
- Validates required fields and returns the created resource with generated `id`.

Tech
- Java, JUnit 5, Mockito, Spring Data JPA, H2 (tests).

Run tests
```bash
mvn -U clean test
```

Note
- Ensure a JDK (javac) is installed and on PATH before running Maven.