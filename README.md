# Good Job - LinkedIn-like Platform

A LinkedIn-like platform built with Spring Boot and Microservices Architecture.

## Project Overview

This project implements a professional networking platform similar to LinkedIn using a microservices architecture. It includes features for job postings, company profiles, user skills, and more.

## Architecture

The system is built using the following components:

- **Microservices Architecture**: Separate services for different business domains
- **CQRS Pattern**: Command Query Responsibility Segregation for job-related operations
- **Spring Cloud Components**:
  - Eureka Server (Service Discovery)
  - API Gateway
  - Config Server
  - Authorization Service (OAuth2 with JWT)

## Microservices

1. **Authorization Service**: OAuth2 Authorization Server with JWT tokens
2. **Benefit Service**: Manages benefits data
3. **Industry Service**: Manages industry data
4. **Speciality Service**: Manages speciality data
5. **Job Service**: Manages job postings with CQRS pattern
6. **Posting Service**: Manages job applications and tracking
7. **Company Service**: Manages company profiles and metrics
8. **Skill Service**: Manages skill data

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Spring Cloud 2023.0.0
- Spring Authorization Server 1.2.1
- PostgreSQL
- Maven
- JWT for Authentication
- Docker (for containerization)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
- Docker (optional)

### Building the Project

```bash
mvn clean install
```

### Running the Services

1. Start the Eureka Server
2. Start the Config Server
3. Start the Authorization Service
4. Start other microservices

## API Documentation

API documentation is available through Springdoc OpenAPI at `/swagger-ui.html` for each service.

## Security

The application uses OAuth2 with JWT for authentication and authorization. All services are secured and require proper authentication.

## Database Schema

The project uses PostgreSQL with a comprehensive schema for jobs, companies, skills, industries, benefits, and salaries. 