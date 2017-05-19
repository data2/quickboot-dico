package com.barnett.db.engine.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.barnett.db.engine.parser.TextFunction;
import com.barnett.db.util.DicoClassLoader;
import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.Resources;

public class DicoSqlSource implements SqlSource {

    private static Cache<String, Object> cache;

    public Object getSource(final String file, final String key) {
        try {
            return cache.get(key, new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    String sql = null;
                    try {
                        String contents = (String) new DicoFileSource().getSource(file);
                        if(contents == null){
                            contents = Resources.toString(DicoClassLoader
                                    .getClassLoader().getResource(key),
                                    Charsets.UTF_8);
                        }
                        sql = TextFunction.excute(contents, key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return sql;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
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
            ;
        }
    }

    static {
        cache = CacheBuilder.newBuilder().build();
    }

}
