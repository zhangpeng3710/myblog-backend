package com.roc.blog.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for CORS
 * if you want to use this filter in other module, you need to extend from this class
 * <p>
 * filter -> interceptor -> controllerAdvice -> @Aspect -> controller
 *
 * @author Zhang Peng
 * @since 2023/3/27 16:00
 */
@Slf4j
public class CorsFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.debug("CorsFilter is executed");

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        log.debug(request.getMethod());

        response.setHeader("Access-Control-Allow-Origin",
                request.getHeader("Origin"));
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

        chain.doFilter(req, res);
    }

}
