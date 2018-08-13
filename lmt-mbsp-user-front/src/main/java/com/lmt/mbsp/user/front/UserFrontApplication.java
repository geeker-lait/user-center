package com.lmt.mbsp.user.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/8/2 下午2:58
 * @Description: 
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lmt.mbsp.user.adapter"})
@SpringBootApplication
@ComponentScan({"com.lmt.mbsp.user", "com.lmt.framework"})
public class UserFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserFrontApplication.class, args);
    }
}
