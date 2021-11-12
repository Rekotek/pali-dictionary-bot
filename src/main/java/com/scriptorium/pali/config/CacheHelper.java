package com.scriptorium.pali.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheHelper {
    public static final String CACHE_NAME_PALI_WIDE = "pali-wide";
    public static final String CACHE_NAME_PALI_STRICT = "pali-strict";

}
