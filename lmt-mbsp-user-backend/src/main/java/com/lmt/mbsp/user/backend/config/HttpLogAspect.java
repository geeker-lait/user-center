package com.lmt.mbsp.user.backend.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

*/
/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/12/2018 11:05
 * @Description:
 *//*

@Aspect
@Order(5)
@Component
public class HttpLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.lmt.mbsp.user.controller..*.*(..))")
    public void httpLog() {
    }

    @Before("httpLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        logger.info("URL : " + request.getRequestURL().toString());

        logger.info("HTTP_METHOD : " + request.getMethod());

        logger.info("IP : " + request.getRemoteAddr());

        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "httpLog()")
    public void doAfterReturning(Object ret) throws Throwable {

        logger.info("RESPONSE : " + ret);

        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }


}
*/

