package com.lmt.mbsp.user.adapter.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: lex
 * @Date: 2018/7/25 下午2:20
 * @Description:
 */
@FeignClient(value = "message-service")
public interface ValidateService {
    @RequestMapping(value = "/validate/createCode", method = RequestMethod.GET)
    String createCode(@RequestParam("key") String key);

    @RequestMapping(value = "/validate/verifyCode", method = RequestMethod.GET)
    Boolean verifyCode(@RequestParam("key") String key, @RequestParam("code") String code);
}
