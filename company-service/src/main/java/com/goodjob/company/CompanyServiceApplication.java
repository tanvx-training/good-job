package com.goodjob.company;

import com.goodjob.common.api.feign.config.CustomFeignClients;
import com.goodjob.common.infrastructure.config.SharedConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@CustomFeignClients
@SpringBootApplication
@Import(SharedConfigurationReference.class)
public class CompanyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }
} 