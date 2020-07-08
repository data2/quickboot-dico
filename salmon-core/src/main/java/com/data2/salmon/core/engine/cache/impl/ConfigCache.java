package com.data2.salmon.core.engine.cache.impl;

import com.data2.salmon.core.engine.cache.FileConfigCache;
import com.data2.salmon.core.engine.config.ConfigurationLoader;
import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @author leewow
 */
@Component
@Slf4j
public class ConfigCache implements FileConfigCache {

    private Cache<String, String> cache = CacheBuilder.newBuilder().build();

    @Override
    public String getSource(final String key) {
        try {
            return cache.get(key, () -> {
                try {
                    return Resources.toString(ConfigurationLoader.getClassLoader().getResource(key), Charsets.UTF_8);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("get configcache err:{}", e.getMessage());
                    return null;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error("get configcache ExecutionException:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public void remove(String obj) {
        if (cache != null) {
            cache.invalidate(obj);
        }

    }

    @Override
    public void removeAll() {
        if (cache != null) {
            cache.invalidateAll();
        }
    }

}
