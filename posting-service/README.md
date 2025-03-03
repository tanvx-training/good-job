# Posting Service

## Overview
The Posting Service manages job posting operations in the Good Job platform. It handles the creation, publication, and lifecycle management of job postings. This service enables companies to advertise job openings, set application deadlines, and manage the visibility of their postings.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing posting information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication

### Key Components:
- **PostingServiceApplication**: Main application class
- **Domain Models**: JobPosting, PostingStatus, ApplicationDeadline, etc.
- **Repositories**: JPA repositories for data access
- **Services**: Business logic implementation
- **Controllers**: REST API endpoints
- **DTOs**: Data Transfer Objects for API requests/responses
- **Mappers**: For entity-DTO conversions
- **Event Publishers**: For publishing posting-related events

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL database
- Eureka Server (for service discovery)
- Config Server (for centralized configuration)

### Local Development
1. Clone the repository
2. Navigate to the posting-service directory:
   ```bash
   cd posting-service
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
   docker build -t goodjob/posting-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8084:8084 goodjob/posting-service
   ```

### Using Docker Compose
```bash
docker-compose up -d posting-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Posting service runs | 8084 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/posting_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |
| `SPRING_CLOUD_STREAM_BINDINGS_POSTING_EVENTS_OUT_DESTINATION` | Kafka topic for posting events | posting-events |

## API Endpoints
- **Job Posting Management**:
  - `GET /api/postings/{id}` - Get posting by ID
  - `GET /api/postings` - Get all postings (with pagination and filtering)
  - `POST /api/postings` - Create new job posting
  - `PUT /api/postings/{id}` - Update job posting
  - `DELETE /api/postings/{id}` - Delete job posting

- **Posting Status Management**:
  - `PUT /api/postings/{id}/status` - Update posting status (draft, published, closed, etc.)
  - `GET /api/postings/status/{status}` - Get postings by status

- **Posting Search**:
  - `GET /api/postings/search` - Search postings by various criteria
  - `GET /api/postings/company/{companyId}` - Get postings by company
  - `GET /api/postings/recent` - Get recently published postings

- **Application Management**:
  - `GET /api/postings/{postingId}/applications` - Get applications for a posting
  - `GET /api/postings/{postingId}/applications/count` - Get application count

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Posting Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Company Service**: To retrieve company information for postings
- **Job Service**: To retrieve job definitions for postings
- **Candidate Service**: To provide posting information for job applications
- **Notification Service**: To send notifications about posting status changes
- **Mail Service**: For email communications about new postings

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring posting creation, status changes, and search operations
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
- **Event Publishing Issues**: Check Kafka connectivity and topic configuration
- **Performance Issues**: Monitor database query performance and consider indexing strategies for search operations 