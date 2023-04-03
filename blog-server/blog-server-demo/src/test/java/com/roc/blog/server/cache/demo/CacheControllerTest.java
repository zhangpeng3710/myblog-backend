package com.roc.blog.server.cache.demo;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @Description
 * @Author: Zhang Peng
 * @Date: 2023/3/31 14:26
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CacheControllerTest {

    @Autowired
    CacheController controller;

    @Test
    @Order(1)
    void getPrud() {
        controller.getPrud("1");
        System.out.println(1);
    }

    @Order(2)
    @Test
    void deleteUser() {
        controller.deleteUser("1");
        System.out.println(2);
    }

    @Order(3)
    @Test
    void saveUser() {
        controller.saveUser(new CacheUser(1, "name", "password"));
        System.out.println(3);
    }

    @Order(4)
    @Test
    void getUser2() {
        controller.getUser2("1");
        System.out.println(4);
    }

    @Order(5)
    @Test
    void getUser3() {
        controller.getUser3("1");
        System.out.println(5);
    }
}