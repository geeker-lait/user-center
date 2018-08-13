package com.lmt.mbsp.user.backend.config;

//import io.undertow.servlet.api.SecurityConstraint;
//import io.undertow.servlet.api.SecurityInfo;
//import io.undertow.servlet.api.TransportGuaranteeType;
//import io.undertow.servlet.api.WebResourceCollection;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
//import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class UndertowConfig {

    //private final GracefulShutdownWrapper gracefulShutdownWrapper;

    /*@Bean
    public UndertowServletWebServerFactory servletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();


        factory.addDeploymentInfoCustomizers(deploymentInfo -> deploymentInfo.addOuterHandlerChainWrapper(gracefulShutdownWrapper));
        factory.addBuilderCustomizers(
                builder -> {
                    builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                            .setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH, true)
                            .setServerOption(UndertowOptions.ENABLE_STATISTICS, true)
                            .addHttpListener(8080, "0.0.0.0");
                });


        return factory;
    }*/



    /*@Value("${server.port}")
    private int httpPort;
    @Value("${server.port}")
    private int httpsPort;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        UndertowEmbeddedServletContainerFactory undertowFactory = new UndertowEmbeddedServletContainerFactory();
        //监听http端口
        undertowFactory.addBuilderCustomizers((UndertowBuilderCustomizer) builder -> builder.addHttpListener(httpPort, "0.0.0.0"));
        //将http的8070端口重定向到https的端口上
        undertowFactory.addDeploymentInfoCustomizers(deploymentInfo -> {
            deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection()
                            .addUrlPattern("/*")) .setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> httpsPort);
        });
        return undertowFactory;
    }*/

}