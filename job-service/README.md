# Job Service

## Overview
The Job Service manages job-related operations in the Good Job platform. It handles job definitions, categories, and job-specific attributes. This service provides the core functionality for defining job positions that can be posted by companies and applied to by candidates.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing job information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication (optional)

### Key Components:
- **JobServiceApplication**: Main application class
- **Domain Models**: Job, JobCategory, JobAttribute, etc.
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
2. Navigate to the job-service directory:
   ```bash
   cd job-service
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
   docker build -t goodjob/job-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8083:8083 goodjob/job-service
   ```

### Using Docker Compose
```bash
docker-compose up -d job-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Job service runs | 8083 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/job_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Job Management**:
  - `GET /api/jobs/{id}` - Get job by ID
  - `GET /api/jobs` - Get all jobs (with pagination and filtering)
  - `POST /api/jobs` - Create new job definition
  - `PUT /api/jobs/{id}` - Update job definition
  - `DELETE /api/jobs/{id}` - Delete job definition

- **Job Categories**:
  - `GET /api/jobs/categories` - Get all job categories
  - `GET /api/jobs/categories/{id}` - Get specific category
  - `POST /api/jobs/categories` - Create new job category
  - `PUT /api/jobs/categories/{id}` - Update job category
  - `DELETE /api/jobs/categories/{id}` - Delete job category

- **Job Attributes**:
  - `GET /api/jobs/{jobId}/attributes` - Get all attributes for a job
  - `GET /api/jobs/{jobId}/attributes/{attributeId}` - Get specific attribute
  - `POST /api/jobs/{jobId}/attributes` - Add new attribute to job
  - `PUT /api/jobs/{jobId}/attributes/{attributeId}` - Update job attribute
  - `DELETE /api/jobs/{jobId}/attributes/{attributeId}` - Delete job attribute

- **Job Search**:
  - `GET /api/jobs/search` - Search jobs by various criteria

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Job Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Company Service**: To retrieve company information for jobs
- **Posting Service**: To provide job definitions for job postings
- **Skill Service**: To manage required skills for jobs
- **Industry Service**: To associate jobs with industries
- **Speciality Service**: To associate jobs with specialities

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring job creation, updates, and search operations
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
- **Performance Issues**: Monitor database query performance and consider indexing strategies for search operations 