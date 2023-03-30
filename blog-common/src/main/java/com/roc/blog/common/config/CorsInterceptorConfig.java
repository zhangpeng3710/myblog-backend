package com.roc.blog.common.config;

import com.roc.blog.common.interceptor.CorsInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * add cors interceptor to spring mvc
 *
 * @author Zhang Peng
 * @since 2023/3/27 16:00
 */

@Slf4j
@Configuration
public class CorsInterceptorConfig implements WebMvcConfigurer {

    private static final String[] EXCLUDE_PATH = {
            "/error",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/swagger-ui.html/**"
    };

    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("CorsConfig addInterceptors");
        registry.addInterceptor(new CorsInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH);
    }


}
