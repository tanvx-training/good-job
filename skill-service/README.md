# Skill Service

## Overview
The Skill Service manages skills and competencies in the Good Job platform. It provides a centralized repository of skills that can be associated with candidates, jobs, and companies. This service enables standardized skill tagging, skill endorsements, and skill-based matching between candidates and job postings.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data JPA**: For data persistence
- **PostgreSQL**: Primary database for storing skill information
- **Spring Cloud OpenFeign**: For inter-service communication
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Cloud Stream**: For event-driven communication (optional)

### Key Components:
- **SkillServiceApplication**: Main application class
- **Domain Models**: Skill, SkillCategory, SkillLevel, etc.
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
2. Navigate to the skill-service directory:
   ```bash
   cd skill-service
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
   docker build -t goodjob/skill-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8085:8085 goodjob/skill-service
   ```

### Using Docker Compose
```bash
docker-compose up -d skill-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Skill service runs | 8085 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/skill_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Skill Management**:
  - `GET /api/skills/{id}` - Get skill by ID
  - `GET /api/skills` - Get all skills (with pagination and filtering)
  - `POST /api/skills` - Create new skill
  - `PUT /api/skills/{id}` - Update skill
  - `DELETE /api/skills/{id}` - Delete skill

- **Skill Categories**:
  - `GET /api/skills/categories` - Get all skill categories
  - `GET /api/skills/categories/{id}` - Get specific category
  - `POST /api/skills/categories` - Create new skill category
  - `PUT /api/skills/categories/{id}` - Update skill category
  - `DELETE /api/skills/categories/{id}` - Delete skill category

- **Skill Levels**:
  - `GET /api/skills/levels` - Get all skill levels
  - `GET /api/skills/levels/{id}` - Get specific level
  - `POST /api/skills/levels` - Create new skill level
  - `PUT /api/skills/levels/{id}` - Update skill level
  - `DELETE /api/skills/levels/{id}` - Delete skill level

- **Skill Search**:
  - `GET /api/skills/search` - Search skills by name, category, etc.
  - `GET /api/skills/popular` - Get most popular skills

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Skill Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Candidate Service**: To provide skills for candidate profiles
- **Job Service**: To provide required skills for job definitions
- **Posting Service**: To enable skill-based job matching

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring skill creation, updates, and search operations
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
- **Performance Issues**: Monitor database query performance and consider indexing strategies for skill search operations 