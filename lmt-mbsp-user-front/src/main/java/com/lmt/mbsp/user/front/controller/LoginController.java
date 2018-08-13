package com.lmt.mbsp.user.front.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.mbsp.user.front.biz.LoginBiz;
import com.lmt.mbsp.user.front.biz.RegistBiz;
import com.lmt.mbsp.user.vo.login.LoginInfo;
import com.lmt.mbsp.user.vo.regist.*;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/8/3 下午4:13
 * @Description: 
 */
@RestController
public class LoginController {

    private final LoginBiz loginBiz;
    private final RegistBiz registBiz;

    @Autowired
    public LoginController(LoginBiz loginBiz, RegistBiz registBiz) {
        this.loginBiz = loginBiz;
        this.registBiz = registBiz;
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ResponseMessage<Object> checkUserName(@RequestBody LoginInfo loginInfo) throws Exception {
        return ok(loginBiz.login(loginInfo));
    }

    @PostMapping("/regist/mobile")
    @ApiOperation(value = "前台-手机注册", responseReference = "post")
    public ResponseMessage<Long> registerByMobile(@ApiParam("注册信息") @RequestBody RegistMobileInfo info) throws Exception {
        info.setRegistWay(RegistWay.MOBILE);
        return ok(registBiz.regist(info));
    }

    @PostMapping("/regist/username")
    @ApiOperation(value = "前台-用户名注册", responseReference = "post")
    public ResponseMessage<Long> registerByUsername(@ApiParam("注册信息") @RequestBody RegistUserNameInfo info) throws Exception {
        info.setRegistWay(RegistWay.USERNAME);
        return ok(registBiz.regist(info));
    }

    @PostMapping("/regist/email")
    @ApiOperation(value = "前台-电子邮箱注册", responseReference = "post")
    public ResponseMessage<Long> registerByEmail(@ApiParam("注册信息") @RequestBody RegistEmailInfo info) throws Exception {
        info.setRegistWay(RegistWay.EMAIL);
        return ok(registBiz.regist(info));
    }

    @GetMapping("/regist/sendSms/{mobile}")
    @ApiOperation(value = "获取验证码", responseReference = "get", notes = "")
    public ResponseMessage<String> sendSms(@ApiParam("手机号") @PathVariable String mobile) throws Exception {
        if(registBiz.sendSms(mobile)) {
            return ResponseMessage.ok();
        }
        return ResponseMessage.error("获取验证码失败");
    }
}
