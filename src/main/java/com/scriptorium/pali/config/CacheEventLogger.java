package com.scriptorium.pali.config;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

// https://www.baeldung.com/spring-boot-ehcache
@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {
    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        log.debug("Cache event {} for key = \"{}\"",
                cacheEvent.getType().name(), cacheEvent.getKey());
    }
}

