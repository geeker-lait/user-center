package com.lmt.mbsp.user.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Auther: lex
 * @Date: 2018/8/1 下午5:56
 * @Description:
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 30)
public class SessionConfig {
}
