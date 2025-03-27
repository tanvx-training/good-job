package com.goodjob.common.aop;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Aspect for logging execution of service and repository Spring components.
 * Uses AOP to log method entry, exit, and exceptions.
 * Also collects metrics for method execution time.
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final MeterRegistry meterRegistry;

    /**
     * Pointcut that matches all repositories, services, and controllers.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.goodjob..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        // Increment error counter metric
        meterRegistry.counter("method.errors", 
                "class", className, 
                "method", methodName, 
                "exception", e.getClass().getSimpleName())
                .increment();
        
        log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'",
                className,
                methodName,
                e.getCause() != null ? e.getCause() : "NULL",
                e.getMessage(),
                e);
    }

    /**
     * Advice that logs when a method is entered and exited.
     * Also records execution time as a metric.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        // Set correlation ID if not already present
        if (MDC.get("correlationId") == null) {
            MDC.put("correlationId", UUID.randomUUID().toString());
        }
        
        // Set service ID
        MDC.put("serviceId", className);
        
        // Create metric timer for method execution
        Timer.Sample sample = Timer.start(meterRegistry);
        
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}",
                    className,
                    methodName,
                    Arrays.toString(joinPoint.getArgs()));
        }
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = sample.stop(Timer.builder("method.execution.time")
                    .tag("class", className)
                    .tag("method", methodName)
                    .tag("success", "true")
                    .register(meterRegistry));
            
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {} (took {} ms)",
                        className,
                        methodName,
                        result,
                        TimeUnit.NANOSECONDS.toMillis(executionTime));
            }
            
            // Increment success counter
            meterRegistry.counter("method.calls", 
                    "class", className, 
                    "method", methodName, 
                    "success", "true")
                    .increment();
            
            return result;
        } catch (Exception e) {
            // Record execution time even for failed calls
            sample.stop(Timer.builder("method.execution.time")
                    .tag("class", className)
                    .tag("method", methodName)
                    .tag("success", "false")
                    .register(meterRegistry));
            
            // Increment failure counter
            meterRegistry.counter("method.calls", 
                    "class", className, 
                    "method", methodName, 
                    "success", "false")
                    .increment();
            
            if (e instanceof IllegalArgumentException) {
                log.error("Illegal argument: {} in {}.{}()",
                        Arrays.toString(joinPoint.getArgs()),
                        className,
                        methodName);
            }
            throw e;
        } finally {
            // Clean up MDC
            MDC.remove("correlationId");
            MDC.remove("serviceId");
        }
    }
} 