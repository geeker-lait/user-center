package com.lmt.mbsp.user.front.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决前端站点(主要为JavaScript发起的Ajax请求)访问的跨域问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  
    @Override  
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping("/**")  
                .allowedOrigins("*")    //允许所有前端站点调用
                .allowCredentials(true)  
                .allowedMethods("GET", "POST", "DELETE", "PUT" , "PATCH")
                .maxAge(1728000);  
    }  
}