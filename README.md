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

## Containerization with Google Jib

This project uses Google Jib for containerizing the Spring Boot microservices. Jib builds optimized Docker and OCI images for Java applications without requiring a Docker daemon.

### Benefits of Using Jib

- **Fast**: Jib optimizes builds by separating your application into multiple layers and only rebuilding the layers that change.
- **Reproducible**: Jib builds images with the same contents regardless of the build environment.
- **Daemon-less**: Jib doesn't require a Docker daemon, making it ideal for CI/CD environments.
- **Optimized**: Jib creates smaller, more efficient images by separating your application into multiple layers.

### Building Container Images

#### Using Maven

To build a container image for a specific service:

```bash
cd <service-directory>
mvn compile jib:build
```

This will build and push the image to the configured registry (default: Docker Hub).

To build to your local Docker daemon:

```bash
cd <service-directory>
mvn compile jib:dockerBuild
```

#### Using the Build Script

A convenience script is provided to build all services:

```bash
# Build and push to Docker Hub
./build-containers.sh

# Build and push to a specific registry
./build-containers.sh ghcr.io

# Build to local Docker daemon
./build-containers.sh docker.io 1.0-SNAPSHOT local
```

### Jib Configuration

Jib is configured in the parent `pom.xml` and can be overridden in each service's `pom.xml`. The default configuration:

- Base image: `eclipse-temurin:17-jre-alpine`
- Image name: `goodjob/<service-name>`
- Tags: `latest` and `${project.version}`
- JVM flags: `-Xms256m -Xmx512m`
- Default profile: `prod`

To customize the Jib configuration for a specific service, modify the `jib-maven-plugin` section in that service's `pom.xml`.

### Running Containerized Services

After building the images, you can run the services using Docker Compose:

```bash
docker-compose up -d
```

Or run individual services:

```bash
docker run -p 8085:8085 goodjob/notification-service:1.0-SNAPSHOT
``` 