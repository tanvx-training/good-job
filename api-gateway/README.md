# API Gateway

## Overview
The API Gateway serves as the single entry point for all client requests to the Good Job platform. It routes requests to appropriate microservices, handles cross-cutting concerns like authentication, rate limiting, and provides a unified API interface to clients. It simplifies client interactions by abstracting the underlying microservice architecture.

## Architecture
- **Spring Cloud Gateway**: Core API gateway framework
- **Spring Security**: For authentication and authorization
- **Spring Cloud LoadBalancer**: For client-side load balancing
- **Spring Cloud Circuit Breaker**: For resilience and fault tolerance
- **Redis**: For rate limiting and distributed session storage (optional)
- **Spring Boot Actuator**: For health checks and monitoring

### Key Components:
- **ApiGatewayApplication**: Main application class
- **Route Configuration**: Defines routing rules for incoming requests
- **Global Filters**: Apply to all routes (authentication, logging, etc.)
- **Route Predicates**: Determine which routes match a request
- **Route Filters**: Apply transformations to requests or responses

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Eureka Server (for service discovery)
- Config Server (for centralized configuration)

### Local Development
1. Clone the repository
2. Navigate to the api-gateway directory:
   ```bash
   cd api-gateway
   ```
3. Build the application:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   
### Docker Deployment
1. Build the Docker image:
   ```bash
   docker build -t goodjob/api-gateway .
   ```
2. Run the container:
   ```bash
   docker run -p 8080:8080 goodjob/api-gateway
   ```

### Using Docker Compose
```bash
docker-compose up -d api-gateway
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which API Gateway runs | 8080 |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_REDIS_HOST` | Redis host (for rate limiting) | redis |
| `SPRING_REDIS_PORT` | Redis port | 6379 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |
| `LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_CLOUD_GATEWAY` | Gateway logging level | INFO |

## API Endpoints
The API Gateway exposes endpoints for all microservices. Here are some key routes:

- **Authentication**: `/api/auth/**` → Routes to authorization-service
- **Candidates**: `/api/candidates/**` → Routes to candidate-service
- **Companies**: `/api/companies/**` → Routes to company-service
- **Jobs**: `/api/jobs/**` → Routes to job-service
- **Postings**: `/api/postings/**` → Routes to posting-service
- **Skills**: `/api/skills/**` → Routes to skill-service
- **Industries**: `/api/industries/**` → Routes to industry-service
- **Specialities**: `/api/specialities/**` → Routes to speciality-service
- **Benefits**: `/api/benefits/**` → Routes to benefit-service
- **Notifications**: `/api/notifications/**` → Routes to notification-service
- **Health Check**: `/actuator/health`
- **Gateway Routes**: `/actuator/gateway/routes`

## Inter-Service Communication
The API Gateway interacts with all microservices:
- Routes client requests to appropriate microservices
- Communicates with Eureka Server for service discovery
- Integrates with the Authorization Service for token validation
- Can implement circuit breakers to handle service failures gracefully

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring API Gateway performance, request rates, and error rates
- **Distributed Tracing**: Integrated with OpenTelemetry for distributed tracing, sending data to Tempo

### Monitoring Setup
The service is pre-configured to work with the observability stack:
- Prometheus scrapes metrics from `/actuator/prometheus`
- Loki collects logs via the Docker logging driver
- Tempo receives trace data via OpenTelemetry
- Grafana provides visualization through pre-configured dashboards

## Troubleshooting
- **Routing Errors**: Check route configurations and ensure destination services are registered with Eureka
- **Authentication Issues**: Verify JWT token configuration and connectivity to the Authorization Service
- **Performance Problems**: Monitor request latency metrics and adjust timeouts or circuit breaker settings
- **Rate Limiting**: Check Redis connectivity if rate limiting is enabled 