# Mail Service

## Overview
The Mail Service handles email communication in the Good Job platform. It manages the creation, templating, and delivery of transactional and marketing emails to users. This service enables important notifications such as account verification, password reset, job application updates, and promotional communications, enhancing user engagement and platform functionality.

## Architecture
- **Spring Boot**: Core application framework
- **Spring Mail**: For email sending capabilities
- **Spring Data MongoDB**: For email template and history persistence
- **MongoDB**: NoSQL database for storing email information
- **Apache Kafka**: For event-driven email processing
- **Thymeleaf**: For email template rendering
- **Spring Security**: For authentication and authorization
- **Spring Boot Actuator**: For health checks and monitoring

### Key Components:
- **MailServiceApplication**: Main application class
- **Domain Models**: EmailTemplate, EmailHistory, EmailType, etc.
- **Repositories**: MongoDB repositories for data access
- **Services**: Business logic implementation
- **Controllers**: REST API endpoints
- **Kafka Listeners**: For consuming email events
- **Template Engine**: For rendering email content
- **DTOs**: Data Transfer Objects for API requests/responses

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MongoDB database
- Kafka broker
- SMTP server
- Eureka Server (for service discovery)
- Config Server (for centralized configuration)

### Local Development
1. Clone the repository
2. Navigate to the mail-service directory:
   ```bash
   cd mail-service
   ```
3. Configure the database, messaging, and SMTP connections in `application.yml` or via environment variables
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
   docker build -t goodjob/mail-service .
   ```
2. Run the container:
   ```bash
   docker run -p 8086:8086 goodjob/mail-service
   ```

### Using Docker Compose
```bash
docker-compose up -d mail-service
```

## Environment Variables
| Variable | Description | Default Value |
|----------|-------------|---------------|
| `SERVER_PORT` | Port on which Mail service runs | 8086 |
| `SPRING_DATA_MONGODB_URI` | MongoDB connection URI | mongodb://mongodb:27017/mail_db |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Kafka bootstrap servers | kafka:9092 |
| `SPRING_KAFKA_CONSUMER_GROUP_ID` | Kafka consumer group ID | mail-group |
| `MAIL_TOPIC` | Kafka topic for mail events | mail-events |
| `SPRING_MAIL_HOST` | SMTP server host | smtp.example.com |
| `SPRING_MAIL_PORT` | SMTP server port | 587 |
| `SPRING_MAIL_USERNAME` | SMTP server username | mail@goodjob.com |
| `SPRING_MAIL_PASSWORD` | SMTP server password | password |
| `SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH` | Enable SMTP authentication | true |
| `SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE` | Enable STARTTLS | true |
| `MAIL_FROM_ADDRESS` | Default sender email address | noreply@goodjob.com |
| `MAIL_FROM_NAME` | Default sender name | Good Job Platform |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | Eureka server URL | http://eureka-server:8761/eureka/ |
| `SPRING_CLOUD_CONFIG_URI` | Config server URL | http://config-server:8888 |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` | JWT issuer URI | http://authorization-service:9000 |

## API Endpoints
- **Email Management**:
  - `POST /api/mail/send` - Send an email
  - `POST /api/mail/send-template` - Send a templated email
  - `GET /api/mail/history/{id}` - Get email history by ID
  - `GET /api/mail/history/user/{userId}` - Get email history for a user

- **Email Template Management**:
  - `GET /api/mail/templates/{id}` - Get template by ID
  - `GET /api/mail/templates` - Get all templates
  - `POST /api/mail/templates` - Create new template
  - `PUT /api/mail/templates/{id}` - Update template
  - `DELETE /api/mail/templates/{id}` - Delete template

- **Health Check**: `GET /actuator/health`

## Inter-Service Communication
The Mail Service interacts with other services in the following ways:
- **Authorization Service**: For user authentication and authorization
- **Candidate Service**: Receives events for candidate-related emails
- **Company Service**: Receives events for company-related emails
- **Posting Service**: Receives events for job posting-related emails
- **Notification Service**: Complements in-app notifications with email notifications

## Logging & Monitoring
- **Logging**: Uses SLF4J with Logback, logs are sent to Loki
- **Metrics**: Exposes metrics via Spring Boot Actuator and Micrometer
- **Prometheus Integration**: Metrics are scraped by Prometheus at `/actuator/prometheus`
- **Grafana Dashboards**: Pre-configured dashboards for monitoring email delivery rates, failures, and Kafka consumer lag
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
- **SMTP Connection Problems**: Verify SMTP server settings and credentials
- **Template Rendering Issues**: Check template syntax and variable mapping
- **Service Discovery Issues**: Check Eureka registration and client configuration
- **Authentication Problems**: Verify JWT configuration and connectivity to Authorization Service 