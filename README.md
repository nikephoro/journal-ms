# Journal API

A Spring Boot application for managing personal journal entries with secure authentication.

## Overview

This application provides a RESTful API for users to create and manage their personal journal entries. It features secure user authentication using JWT tokens and data persistence with MySQL.

## Technologies

- Java 21
- Spring Boot 3.1.5
- Spring Security with JWT
- MySQL Database
- Maven
- Swagger/OpenAPI Documentation
- JPA/Hibernate
- Lombok

## Features

- User Authentication
  - Registration
  - Login
  - Current user information retrieval
- Journal Entries Management
  - Create new entries
  - Retrieve all entries for authenticated user

## Configuration

### Database Setup

The application requires a MySQL database. Configure your database connection in `application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/journal_database
    username: journal_user
    password: journal_password
```

### Security

JWT token configuration is managed through the `application-dev.yml` file. The signing key is used for JWT token generation and validation.

## API Endpoints

### Authentication

- `POST /api/v1/auth/register` - Register a new user
- `POST /api/v1/auth/login` - Authenticate user and receive JWT token
- `GET /api/v1/auth/me` - Get current user information

### Journal Entries

- `POST /api/v1/journal/entries` - Create a new journal entry
- `GET /api/v1/journal/entries` - Retrieve all journal entries for authenticated user

## Documentation

API documentation is available through Swagger UI. Once the application is running, visit:
`http://localhost:8086/swagger-ui.html`

## Building and Running

To build the project:
```bash
mvn clean install
```

To run the application:
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

The application will start on port 8086 by default.

## Testing

The project includes comprehensive test coverage using:
- JUnit 5
- Mockito
- AssertJ

Run tests with:
```bash
mvn test
```

