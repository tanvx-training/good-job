package com.goodjob.common.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

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

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toLongString();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        Date startTime = new Date();
        logger.info("{} - Method {} execution started at: {}", className, methodName, dateFormat.format(startTime));

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Thực thi phương thức
        long end = System.currentTimeMillis();
        long duration = end - start;

        Date endTime = new Date();
        logger.info("{} - Method {} execution lasted: {} ms", className, methodName, duration);
        logger.info("{} - Method {} execution ended at: {}", className, methodName, dateFormat.format(endTime));

        if (duration > 10) {
            logger.warn("{} - Method execution longer than 10 ms!", className);
        }

        return result;
    }
}
