# Common Library

## Overview
The Common Library provides shared code, utilities, and configurations for all microservices in the Good Job platform. It centralizes common functionality to promote code reuse, maintain consistency across services, and simplify development. This library includes exception handling, security configurations, data transfer objects, utility classes, and other cross-cutting concerns.

## Architecture
- **Spring Boot**: Core framework integration
- **Spring Security**: Common security configurations
- **Spring Data**: Common data access patterns
- **Spring Cloud**: Shared cloud configurations
- **Spring Validation**: Common validation utilities
- **Observability**: Shared monitoring and tracing configurations

### Key Components:
- **Exception Handling**: Global exception handlers and custom exceptions
- **Security**: Common security configurations and utilities
- **DTOs**: Shared data transfer objects
- **Enums**: Common enumeration types
- **Constants**: Shared constant values
- **Utilities**: Helper classes and utility methods
- **Validation**: Custom validators and validation groups
- **Auditing**: Common auditing configurations
- **Pagination**: Standardized pagination support
- **Observability**: Metrics, logging, and tracing configurations

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Local Development
1. Clone the repository
2. Navigate to the common-lib directory:
   ```bash
   cd common-lib
   ```
3. Build the library:
   ```bash
   mvn clean install
   ```
4. To use in other services, add the dependency to their pom.xml:
   ```xml
   <dependency>
       <groupId>com.goodjob</groupId>
       <artifactId>common-lib</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   ```

## Usage Examples

### Exception Handling
```java
// Using custom exceptions
throw new ResourceNotFoundException("User", "id", userId);

// In your service, add the global exception handler
@ControllerAdvice
public class GlobalExceptionHandler extends CommonExceptionHandler {
    // Add service-specific exception handling if needed
}
```

### Security Utilities
```java
// Get current user ID
String userId = SecurityUtils.getCurrentUserId();

// Check if user has specific role
boolean isAdmin = SecurityUtils.hasRole("ROLE_ADMIN");
```

### DTOs and Mappers
```java
// Using common DTOs
PageResponseDTO<UserDTO> response = new PageResponseDTO<>(userPage);

// Using common mapper configurations
@Mapper(config = CommonMapperConfig.class)
public interface UserMapper {
    // Mapping methods
}
```

### Auditing
```java
// Entity with common auditing
@Entity
public class User extends BaseEntity {
    // Entity fields
}
```

## Key Classes and Interfaces

| Class/Interface | Description |
|-----------------|-------------|
| `BaseEntity` | Base class for all entities with common fields (id, created/updated timestamps) |
| `BaseDTO` | Base class for all DTOs with common fields |
| `CommonExceptionHandler` | Global exception handler with standard error responses |
| `SecurityUtils` | Security utility methods |
| `PageRequestDTO` | Standardized pagination request |
| `PageResponseDTO` | Standardized pagination response |
| `ValidationGroups` | Common validation groups |
| `DateTimeUtils` | Date and time utility methods |
| `StringUtils` | String manipulation utilities |
| `LoggingAspect` | Common logging aspect |
| `MetricsConfig` | Common metrics configuration |
| `TracingConfig` | Common tracing configuration |

## Logging & Monitoring
The common library provides standardized configurations for:
- **Logging**: Common logging patterns and MDC setup
- **Metrics**: Standard metric names and tags
- **Tracing**: Common span naming and attribute conventions
- **Health Checks**: Standard health indicators

## Best Practices
- Always use the common library for cross-cutting concerns
- Extend base classes rather than duplicating functionality
- Follow the established naming conventions
- Contribute improvements back to the common library
- Keep service-specific code in the service, not in the common library
- Use the provided utility methods instead of reimplementing common functionality

## Troubleshooting
- **Dependency Conflicts**: Ensure compatible versions of dependencies
- **Bean Definition Conflicts**: Use conditional bean creation
- **Circular Dependencies**: Avoid circular references between common library and services 