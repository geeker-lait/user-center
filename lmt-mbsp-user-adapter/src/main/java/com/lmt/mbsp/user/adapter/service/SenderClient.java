package com.lmt.mbsp.user.adapter.service;

import com.lmt.mbsp.user.adapter.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: lex
 * @Date: 2018/7/25 下午2:13
 * @Description:
 */
@FeignClient(value = "message-service", configuration = FeignConfig.class)
public interface SenderClient {
    @RequestMapping(value = "/send/sms", method = RequestMethod.POST)
    ResponseEntity sendSms(@RequestParam("phone") String phone, @RequestParam("message") String message);

    @RequestMapping(value = "/send/mail", method = RequestMethod.POST)
    ResponseEntity sendMail(@RequestParam("address") String address, @RequestParam("title") String title, @RequestParam("message") String message);
}
