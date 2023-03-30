package com.roc.blog.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor for CORS
 * <p>
 * filter -> interceptor -> controllerAdvice -> @Aspect -> controller
 *
 * @author Zhang Peng
 * @since 2023/3/27 16:00
 */

@Slf4j
public class CorsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        log.debug("CorsInterceptor preHandle is executed");
        log.debug(request.getMethod());

        response.setHeader("Access-Control-Allow-Origin",
                request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age",
                "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, X-Requested-With, X-Custom-Header, Authorization");
        response.setHeader("Access-Control-Allow-Credentials",
                "true");

        return true;
    }

}
