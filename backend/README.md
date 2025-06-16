# M2 Backend (Spring Boot)

RESTful API backend for the M2 platform, providing user management, authentication, board, estimate, and material endpoints.

---

## Tech Stack

- Java 17+
- Spring Boot 3+
- Spring Data JPA
- Spring Security (JWT)
- H2/PostgreSQL/MySQL (configurable)
- Maven

---

## Setup & Run

### 1. Prerequisites
- Java 17 or newer
- Maven 3.8+

### 2. Environment Variables

Configure via `src/main/resources/application.yml` or environment variables:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `SERVER_PORT` (default: 8080)

### 3. Run locally

```bash
mvn spring-boot:run
```

App runs at [http://localhost:8080](http://localhost:8080)

### 4. Build

```bash
mvn clean package
```

### 5. Run with Docker

```bash
docker build -t m2-backend .
docker run -p 8080:8080 m2-backend
```

---

## API Docs

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Features

- User registration, login, and JWT authentication
- Board CRUD (공지사항/FAQ/Q&A)
- Estimate submission and management
- Material management
- Picture/file upload
- Global exception handling with custom error responses

---

## Project Structure

```
backend/
├── src/main/java/com/m2/   # Main Java source
│   ├── controller/         # REST controllers
│   ├── service/            # Business logic
│   ├── repository/         # JPA repositories
│   ├── domain/             # Entity classes
│   ├── dto/                # Data Transfer Objects
│   ├── config/             # Security/configuration
│   └── ...
├── src/main/resources/     # application.yml, static, templates
├── pom.xml
└── ...
```

---

## Testing

```bash
mvn test
```
