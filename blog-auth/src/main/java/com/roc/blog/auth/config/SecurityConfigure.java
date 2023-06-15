//package com.roc.blog.auth.config;
//
//import org.apache.logging.log4j.core.config.Order;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.annotation.Resource;
//
///**
// * @Description
// * @Author: Zhang Peng
// * @Date: 2023/4/17 23:21
// */
//@Order(5)
//@EnableWebSecurity
//public class SecurityConfigure extends WebSecurityConfigurerAdapter {
//    @Resource
//    private FebsUserDetailService userDetailService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers().antMatchers("/oauth/**")
//                .and()
//                .authorizeRequests().antMatchers("/oauth/**").authenticated()
//                .and()
//                .csrf().disable();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
//    }
//}
