package com.roc.blog.server.cache.demo;

import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author: Zhang Peng
 * @Date: 2023/3/31 13:27
 */
@Service
public class CacheService {
    public CacheUser getUser(int userId) {
        System.out.println("执行此方法，说明没有缓存，如果没有走到这里，就说明缓存成功了");
        CacheUser user = new CacheUser(userId, "no cache_"+userId, "password_"+userId);
        return user;
    }

    public CacheUser getUser2(int userId) {
        System.out.println("执行此方法，说明没有缓存，如果没有走到这里，就说明缓存成功了");
        CacheUser user = new CacheUser(userId, "name_nocache"+userId, "nocache");
        return user;
    }

}
