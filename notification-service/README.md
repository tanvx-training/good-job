# Notification Service

## Overview
The Notification Service manages real-time notifications in the Good Job platform. It handles the creation, storage, and delivery of notifications to users across various channels (in-app, WebSocket, etc.). This service enables timely updates about job applications, profile views, messages, and other important events, enhancing user engagement and experience.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Data MongoDB**: For notification data persistence
- **MongoDB**: NoSQL database for storing notification information
- **Spring WebSocket**: For real-time notification delivery
- **Apache Kafka**: For event-driven notification processing
- **Redis**: For WebSocket session management and caching
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring

### Key Components:
- **NotificationServiceApplication**: Main application class
- **Domain Models**: Notification, NotificationType, NotificationStatus, etc.
- **Repositories**: MongoDB repositories for data access
- **Services**: Business logic implementation
- **Controllers**: REST API and WebSocket endpoints
- **Kafka Listeners**: For consuming notification events
- **WebSocket Configuration**: For real-time communication
- **DTOs**: Data Transfer Objects for API requests/responses

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MongoDB database
- Redis server
- Kafka broker
- Eureka Server (for service discovery)
- Config Server (for centralized configuration)

### Local Development
1. Clone the repository
2. Navigate to the notification-service directory:
   ```bash
   cd notification-service
   ```
3. Configure the database and messaging connections in `application.yml` or via environment variables
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
   docker build -t goodjob/notification-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8085:8085 goodjob/notification-service
   ```

### Using Docker Compose
```bash
docker-compose up -d notification-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Notification service runs | 8085 |
| `SPRING_DATA_MONGODB_URI` | MongoDB connection URI | mongodb://mongodb:27017/notification_db |
| `SPRING_REDIS_HOST` | Redis host | redis |
| `SPRING_REDIS_PORT` | Redis port | 6379 |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Kafka bootstrap servers | kafka:9092 |
| `SPRING_KAFKA_CONSUMER_GROUP_ID` | Kafka consumer group ID | notification-group |
| `NOTIFICATION_TOPIC` | Kafka topic for notification events | notification-events |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Notification Management**:
  - `GET /api/notifications/{id}` - Get notification by ID
  - `GET /api/notifications/user/{userId}` - Get notifications for a user
  - `GET /api/notifications/unread/count` - Get unread notification count
  - `POST /api/notifications` - Create new notification (admin/system)
  - `PUT /api/notifications/{id}/read` - Mark notification as read
  - `PUT /api/notifications/read-all` - Mark all notifications as read
  - `DELETE /api/notifications/{id}` - Delete notification

- **WebSocket Endpoints**:
  - `/ws/notifications` - WebSocket endpoint for real-time notifications
  - `/topic/user/{userId}/notifications` - User-specific notification topic

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Notification Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Candidate Service**: Receives events about application status changes
- **Company Service**: Receives events about company updates
- **Posting Service**: Receives events about job posting changes
- **Mail Service**: Triggers email notifications for important events

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring notification delivery, WebSocket connections, and Kafka consumer lag
- **Distributed Tracing**: Integrated with OpenTelemetry for distributed tracing, sending data to Tempo

### Monitoring Setup
The service is pre-configured to work with the observability stack:
- Prometheus scrapes metrics from `/actuator/prometheus`
- Loki collects logs via the Docker logging driver
- Tempo receives trace data via OpenTelemetry
- Grafana provides visualization through pre-configured dashboards

## Troubleshooting
- **MongoDB Connectivity**: Verify MongoDB connection parameters
- **Kafka Issues**: Check Kafka broker connectivity and consumer group status
- **WebSocket Connection Problems**: Verify Redis connectivity and WebSocket configuration
- **Service Discovery Issues**: Check Eureka registration and client configuration
- **Authentication Problems**: Verify JWT configuration and connectivity to Authorization Service 