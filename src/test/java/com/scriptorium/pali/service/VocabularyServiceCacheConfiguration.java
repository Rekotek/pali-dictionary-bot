package com.scriptorium.pali.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static com.scriptorium.pali.config.CacheHelper.CACHE_NAME_PALI_STRICT;
import static com.scriptorium.pali.config.CacheHelper.CACHE_NAME_PALI_WIDE;

@TestConfiguration
@EnableCaching
@ComponentScan("com.scriptorium.pali.service")
public class VocabularyServiceCacheConfiguration {
    @Bean
    CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_NAME_PALI_WIDE, CACHE_NAME_PALI_STRICT);
    }
}
