package com.roc.blog.server.cache.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author: Zhang Peng
 * @Date: 2023/3/31 13:32
 */
@Data
public class CacheUser implements Serializable {
    private int userId;
    private String name;
    private String password;

    public CacheUser(int id, String name, String password) {
        this.userId = id;
        this.name = name;
        this.password = password;
    }
}
