package com.lmt.mbsp.user.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lex
 * @Date: 2018/7/25 下午2:12
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserAdapterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserAdapterApplication.class, args);
    }
}
