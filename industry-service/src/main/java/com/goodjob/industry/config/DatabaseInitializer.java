package com.goodjob.industry.config;

import com.goodjob.industry.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Database initializer for the Industry Service.
 * The actual initialization is handled by schema.sql and data.sql.
 * This class just logs the current state of the database.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final IndustryRepository industryRepository;

    @Override
    public void run(String... args) {
        log.info("Database initialization is handled by schema.sql and data.sql");
        log.info("Current industry count in database: {}", industryRepository.count());
    }
} 