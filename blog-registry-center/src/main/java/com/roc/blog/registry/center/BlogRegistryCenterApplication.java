package com.roc.blog.registry.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BlogRegistryCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogRegistryCenterApplication.class, args);
    }

}
