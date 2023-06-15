//package com.roc.blog.auth.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.annotation.Resource;
//
///**
// * @Description
// * @Author: Zhang Peng
// * @Date: 2023/4/17 23:41
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter{
//    @Resource
//    private AuthenticationManager authenticationManager;
//    @Resource
//    private RedisConnectionFactory redisConnectionFactory;
//    @Resource
//    private FebsUserDetailService userDetailService;
//    @Resource
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("febs")
//                .secret(passwordEncoder.encode("123456"))
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("all");
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints.tokenStore(tokenStore())
//                .userDetailsService(userDetailService)
//                .authenticationManager(authenticationManager)
//                .tokenServices(defaultTokenServices());
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    @Primary
//    @Bean
//    public DefaultTokenServices defaultTokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(tokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 24);
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
//        return tokenServices;
//    }
//}
