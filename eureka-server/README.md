# Eureka Server

## Overview
The Eureka Server provides service discovery for the Good Job microservices ecosystem. It allows services to find and communicate with each other without hardcoding hostname and port. Each service registers with Eureka and sends heartbeats to indicate its availability.

## Architecture
- **Spring Cloud Netflix Eureka Server**: Core service registry and discovery server
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Security (optional)**: For securing the Eureka dashboard

### Key Components:
- **EurekaServerApplication**: Main application class with `@EnableEurekaServer` annotation
- **Configuration**: Settings for self-preservation mode, registry fetch/update intervals

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Local Development
1. Clone the repository
2. Navigate to the eureka-server directory:
   ```bash
   cd eureka-server
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
   docker build -t goodjob/eureka-server .
   ```
2. Run the container:
   ```bash
   docker run -p 8761:8761 goodjob/eureka-server
   ```

### Using Docker Compose
```bash
docker-compose up -d eureka-server
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Eureka server runs | 8761 |
| `EUREKA_CLIENT_REGISTER_WITH_EUREKA` | Whether to register with Eureka | false |
| `EUREKA_CLIENT_FETCH_REGISTRY` | Whether to fetch registry | false |
| `EUREKA_SERVER_ENABLE_SELF_PRESERVATION` | Enable/disable self-preservation mode | true |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | default |

## API Endpoints
- **Eureka Dashboard**: `http://localhost:8761/`
- **Service Registry**: `http://localhost:8761/eureka/apps`
- **Health Check**: `http://localhost:8761/actuator/health`
- **Info**: `http://localhost:8761/actuator/info`

## Inter-Service Communication
Eureka Server is a central component that enables communication between all microservices:
- All microservices register with Eureka Server on startup
- Services query Eureka to discover other services' locations
- Eureka provides load balancing through integration with Spring Cloud LoadBalancer

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring Eureka Server health and performance
- **Distributed Tracing**: Integrated with OpenTelemetry for distributed tracing, sending data to Tempo

### Monitoring Setup
The service is pre-configured to work with the observability stack:
- Prometheus scrapes metrics from `/actuator/prometheus`
- Loki collects logs via the Docker logging driver
- Tempo receives trace data via OpenTelemetry
- Grafana provides visualization through pre-configured dashboards

## Troubleshooting
- **Service Registration Issues**: Check network connectivity and ensure correct configuration of `eureka.client.serviceUrl.defaultZone`
- **High CPU Usage**: May indicate too many registry fetches; adjust `eureka.server.response-cache-update-interval-ms`
- **Memory Leaks**: Monitor heap usage and adjust JVM parameters if needed 