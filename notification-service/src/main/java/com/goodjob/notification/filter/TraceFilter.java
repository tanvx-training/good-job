package com.goodjob.notification.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceFilter extends OncePerRequestFilter {

  private static final String TRACE_ID = "traceId";
  private static final String SPAN_ID = "spanId";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String traceId = request.getHeader("X-B3-TraceId");
      if (traceId == null || traceId.isEmpty()) {
        traceId = UUID.randomUUID().toString().replace("-", "");
      }

      String spanId = request.getHeader("X-B3-SpanId");
      if (spanId == null || spanId.isEmpty()) {
        spanId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
      }

      MDC.put(TRACE_ID, traceId);
      MDC.put(SPAN_ID, spanId);

      MDC.put("requestUri", request.getRequestURI());
      MDC.put("requestMethod", request.getMethod());

      response.addHeader("X-B3-TraceId", traceId);
      response.addHeader("X-B3-SpanId", spanId);

      filterChain.doFilter(request, response);
    } finally {
      MDC.remove(TRACE_ID);
      MDC.remove(SPAN_ID);
      MDC.remove("requestUri");
      MDC.remove("requestMethod");
    }
  }
}

