package com.goodjob.common.api.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation to enable all Feign clients defined in the common-lib package.
 * Add this annotation to your main application class to use the common Feign clients.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableFeignClients(basePackages = "com.goodjob.common.feign.client")
@Import(FeignClientConfig.class)
public @interface CustomFeignClients {
} 