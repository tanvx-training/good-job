package com.goodjob.job.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService  {

    private final CacheManager cacheManager;

    /**
     * Xóa một cache entry cụ thể
     */
    public void evictCache(String cacheName, String cacheKey) {
        log.info(
                "Evict cache {} with key {}", cacheName, cacheKey
        );
        Objects.requireNonNull(cacheManager.getCache(cacheName)).evict(cacheKey);
    }

    /**
     * Xóa toàn bộ cache
     */
    public void clearCache(String cacheName) {
        log.info(
                "Clear cache {}", cacheName
        );
        Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
    }

    /**
     * Schedule để tự động xóa cache sau một khoảng thời gian
     * Chạy vào 2 giờ sáng mỗi ngày
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void evictAllCachesAtIntervals() {
        log.info("Executing scheduled cache eviction");
        clearCache("companies");
        clearCache("benefits");
        clearCache("skills");
        clearCache("industries");
    }
}
