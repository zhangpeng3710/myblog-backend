package com.roc.blog.server.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author: Zhang Peng
 * @Date: 2023/4/10 19:44
 */
@Configuration
public class MyRemoteIpFilter {
    @Bean
    FilterRegistrationBean<RemoteIpFilter> remoteIpFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean(new RemoteIpFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }
}
