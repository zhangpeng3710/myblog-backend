package com.roc.blog.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;



@OpenAPIDefinition(
        info = @Info(
                title = "My Blog",
                description = "Blog backend ...",
                contact = @Contact(
                        name = "Peng",
                        email = "kldzp@126.com"
                ),
                license = @License(
                        name = "MIT Licence",
                        url = "https://github.com/zhangpeng3710/myblog-backend.git")),
        servers = @Server(url = "http://localhost:8080")
)
public class SpringDocConfig {

}
