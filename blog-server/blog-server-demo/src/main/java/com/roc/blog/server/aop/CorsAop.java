package com.roc.blog.server.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AOP参考 https://mawen.work/springboot/2020/08/18/springboot-aop/
 * CORS参考 https://segmentfault.com/a/1190000024518005
 * filter,interceptor,aop https://blog.51cto.com/phyger/5201782
 * filter -> interceptor -> controllerAdvice -> @Aspect -> controller
 * filter是servlet规范中的一部分，是servlet容器实现的，
 * interceptor是spring框架实现的，是基于java的反射机制的。
 * aop是spring框架实现的，是基于java的反射机制的。
 *
 * @author Zhang Peng
 * @since 2023/3/27 16:00
 */

@Slf4j
@Aspect
@Configuration
public class CorsAop {

    //all class end with 'Controller' and all methods in controller package
    @After("execution (* com.roc.blog.server.controller..*Controller.*(..))")
    public void corsAop(JoinPoint jp) {
        log.debug("corsAop is executed");

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String origin = request.getHeader("Origin");

        //set header for CORS
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
