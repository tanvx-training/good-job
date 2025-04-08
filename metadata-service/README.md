# Benefit Service

## Overview
The Benefit Service manages employee benefits and perks in the Good Job platform. It provides a centralized repository of benefits that can be associated with companies and job postings. This service enables companies to showcase their employee benefits and allows candidates to search for jobs with specific benefits, enhancing the job search and recruitment experience.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing benefit information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication (optional)

### Key Components:
- **BenefitServiceApplication**: Main application class
- **Domain Models**: Benefit, BenefitCategory, CompanyBenefit, etc.
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
2. Navigate to the benefit-service directory:
   ```bash
   cd benefit-service
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
   docker build -t goodjob/benefit-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8088:8088 goodjob/benefit-service
   ```

### Using Docker Compose
```bash
docker-compose up -d benefit-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Benefit service runs | 8088 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/benefit_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Benefit Management**:
  - `GET /api/benefits/{id}` - Get benefit by ID
  - `GET /api/benefits` - Get all benefits (with pagination)
  - `POST /api/benefits` - Create new benefit
  - `PUT /api/benefits/{id}` - Update benefit
  - `DELETE /api/benefits/{id}` - Delete benefit

- **Benefit Category Management**:
  - `GET /api/benefits/categories` - Get all benefit categories
  - `GET /api/benefits/categories/{id}` - Get specific category
  - `POST /api/benefits/categories` - Create new benefit category
  - `PUT /api/benefits/categories/{id}` - Update benefit category
  - `DELETE /api/benefits/categories/{id}` - Delete benefit category

- **Company Benefits**:
  - `GET /api/companies/{companyId}/benefits` - Get all benefits for a company
  - `POST /api/companies/{companyId}/benefits` - Add benefit to company
  - `DELETE /api/companies/{companyId}/benefits/{benefitId}` - Remove benefit from company

- **Job Posting Benefits**:
  - `GET /api/postings/{postingId}/benefits` - Get all benefits for a job posting
  - `POST /api/postings/{postingId}/benefits` - Add benefit to job posting
  - `DELETE /api/postings/{postingId}/benefits/{benefitId}` - Remove benefit from job posting

- **Benefit Search**:
  - `GET /api/benefits/search` - Search benefits by name, description, etc.
  - `GET /api/benefits/popular` - Get most popular benefits

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Benefit Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Company Service**: To associate benefits with companies
- **Posting Service**: To associate benefits with job postings
- **Job Service**: To provide benefit information for job definitions

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring benefit creation, updates, and usage statistics
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
- **Performance Issues**: Monitor database query performance and consider indexing strategies for benefit search operations 