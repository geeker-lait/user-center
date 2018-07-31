package com.lmt.mbsp.user.adapter.service;

import com.lmt.mbsp.user.adapter.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: lex
 * @Date: 2018/7/25 下午2:20
 * @Description:
 */
@FeignClient(value = "message-service", configuration = FeignConfig.class)
public interface CaptchaClient {
    @RequestMapping(value = "/captcha/getImage", method = RequestMethod.GET)
    void getImage();

    @RequestMapping(value = "/captcha/verifyImage", method = RequestMethod.GET)
    Boolean verifyImage(@RequestParam String verifyImage);
}
