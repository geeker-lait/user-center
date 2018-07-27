package com.lmt.mbsp.user.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket createRestApi() {

        ParameterBuilder tokenParams = new ParameterBuilder();
        tokenParams.name("X-Token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        ParameterBuilder TimeParams = new ParameterBuilder();
        TimeParams.name("X-Timestamp").description("时间戳").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        ParameterBuilder signParams = new ParameterBuilder();
        signParams.name("X-Sign").description("签名").modelRef(new ModelRef("string")).parameterType("header").required(true).build();

        List<Parameter> headerParams = new ArrayList<Parameter>();
        /*headerParams.add(tokenParams.build());
        headerParams.add(TimeParams.build());
        headerParams.add(signParams.build());*/

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("api")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lmt"))
                .build()
                .globalOperationParameters(headerParams);

        /*return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lmt"))
                .paths(PathSelectors.any())
                .build();*/
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("用户中心")
                .description("用户中心接口")
                .contact("lait.zhang")
                .version("1.0.0")
                .build();
    }


    /**
     * 使用enableMVC注解的话,该配置必须,否则无法映射资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


}
