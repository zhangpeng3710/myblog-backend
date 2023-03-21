package com.roc.blog.server.aop;

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
 *
 * CORS参考 https://segmentfault.com/a/1190000024518005
 */


//@Aspect
//@Configuration
//public class CorsAdvice {
//    //execution表达式 此表达式表示扫描controller下所有类的所有方法都执行此aop
//    @After("execution (* com.roc.blog.server.controller..*Controller.*(..))")
//    public void corsAop(JoinPoint jp) throws Throwable {
//        //获取response
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String origin = request.getHeader("Origin");
//        //核心设置
//        response.setHeader("Access-Control-Allow-Origin", origin);
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//    }
//}
