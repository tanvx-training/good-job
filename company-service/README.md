# Company Service

## Overview
The Company Service manages all company-related operations in the Good Job platform. It handles company profiles, locations, departments, and employee management. This service enables employers to create and maintain their company presence, post jobs, and manage their recruitment process.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing company information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication (optional)

### Key Components:
- **CompanyServiceApplication**: Main application class
- **Domain Models**: Company, Location, Department, Employee, etc.
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
2. Navigate to the company-service directory:
   ```bash
   cd company-service
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
   docker build -t goodjob/company-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8082:8082 goodjob/company-service
   ```

### Using Docker Compose
```bash
docker-compose up -d company-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Company service runs | 8082 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/company_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Company Profile**:
  - `GET /api/companies/{id}` - Get company by ID
  - `GET /api/companies` - Get all companies (with pagination)
  - `POST /api/companies` - Create new company profile
  - `PUT /api/companies/{id}` - Update company profile
  - `DELETE /api/companies/{id}` - Delete company profile

- **Company Locations**:
  - `GET /api/companies/{companyId}/locations` - Get all locations for a company
  - `GET /api/companies/{companyId}/locations/{locationId}` - Get specific location
  - `POST /api/companies/{companyId}/locations` - Add new location
  - `PUT /api/companies/{companyId}/locations/{locationId}` - Update location
  - `DELETE /api/companies/{companyId}/locations/{locationId}` - Delete location

- **Company Departments**:
  - `GET /api/companies/{companyId}/departments` - Get all departments
  - `GET /api/companies/{companyId}/departments/{departmentId}` - Get specific department
  - `POST /api/companies/{companyId}/departments` - Create new department
  - `PUT /api/companies/{companyId}/departments/{departmentId}` - Update department
  - `DELETE /api/companies/{companyId}/departments/{departmentId}` - Delete department

- **Company Employees**:
  - `GET /api/companies/{companyId}/employees` - Get all employees
  - `GET /api/companies/{companyId}/employees/{employeeId}` - Get specific employee
  - `POST /api/companies/{companyId}/employees` - Add new employee
  - `PUT /api/companies/{companyId}/employees/{employeeId}` - Update employee
  - `DELETE /api/companies/{companyId}/employees/{employeeId}` - Remove employee

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Company Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Job Service**: To manage job postings associated with the company
- **Industry Service**: To retrieve industry information
- **Benefit Service**: To manage company benefits
- **Notification Service**: To send notifications about company updates
- **Mail Service**: For email communications

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring company registrations, profile updates, and employee management
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