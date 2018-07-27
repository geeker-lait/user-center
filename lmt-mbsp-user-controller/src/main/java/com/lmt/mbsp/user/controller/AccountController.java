package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.AccountBiz;
import com.lmt.mbsp.user.biz.LoginBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.AccountQuery;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.service.AccountService;
import com.lmt.mbsp.user.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * @Auther: Tangsm
 * @Date: 6/1/2018 17:16
 * @Description: 含前后台注册（创建）账号、修改密码、重置密码、变更用户名
 */
@RestController
@RequestMapping("/account")
public class AccountController implements CrudController<Account, Long, AccountQuery, AccountInfo> {
    private AccountService accountService;
    private AccountBiz accountBiz;

    @Autowired
    public AccountController(AccountService accountService, AccountBiz accountBiz) {
        this.accountService = accountService;
        this.accountBiz = accountBiz;
    }

    @Override
    public CrudService<Account, Long> getService() {
        return accountService;
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

    @GetMapping("/sendSms/{mobile}")
    @ApiOperation(value = "获取验证码", responseReference = "get")
    public ResponseMessage<String> sendSms(@ApiParam("手机号") @PathVariable String mobile) throws Exception {
        String sms = accountBiz.sendSms(mobile);
        return ok(sms);
    }

    /****************************************************************************************** 前台 ******************************************************************/
    @PostMapping("/register")
    @ApiOperation(value = "前台-注册", responseReference = "post")
    public ResponseMessage<Long> register(HttpRequest httpRequest, @RequestBody RegistInfo info) throws Exception {
        // TODO 注册的信息需放入登录session信息中

        return ok(accountBiz.register(info));
    }

    @PutMapping("/forgetPwd")
    @ApiOperation(value = "前台-忘记密码-手机验证", responseReference = "put")
    public ResponseMessage<ForgotPasswordInfo> forgetPwd(@RequestBody ForgotPasswordInfo info) throws Exception {
        // TODO
        accountBiz.forgetPwd(info);
        return ok(info);
    }

    @PutMapping("/resetPwd")
    @ApiOperation(value = "前台-重置密码", responseReference = "put")
    public ResponseMessage<ResetPasswordInfo> resetPwd(@RequestBody ResetPasswordInfo info) throws Exception {
        accountBiz.resetPwd(info);
        return ok(info);
    }

    /****************************************************************************************** 用户中心 ******************************************************************/
    @PutMapping("/cs/resetOperatorPwd")
    @ApiOperation(value = "用户中心-重置操作员密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> resetOperatorPwd(@RequestBody EditPasswordInfo info) throws Exception {
        // TODO 登录信息获取
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());

        accountBiz.resetOperatorPwd(info);

        return ok(info);
    }

    @PutMapping("/cs/editPwd")
    @ApiOperation(value = "用户中心-修改自己密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> editSelfPwd(@RequestBody EditPasswordInfo info) throws Exception {
        return ok(editPwd(info));
    }

    // 用以验证修改密码时填写的旧密码是否正确
    @GetMapping("/cs/searchPwd")
    @ApiOperation(value = "用户中心-查询密码（修改密码校验使用）", responseReference = "get")
    public ResponseMessage<String> searchCusPassword() throws Exception {
        return ok(selPwd());
    }

    /****************************************************************************************** 后台 ******************************************************************/
    @PutMapping("/cms/editPwd")
    @ApiOperation(value = "后台-修改自己密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> editCmsSelfPwd(@RequestBody EditPasswordInfo info) throws Exception {
        return ok(editPwd(info));
    }

    @PutMapping("/cms/editSysPwd")
    @ApiOperation(value = "后台-修改系统用户密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> editCmsSysPwd(@RequestBody EditPasswordInfo info) throws Exception {
        accountBiz.editPwd(info);
        return ok(info);
    }

    @PutMapping("/cms/editUserPwd")
    @ApiOperation(value = "后台-修改个人用户密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> editCmsUserPwd(@RequestBody EditPasswordInfo info) throws Exception {
        accountBiz.editPwd(info);
        return ok(info);
    }

    @GetMapping("/cms/searchPwd")
    @ApiOperation(value = "后台-查询密码（修改密码校验使用）", responseReference = "get")
    public ResponseMessage<String> searchCmsPassword() throws Exception {
        return ok(selPwd());
    }

    /****************************************************************************************** 私有方法 ******************************************************************/
    private String selPwd() throws Exception {
        // TODO 登录信息获取
        LoginContext context = LoginContext.getLoginContext();

        return accountBiz.selectPwd(context.getAccountId());
    }

    private EditPasswordInfo editPwd(EditPasswordInfo info) throws Exception {
        // TODO 登录信息获取
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());

        accountBiz.editPwd(info);

        return info;
    }
}
