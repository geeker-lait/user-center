package com.lmt.mbsp.user.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/30/2018 18:41
 * @Description:
 */
@Configuration
public class RabbitmqConfig {




    /**
     * 队列名在这里定义和配置
     * @return
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

}
