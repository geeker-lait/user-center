package com.lmt.mbsp.user.adapter.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: lex
 * @Date: 2018/7/25 下午2:13
 * @Description:
 */
@Service
@FeignClient(value = "message-service")
public interface SenderService {
    @RequestMapping(value = "/send/sns", method = RequestMethod.POST)
    String sendSns(@RequestParam("phone") String phone, @RequestParam("message") String message);

    @RequestMapping(value = "/send/mail", method = RequestMethod.POST)
    String sendMail(@RequestParam("address") String address, @RequestParam("title") String title, @RequestParam("message") String message);
}
