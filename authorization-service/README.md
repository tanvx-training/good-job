# Authorization Service

## Overview
The Authorization Service manages authentication and authorization for the Good Job platform. It handles user registration, login, token issuance, and validation. This service implements OAuth 2.0 and OpenID Connect protocols using Spring Authorization Server, providing secure access to protected resources across all microservices.

## Architecture
- **Spring Authorization Server**: Core OAuth 2.0 and OpenID Connect implementation
- **Spring Security**: For authentication and authorization
- **Spring Data JPA**: For user data persistence
- **PostgreSQL**: Database for storing user credentials and authorization data
- **Redis (optional)**: For token storage and session management
- **Spring Boot Actuator**: For health checks and monitoring

### Key Components:
- **AuthorizationServerApplication**: Main application class
- **SecurityConfig**: Core security configuration
- **UserDetailsService**: Custom implementation for user authentication
- **JwtCustomizer**: Customizes JWT tokens with additional claims
- **OAuth2 Endpoints**: Token issuance, introspection, and revocation

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL database
- Redis (optional)

### Local Development
1. Clone the repository
2. Navigate to the authorization-service directory:
   ```bash
   cd authorization-service
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
   docker build -t goodjob/authorization-service .
   ```
2. Run the container:
   ```bash
   docker run -p 9000:9000 goodjob/authorization-service
   ```

### Using Docker Compose
```bash
docker-compose up -d authorization-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Authorization server runs | 9000 |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | jdbc:postgresql://postgres:5432/auth_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |
| `SPRING_REDIS_HOST` | Redis host (if used) | redis |
| `SPRING_REDIS_PORT` | Redis port | 6379 |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `JWT_ACCESS_TOKEN_EXPIRATION` | Access token expiration time (seconds) | 3600 |
| `JWT_REFRESH_TOKEN_EXPIRATION` | Refresh token expiration time (seconds) | 86400 |

## API Endpoints
- **Token Endpoint**: `POST /oauth2/token` - Obtain access and refresh tokens
- **Authorization Endpoint**: `GET /oauth2/authorize` - OAuth 2.0 authorization endpoint
- **Token Introspection**: `POST /oauth2/introspect` - Validate tokens
- **Token Revocation**: `POST /oauth2/revoke` - Revoke tokens
- **User Registration**: `POST /api/auth/register` - Register new users
- **User Info**: `GET /userinfo` - Get authenticated user information
- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Authorization Service interacts with other services in the following ways:
- **API Gateway**: Validates tokens for incoming requests
- **All Microservices**: Provides authentication and authorization through token validation
- **User-related Services**: May communicate with candidate-service or company-service for additional user information

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring authentication attempts, token issuance, and error rates
- **Distributed Tracing**: Integrated with OpenTelemetry for distributed tracing, sending data to Tempo

### Monitoring Setup
The service is pre-configured to work with the observability stack:
- Prometheus scrapes metrics from `/actuator/prometheus`
- Loki collects logs via the Docker logging driver
- Tempo receives trace data via OpenTelemetry
- Grafana provides visualization through pre-configured dashboards

## Troubleshooting
- **Token Validation Issues**: Check JWT signing keys and token expiration settings
- **Database Connectivity**: Verify PostgreSQL connection parameters
- **High CPU/Memory Usage**: Monitor token generation rate and consider caching strategies
- **Login Failures**: Check user credentials in the database and authentication logs 