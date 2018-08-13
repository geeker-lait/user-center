package com.lmt.mbsp.user.backend.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.mbsp.user.backend.biz.AccountBiz;
import com.lmt.mbsp.user.backend.biz.vo.LoginContext;
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
    private final AccountBiz accountBiz;

    @Autowired
    public AccountController(AccountBiz accountBiz) {
        this.accountBiz = accountBiz;
    }

    /****************************************************************************************** 后台-用户账号 ******************************************************************/

    // 用以验证修改密码时填写的旧密码是否正确
    @GetMapping("/searchPwd")
    @ApiOperation(value = "后台-查询密码（修改密码校验使用）", responseReference = "get")
    public ResponseMessage<String> searchCusPassword() throws Exception {
        return ok(selPwd());
    }

    @PutMapping("/editPwd")
    @ApiOperation(value = "后台-修改自己密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> editSelfPwd(@RequestBody EditPasswordInfo info) throws Exception {
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());

        accountBiz.editPwd(info);

        return ok(info);
    }

    @PutMapping("/resetPwd")
    @ApiOperation(value = "后台-重置用户账号密码", responseReference = "put")
    public ResponseMessage<ResetPasswordInfo> resetPwd(@RequestBody ResetPasswordInfo info) throws Exception {
        accountBiz.resetPwd(info);
        return ok(info);
    }

    @GetMapping("/toAuthorize/{userId}/{id}")
    @ApiOperation(value = "后台-进入用户账号授权页面", responseReference = "get")
    public  ResponseMessage<ToAccountAuthorizeInfo> toAuthorize(@PathVariable Long userId, @PathVariable Long id)  throws Exception{
        return ok(accountBiz.toAuthorize(userId, id));
    }

    @PutMapping("/authorize")
    @ApiOperation(value = "后台-用户账号授权", responseReference = "put")
    public  ResponseMessage<SaveAccountAuthorizeInfo> authorize(@RequestBody SaveAccountAuthorizeInfo info)  throws Exception{
        accountBiz.authorize(info);

        return ok();
    }

    @PutMapping("/toSuperAcc/{id}")
    @ApiOperation(value = "后台-用户账号设为超管", responseReference = "put")
    public  ResponseMessage toSuperAccount(@PathVariable Long id)  throws Exception{
        accountBiz.toSuperAccount(id);

        return ok(id);
    }

    @PutMapping("/cancelSuperAcc/{id}")
    @ApiOperation(value = "后台-用户账号取消超管", responseReference = "put")
    public  ResponseMessage cancelSuperAcc(@PathVariable Long id)  throws Exception{
        accountBiz.cancelSuperAccount(id);

        return ok();
    }

    @PutMapping("/addManager/{id}")
    @ApiOperation(value = "后台-授权企业管理员", responseReference = "put")
    public  ResponseMessage<Long> addManager(@PathVariable Long id) throws Exception{
        accountBiz.addManager(id);

        return ok(id);
    }

    @PutMapping("/cancelManager/{id}")
    @ApiOperation(value = "后台-取消企业管理员授权", responseReference = "put")
    public  ResponseMessage<Long> cancelManager(@PathVariable Long id) throws Exception{
        accountBiz.cancelManager(id);

        return ok(id);
    }

    @PutMapping("/diabled/{id}")
    @ApiOperation(value = "后台-禁用用户账号", responseReference = "put")
    public  ResponseMessage<Long> diabledAccount(@PathVariable Long id)  throws Exception{
        accountBiz.disabled(id);
        return ok(id);
    }

    @PutMapping("/unDiabled/{id}")
    @ApiOperation(value = "后台-激活用户账号", responseReference = "put")
    public  ResponseMessage<Long> unDiabledAccount(@PathVariable Long id)  throws Exception{
        accountBiz.unDisabled(id);
        return ok(id);
    }

    @GetMapping("/toChange/{id}")
    @ApiOperation(value = "后台-进入变更用户账号页面", responseReference = "get")
    public  ResponseMessage<ToEditAccountInfo> toChangeAccount(@PathVariable Long id)  throws Exception{
        return ok(accountBiz.toEditAccount(id));
    }

    @PutMapping("/change")
    @ApiOperation(value = "后台-变更用户账号", responseReference = "put")
    public  ResponseMessage<EditAccountInfo> changeAcc(@RequestBody EditAccountInfo info)  throws Exception{
        accountBiz.editAccount(info);
        return ok(info);
    }

    @PutMapping("/bind")
    @ApiOperation(value = "后台-绑定用户账号", responseReference = "put")
    public  ResponseMessage<BindAccountInfo> bindAcc(@RequestBody BindAccountInfo info)  throws Exception{
        accountBiz.bindAcc(info);
        return ok(info);
    }
    /****************************************************************************************** 私有方法 ******************************************************************/
    private String selPwd() throws Exception {
        LoginContext context = LoginContext.getLoginContext();

        return accountBiz.selectPwd(context.getAccountId());
    }
}
