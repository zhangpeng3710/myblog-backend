package com.roc.blog.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//scan all modules in project blog-backend to load all beans
@SpringBootApplication(scanBasePackages = {"com.roc.blog"})
@MapperScan("com.roc.blog.server.dao.mapper")
@ServletComponentScan("com.roc.blog.server.filter")
@EnableDiscoveryClient
public class BlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServerApplication.class, args);
    }

}
