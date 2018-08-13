package com.lmt.mbsp.user.front.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.mbsp.user.front.biz.AccountBiz;
import com.lmt.mbsp.user.front.biz.LoginBiz;
import com.lmt.mbsp.user.front.biz.vo.LoginContext;
import com.lmt.mbsp.user.vo.account.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * Auther: Tangsm
 * Date: 6/1/2018 17:16
 * Description: 含前后台注册（创建）账号、修改密码、重置密码、变更用户名
 */
@RestController
@RequestMapping("/account")
public class AccountController{
    private AccountBiz accountBiz;
    private LoginBiz loginBiz;

    @Autowired
    public AccountController(AccountBiz accountBiz, LoginBiz loginBiz) {
        this.accountBiz = accountBiz;
        this.loginBiz = loginBiz;
    }

    /****************************************************************************************** 通用 ******************************************************************/
    @GetMapping("/checkUserName/{username}")
    @ApiOperation(value = "校验用户名是否已被注册", responseReference = "get")
    public ResponseMessage<Boolean> checkUserName(@PathVariable String username) throws Exception {
        return ok(accountBiz.checkUserName(username));
    }

    @GetMapping("/checkMobile/{mobile}")
    @ApiOperation(value = "校验手机号是否已被注册", responseReference = "get")
    public ResponseMessage<Boolean> checkMobile(@PathVariable String mobile) throws Exception {
        return ok(accountBiz.checkMobile(mobile));
    }

    @PutMapping("/findPwd/{mobile}")
    @ApiOperation(value = "前台-找回密码发送手机验证码", responseReference = "put")
    public ResponseMessage<Boolean> findPwd(@PathVariable String mobile) throws Exception {
        return ok(accountBiz.findPwd(mobile));
    }

    @PutMapping("/resetPwd")
    @ApiOperation(value = "前台-重置密码", responseReference = "put")
    public ResponseMessage<ResetPasswordInfo> resetPwd(@RequestBody ResetPasswordInfo info) throws Exception {
        accountBiz.resetPwd(info);
        return ok(info);
    }

    // 用以验证修改密码时填写的旧密码是否正确
    @GetMapping("/searchPwd")
    @ApiOperation(value = "用户中心-查询密码（修改密码校验使用）", responseReference = "get")
    public ResponseMessage<String> searchCusPassword() throws Exception {
        return ok(selPwd());
    }

    @GetMapping("/toAuthorize/{userId}/{id}")
    @ApiOperation(value = "用户中心-进入操作员账号授权页面", responseReference = "get")
    public  ResponseMessage<ToAccountAuthorizeInfo> toAuthorize(@PathVariable Long userId, @PathVariable Long id)  throws Exception{
        return ok(accountBiz.toAuthorize(userId, id));
    }

    @PutMapping("/authorize")
    @ApiOperation(value = "用户中心-操作员账号授权", responseReference = "put")
    public  ResponseMessage<SaveAccountAuthorizeInfo> authorize(@RequestBody SaveAccountAuthorizeInfo info)  throws Exception{
        accountBiz.authorize(info);

        return ok();
    }

    @PutMapping("/diabled/{id}")
    @ApiOperation(value = "用户中心-禁用操作员账号", responseReference = "put")
    public  ResponseMessage<Long> diabledAccount(@PathVariable Long id)  throws Exception{
        accountBiz.disabled(id);
        return ok(id);
    }

    @PutMapping("/unDiabled/{id}")
    @ApiOperation(value = "用户中心-激活操作员账号", responseReference = "put")
    public  ResponseMessage<Long> unDiabledAccount(@PathVariable Long id)  throws Exception{
        accountBiz.unDisabled(id);
        return ok(id);
    }

    @GetMapping("/toChange/{id}")
    @ApiOperation(value = "用户中心-进入变更账号页面", responseReference = "get")
    public  ResponseMessage<ToEditAccountInfo> toChangeAccount(@PathVariable Long id)  throws Exception{
        return ok(accountBiz.toEditAccount(id));
    }

    @PutMapping("/change")
    @ApiOperation(value = "用户中心-变更账号", responseReference = "put")
    public  ResponseMessage<EditAccountInfo> changeAcc(@RequestBody EditAccountInfo info)  throws Exception{
        accountBiz.editAccount(info);
        return ok(info);
    }

    @PutMapping("/bind")
    @ApiOperation(value = "用户中心-绑定账号", responseReference = "put")
    public  ResponseMessage<BindAccountInfo> bindAcc(@RequestBody BindAccountInfo info)  throws Exception{
        accountBiz.bindAcc(info);
        return ok(info);
    }
    /****************************************************************************************** 私有方法 ******************************************************************/
    private String selPwd() throws Exception {
        LoginContext context = loginBiz.getLoginContext();

        return accountBiz.selectPwd(context.getAccountId());
    }
}
