package com.yusiyu.springbootshiro.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

//自定义shiro中的缓存管理器
public class RedisCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);   //会打印com.yusiyu.springbootshiro.shiro.realms.CustomerRealm.authorizationCache
        return new RedisCache<K,V>(cacheName);
    }
}
