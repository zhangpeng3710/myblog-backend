package com.roc.blog.server.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@WebFilter
public class CorsFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With, X-Custom-Header");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE");

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.getOutputStream().write("Options Success".getBytes(StandardCharsets.UTF_8));
        } else {
            chain.doFilter(req, res);
        }
    }

}
