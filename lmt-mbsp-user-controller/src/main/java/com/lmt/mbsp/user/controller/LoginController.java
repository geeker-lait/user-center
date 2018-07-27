package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.mbsp.user.biz.LoginBiz;
import com.lmt.mbsp.user.vo.LoginInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 11:43
 * @Description:
 */

@RestController
public class LoginController {


    @Autowired
    private LoginBiz loginBiz;



    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ResponseMessage<Object> checkUserName(@RequestBody LoginInfo loginInfo) throws Exception {
        return ok(loginBiz.login(loginInfo));
    }

}
