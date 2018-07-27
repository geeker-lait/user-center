package com.lmt.mbsp.user.adapter.config;

import feign.Contract;
import feign.Feign;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static java.util.concurrent.TimeUnit.SECONDS;

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
        return new feign.Contract.Default();
    }

//    @Bean
//    public HystrixClientFallback fb(){
//        return new HystrixClientFallback();
//    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }

}
