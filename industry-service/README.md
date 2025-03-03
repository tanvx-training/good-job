# Industry Service

## Overview
The Industry Service manages industry classifications in the Good Job platform. It provides a centralized repository of industries that can be associated with companies and job postings. This service enables standardized industry categorization, helping candidates find relevant companies and job opportunities in their preferred sectors.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing industry information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication (optional)

### Key Components:
- **IndustryServiceApplication**: Main application class
- **Domain Models**: Industry, SubIndustry, etc.
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
2. Navigate to the industry-service directory:
   ```bash
   cd industry-service
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
   docker build -t goodjob/industry-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8086:8086 goodjob/industry-service
   ```

### Using Docker Compose
```bash
docker-compose up -d industry-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Industry service runs | 8086 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/industry_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Industry Management**:
  - `GET /api/industries/{id}` - Get industry by ID
  - `GET /api/industries` - Get all industries (with pagination)
  - `POST /api/industries` - Create new industry
  - `PUT /api/industries/{id}` - Update industry
  - `DELETE /api/industries/{id}` - Delete industry

- **Sub-Industry Management**:
  - `GET /api/industries/{industryId}/sub-industries` - Get all sub-industries for an industry
  - `GET /api/industries/{industryId}/sub-industries/{subIndustryId}` - Get specific sub-industry
  - `POST /api/industries/{industryId}/sub-industries` - Create new sub-industry
  - `PUT /api/industries/{industryId}/sub-industries/{subIndustryId}` - Update sub-industry
  - `DELETE /api/industries/{industryId}/sub-industries/{subIndustryId}` - Delete sub-industry

- **Industry Search**:
  - `GET /api/industries/search` - Search industries by name, description, etc.
  - `GET /api/industries/popular` - Get most popular industries

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Industry Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Company Service**: To provide industry classifications for companies
- **Job Service**: To associate jobs with relevant industries
- **Posting Service**: To enable industry-based job search

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring industry creation, updates, and search operations
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
- **Performance Issues**: Monitor database query performance and consider indexing strategies for industry search operations 