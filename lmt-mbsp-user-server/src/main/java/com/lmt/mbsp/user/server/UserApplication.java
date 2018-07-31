package com.lmt.mbsp.user.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/10/2018 18:14
 * @Description:
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lmt.mbsp.user.adapter"})
@SpringBootApplication
@ComponentScan({"com.lmt.mbsp.user", "com.lmt.framework"})
public class UserApplication /*extends SpringBootServletInitializer*/ {
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    /*@Bean
    public UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory() {
        return new UndertowEmbeddedServletContainerFactory() {

            @Override
            protected UndertowEmbeddedServletContainer getUndertowEmbeddedServletContainer(
                    Undertow.Builder builder, DeploymentManager manager, int port) {
                return new UndertowEmbeddedServletContainer(builder, manager,
                        getContextPath(), isUseForwardHeaders(), port >= 0,
                        getCompression(), getServerHeader()) {

                    @Override
                    public void stop() throws EmbeddedServletContainerException {
                        super.stop();
                        manager.undeploy();
                    }

                };
            }

        };
    }
*/


/*    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory =
                new TomcatEmbeddedServletContainerFactory();
        return factory;
    }*/
    /*

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UserApplication.class);
    }
*/

    // 加载YML格式自定义配置文件
    /*@Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        // File引入
        //yaml.setResources(new FileSystemResource("config.yml"));
        //class引入
        yaml.setResources(new ClassPathResource("application-dev-dao.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }*/


    /*@Configuration
    @ConditionalOnClass({Servlet.class, Undertow.class, SslClientAuthMode.class})//Undertow配置判断
    @ConditionalOnMissingBean(
            value = {EmbeddedServletContainerFactory.class},
            search = SearchStrategy.CURRENT
    )
    public static class EmbeddedUndertow {
        public EmbeddedUndertow() {
        }

        @Bean
        public UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory() {
            return new UndertowEmbeddedServletContainerFactory();
        }
    }*/


    /*@Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

            @Override
            public void customize(Builder builder) {
                builder.addHttpListener(8080, "0.0.0.0");
            }

        });
        return factory;
    }*/


}
