package com.lmt.mbsp.user.biz.message;

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
@Service
public interface ValidateService extends com.lmt.mbsp.user.adapter.service.ValidateService {
}
