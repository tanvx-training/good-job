# Hướng dẫn Triển khai Stack Giám sát (Monitoring Stack)

## Giới thiệu

Dự án này sử dụng Grafana Observability Stack để theo dõi và giám sát microservices:

- **Prometheus**: Thu thập và lưu trữ metrics
- **Loki**: Thu thập và lưu trữ logs
- **Tempo**: Thu thập và lưu trữ distributed traces
- **Grafana**: Dashboard và visualization

## Bước 1: Khởi động Stack Giám sát

```bash
# Khởi động stack giám sát
docker-compose -f docker-compose.monitoring.yml up -d
```

## Bước 2: Cấu hình Microservices

### 2.1. Thêm Dependencies

Thêm các dependency sau vào file `pom.xml` của mỗi microservice:

```xml
<!-- Micrometer + Prometheus cho metrics -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

<!-- Logback JSON encoder từ Logstash -->
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>7.3</version>
</dependency>

<!-- OpenTelemetry dependencies cho distributed tracing -->
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-api</artifactId>
    <version>1.25.0</version>
</dependency>
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-sdk</artifactId>
    <version>1.25.0</version>
</dependency>
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-otlp</artifactId>
    <version>1.25.0</version>
</dependency>
<dependency>
    <groupId>io.opentelemetry.instrumentation</groupId>
    <artifactId>opentelemetry-spring-boot-starter</artifactId>
    <version>1.25.0-alpha</version>
</dependency>
```

### 2.2. Cấu hình Logback

Tạo file `src/main/resources/logback-spring.xml` trong mỗi microservice với nội dung sau:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>

    <springProperty scope="context" name="springAppName" source="spring.application.name"/>
    
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="net.logstash.logback.encoder.LogstashEncoder">
            <includeMdcKeyName>traceId</includeMdcKeyName>
            <includeMdcKeyName>spanId</includeMdcKeyName>
            <customFields>{"service":"${springAppName}"}</customFields>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

### 2.3. Cấu hình Application Properties

Thêm các cấu hình sau vào file `application.properties` hoặc `application.yml` của mỗi microservice:

```properties
# Cấu hình Micrometer/Prometheus cho Metrics
management.endpoints.web.exposure.include=health,info,prometheus,metrics
management.endpoint.health.show-details=always
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
management.metrics.tags.application=${spring.application.name}

# Cấu hình OpenTelemetry cho distributed tracing
otel.exporter.otlp.endpoint=http://tempo:4317
otel.resource.attributes=service.name=${spring.application.name}
otel.traces.sampler.probability=1.0

# Cấu hình tích hợp với Logback
logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]
```

### 2.4. Tạo Trace Filter

Tạo một filter để tự động thêm trace IDs vào MDC cho logging:

```java
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";
    private static final String SPAN_ID = "spanId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Kiểm tra xem đã có trace ID từ request header chưa
            String traceId = request.getHeader("X-B3-TraceId");
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString().replace("-", "");
            }
            
            String spanId = request.getHeader("X-B3-SpanId");
            if (spanId == null || spanId.isEmpty()) {
                spanId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            }
            
            // Đặt trace ID và span ID vào MDC
            MDC.put(TRACE_ID, traceId);
            MDC.put(SPAN_ID, spanId);
            
            // Cũng có thể lưu thông tin thêm về request
            MDC.put("requestUri", request.getRequestURI());
            MDC.put("requestMethod", request.getMethod());
            
            // Thêm trace ID và span ID vào response header
            response.addHeader("X-B3-TraceId", traceId);
            response.addHeader("X-B3-SpanId", spanId);
            
            filterChain.doFilter(request, response);
        } finally {
            // Xóa khỏi MDC để tránh memory leak
            MDC.remove(TRACE_ID);
            MDC.remove(SPAN_ID);
            MDC.remove("requestUri");
            MDC.remove("requestMethod");
        }
    }
}
```

## Bước 3: Truy cập Dashboards

- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **Loki**: http://localhost:3100
- **Tempo**: http://localhost:3200

## Bước 4: Tùy chỉnh Metrics và Traces

### Thêm Custom Metrics

```java
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final Counter orderCounter;
    
    public OrderService(MeterRegistry registry) {
        // Khởi tạo counter cho việc theo dõi số lượng đơn hàng
        this.orderCounter = registry.counter("orders.processed", "type", "online");
    }
    
    public void processOrder() {
        // Logic xử lý đơn hàng
        
        // Tăng counter mỗi khi xử lý đơn hàng
        orderCounter.increment();
    }
}
```

### Thêm Custom Spans cho Tracing

```java
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final Tracer tracer;
    
    public PaymentService(Tracer tracer) {
        this.tracer = tracer;
    }
    
    public void processPayment(String orderId, double amount) {
        // Tạo span mới để theo dõi quá trình xử lý thanh toán
        Span span = tracer.spanBuilder("payment-processing")
                .setAttribute("orderId", orderId)
                .setAttribute("amount", amount)
                .startSpan();
        
        try (Scope scope = span.makeCurrent()) {
            // Logic xử lý thanh toán
            
            // Có thể thêm event trong span
            span.addEvent("payment-authorized");
            
            // Thêm thông tin chi tiết
            span.setAttribute("paymentMethod", "credit-card");
        } catch (Exception e) {
            // Ghi nhận lỗi trong span
            span.recordException(e);
            span.setStatus(io.opentelemetry.api.trace.StatusCode.ERROR, e.getMessage());
            throw e;
        } finally {
            // Kết thúc span
            span.end();
        }
    }
}
``` 