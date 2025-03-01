package com.goodjob.benefit.config;

import com.goodjob.benefit.repository.BenefitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Database initializer for the Benefit Service.
 * Note: Actual initialization is now handled by schema.sql and data.sql
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final BenefitRepository benefitRepository;

    /**
     * Run after application startup.
     *
     * @param args command line arguments
     */
    @Override
    public void run(String... args) {
        log.info("Database initialization is handled by schema.sql and data.sql");
        log.info("Current benefit count in database: {}", benefitRepository.count());
    }
} 