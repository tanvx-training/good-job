# Config Server

## Overview
The Config Server provides centralized configuration management for all microservices in the Good Job platform. It stores configuration properties in a Git repository and serves them to the microservices at runtime, enabling dynamic configuration updates without service restarts.

## Architecture
- **Spring Cloud Config Server**: Core configuration server
- **Git Backend**: Stores configuration files (can be GitHub, GitLab, or local Git repository)
- **Spring Boot Actuator**: For health checks and monitoring
- **Spring Security**: For securing configuration endpoints
- **Spring Cloud Bus (optional)**: For broadcasting configuration changes

### Key Components:
- **ConfigServerApplication**: Main application class with `@EnableConfigServer` annotation
- **Security Configuration**: Authentication for accessing sensitive configuration
- **Encryption/Decryption**: Support for encrypted property values

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Git repository for storing configuration files

### Local Development
1. Clone the repository
2. Navigate to the config-server directory:
   ```bash
   cd config-server
   ```
3. Configure the Git repository URL in `application.yml` or via environment variables
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
   docker build -t goodjob/config-server .
   ```
2. Run the container:
   ```bash
   docker run -p 8888:8888 -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=<git-repo-url> goodjob/config-server
   ```

### Using Docker Compose
```bash
docker-compose up -d config-server
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Config server runs | 8888 |
| `SPRING_CLOUD_CONFIG_SERVER_GIT_URI` | Git repository URI for configuration | https://github.com/yourusername/goodjob-config |
| `SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME` | Git username (if private repo) | - |
| `SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD` | Git password (if private repo) | - |
| `SPRING_CLOUD_CONFIG_SERVER_GIT_DEFAULT_LABEL` | Git branch to use | main |
| `SPRING_SECURITY_USER_NAME` | Username for basic auth | configuser |
| `SPRING_SECURITY_USER_PASSWORD` | Password for basic auth | configpassword |
| `ENCRYPT_KEY` | Key for property encryption/decryption | - |

## API Endpoints
- **Configuration**: `http://localhost:8888/{service-name}/{profile}`
- **Encrypted Properties**: `http://localhost:8888/encrypt` and `http://localhost:8888/decrypt`
- **Health Check**: `http://localhost:8888/actuator/health`
- **Refresh**: `http://localhost:8888/actuator/refresh` (POST)
- **Bus Refresh (if enabled)**: `http://localhost:8888/actuator/busrefresh` (POST)

## Inter-Service Communication
Config Server interacts with all microservices in the following ways:
- All microservices fetch their configuration from Config Server on startup
- Services can refresh their configuration at runtime via the `/actuator/refresh` endpoint
- With Spring Cloud Bus, configuration changes can be broadcast to all services

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring Config Server health and performance
- **Distributed Tracing**: Integrated with OpenTelemetry for distributed tracing, sending data to Tempo

### Monitoring Setup
The service is pre-configured to work with the observability stack:
- Prometheus scrapes metrics from `/actuator/prometheus`
- Loki collects logs via the Docker logging driver
- Tempo receives trace data via OpenTelemetry
- Grafana provides visualization through pre-configured dashboards

## Troubleshooting
- **Git Connectivity Issues**: Ensure the Git repository is accessible and credentials are correct
- **Configuration Not Updating**: Check if the service has the Spring Cloud Config Client dependency and is properly configured
- **Encryption/Decryption Errors**: Verify the `ENCRYPT_KEY` is properly set and consistent across restarts 