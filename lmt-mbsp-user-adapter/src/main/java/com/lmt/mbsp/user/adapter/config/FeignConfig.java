package com.lmt.mbsp.user.adapter.config;

import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Auther: lex
 * @Date: 2018/7/26 下午3:26
 * @Description:
 */
@Configuration
public class FeignConfig {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

    @Bean
    public Contract feignContract(){
        return new SpringMvcContract();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }

//    @Bean
//    public HystrixClientFallback fb(){
//        return new HystrixClientFallback();
//    }

//    @Bean
//    public Retryer feignRetryer() {
//        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
//    }

}
