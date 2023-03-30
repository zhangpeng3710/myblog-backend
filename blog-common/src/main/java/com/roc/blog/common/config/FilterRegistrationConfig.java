package com.roc.blog.common.config;

import com.roc.blog.common.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description filter registration config
 * @Author: Zhang Peng
 * @Date: 2023/3/30 13:10
 */
@Configuration
public class FilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> registerCorsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        //-2147483648 is the highest priority, 2147483647 is the lowest priority
        //2147483647 is the default value if not set order value
        bean.setOrder(1);
        bean.setFilter(new CorsFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }

}
