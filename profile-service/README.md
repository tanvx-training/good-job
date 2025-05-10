# Candidate Service

## Overview
The Candidate Service manages all candidate-related operations in the Good Job platform. It handles candidate profiles, resumes, job applications, and career preferences. This service enables job seekers to create and maintain their professional profiles, apply for jobs, and track their application status.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing candidate information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication (optional)

### Key Components:
- **CandidateServiceApplication**: Main application class
- **Domain Models**: Candidate, Resume, Education, Experience, etc.
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
2. Navigate to the candidate-service directory:
   ```bash
   cd candidate-service
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
   docker build -t goodjob/candidate-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8081:8081 goodjob/candidate-service
   ```

### Using Docker Compose
```bash
docker-compose up -d candidate-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Candidate service runs | 8081 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/candidate_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Candidate Profile**:
  - `GET /api/candidates/{id}` - Get candidate by ID
  - `GET /api/candidates/me` - Get current candidate profile
  - `POST /api/candidates` - Create new candidate profile
  - `PUT /api/candidates/{id}` - Update candidate profile
  - `DELETE /api/candidates/{id}` - Delete candidate profile

- **Resume Management**:
  - `GET /api/candidates/{candidateId}/resumes` - Get all resumes for a candidate
  - `GET /api/candidates/{candidateId}/resumes/{resumeId}` - Get specific resume
  - `POST /api/candidates/{candidateId}/resumes` - Upload new resume
  - `PUT /api/candidates/{candidateId}/resumes/{resumeId}` - Update resume
  - `DELETE /api/candidates/{candidateId}/resumes/{resumeId}` - Delete resume

- **Job Applications**:
  - `GET /api/candidates/{candidateId}/applications` - Get all applications
  - `GET /api/candidates/{candidateId}/applications/{applicationId}` - Get specific application
  - `POST /api/candidates/{candidateId}/applications` - Submit job application
  - `PUT /api/candidates/{candidateId}/applications/{applicationId}` - Update application
  - `DELETE /api/candidates/{candidateId}/applications/{applicationId}` - Withdraw application

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Candidate Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Job Service**: To retrieve job details for applications
- **Skill Service**: To manage candidate skills
- **Notification Service**: To send notifications about application status changes
- **Mail Service**: For email communications

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring candidate registrations, profile updates, and application submissions
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
- **Performance Issues**: Monitor database query performance and consider indexing strategies 