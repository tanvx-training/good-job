# Speciality Service

## Overview
The Speciality Service manages professional specialities and areas of expertise in the Good Job platform. It provides a centralized repository of specialities that can be associated with candidates, jobs, and companies. This service enables precise categorization of professional domains, helping candidates find relevant job opportunities and companies find qualified candidates.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing speciality information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication (optional)

### Key Components:
- **SpecialityServiceApplication**: Main application class
- **Domain Models**: Speciality, SpecialityGroup, etc.
- **Repositories**: JPA repositories for data access
- **Services**: Business logic implementation
- **Controllers**: REST API endpoints
- **DTOs**: Data Transfer Objects for API requests/responses
- **Mappers**: For entity-DTO conversions

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL database
- Eureka Server (for service discovery)
- Config Server (for centralized configuration)

### Local Development
1. Clone the repository
2. Navigate to the speciality-service directory:
   ```bash
   cd speciality-service
   ```
3. Configure the database connection in `application.yml` or via environment variables
4. Build the application:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   
### Docker Deployment
1. Build the Docker image:
   ```bash
   docker build -t goodjob/speciality-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8087:8087 goodjob/speciality-service
   ```

### Using Docker Compose
```bash
docker-compose up -d speciality-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Speciality service runs | 8087 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/speciality_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Speciality Management**:
  - `GET /api/specialities/{id}` - Get speciality by ID
  - `GET /api/specialities` - Get all specialities (with pagination)
  - `POST /api/specialities` - Create new speciality
  - `PUT /api/specialities/{id}` - Update speciality
  - `DELETE /api/specialities/{id}` - Delete speciality

- **Speciality Group Management**:
  - `GET /api/specialities/groups` - Get all speciality groups
  - `GET /api/specialities/groups/{id}` - Get specific group
  - `POST /api/specialities/groups` - Create new speciality group
  - `PUT /api/specialities/groups/{id}` - Update speciality group
  - `DELETE /api/specialities/groups/{id}` - Delete speciality group

- **Speciality Search**:
  - `GET /api/specialities/search` - Search specialities by name, description, etc.
  - `GET /api/specialities/popular` - Get most popular specialities
  - `GET /api/specialities/groups/{groupId}/specialities` - Get specialities by group

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Speciality Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Candidate Service**: To provide specialities for candidate profiles
- **Job Service**: To associate jobs with relevant specialities
- **Posting Service**: To enable speciality-based job search
- **Company Service**: To associate companies with specialities

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring speciality creation, updates, and search operations
- **Distributed Tracing**: Integrated with OpenTelemetry for distributed tracing, sending data to Tempo

### Monitoring Setup
The service is pre-configured to work with the observability stack:
- Prometheus scrapes metrics from `/actuator/prometheus`
- Loki collects logs via the Docker logging driver
- Tempo receives trace data via OpenTelemetry
- Grafana provides visualization through pre-configured dashboards

## Troubleshooting
- **Database Connectivity**: Verify PostgreSQL connection parameters
- **Service Discovery Issues**: Check Eureka registration and client configuration
- **Authentication Problems**: Verify JWT configuration and connectivity to Authorization Service
- **Performance Issues**: Monitor database query performance and consider indexing strategies for speciality search operations 