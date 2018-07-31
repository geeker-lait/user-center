package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.AccountBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.AccountQuery;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.service.AccountService;
import com.lmt.mbsp.user.vo.account.AccountInfo;
import com.lmt.mbsp.user.vo.account.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * Auther: Tangsm
 * Description: 系统用户账号
 */
@RestController
@RequestMapping("/admin-account")
public class AdminAccountController implements CrudController<Account, Long, AccountQuery, AccountInfo> {
    private AccountService accountService;
    private AccountBiz accountBiz;

    @Autowired
    public AdminAccountController(AccountService accountService, AccountBiz accountBiz) {
        this.accountService = accountService;
        this.accountBiz = accountBiz;
    }

    @Override
    public CrudService<Account, Long> getService() {
        return accountService;
    }

    /****************************************************************************************** 后台-系统用户账号 ******************************************************************/

    @PutMapping("/editPwd")
    @ApiOperation(value = "修改自己密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> editSelfPwd(@RequestBody EditPasswordInfo info) throws Exception {
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());

        accountBiz.editPwd(info);

        return ok(info);
    }

    @PutMapping("/resetPwd")
    @ApiOperation(value = "后台-重置系统用户账号密码", responseReference = "put")
    public ResponseMessage<ResetPasswordInfo> resetPwd(@RequestBody ResetPasswordInfo info) throws Exception {
        accountBiz.resetPwd(info);
        return ok(info);
    }

    @GetMapping("/toAuthorize/{userId}/{accountId}")
    @ApiOperation(value = "后台-进入系统用户账号授权页面", responseReference = "get")
    public  ResponseMessage<ToAccountAuthorizeInfo> toAuthorize(@PathVariable Long userId, @PathVariable Long accountId)  throws Exception{
        return ok(accountBiz.toAuthorize(userId, accountId));
    }

    @PutMapping("/authorize")
    @ApiOperation(value = "后台-系统用户账号授权", responseReference = "put")
    public  ResponseMessage<SaveAccountAuthorizeInfo> authorize(@RequestBody SaveAccountAuthorizeInfo info)  throws Exception{
        accountBiz.authorize(info);

        return ok();
    }

    @PutMapping("/toSuperAcc/{accountId}")
    @ApiOperation(value = "后台-系统用户账号设为超管", responseReference = "put")
    public  ResponseMessage<SaveAccountAuthorizeInfo> toSuperAccount(@PathVariable Long accountId)  throws Exception{
        accountBiz.toSuperAccount(accountId);

        return ok();
    }

    @PutMapping("/diabledAcc/{accountId}")
    @ApiOperation(value = "后台-禁用系统用户账号", responseReference = "put")
    public  ResponseMessage<Long> diabledAccount(@PathVariable Long accountId)  throws Exception{
        accountBiz.disabled(accountId);
        return ok(accountId);
    }

    @PutMapping("/unDiabledAcc/{accountId}")
    @ApiOperation(value = "后台-激活系统用户账号", responseReference = "put")
    public  ResponseMessage<Long> unDiabledAccount(@PathVariable Long accountId)  throws Exception{
        accountBiz.unDisabled(accountId);
        return ok(accountId);
    }

    @GetMapping("/toChangeAcc/{accountId}")
    @ApiOperation(value = "后台-进入变更系统用户账号页面", responseReference = "get")
    public  ResponseMessage<ToEditAccountInfo> toChangeAccount(@PathVariable Long accountId)  throws Exception{
        return ok(accountBiz.toEditAccount(accountId));
    }

    @PutMapping("/changeAcc")
    @ApiOperation(value = "后台-变更系统用户账号", responseReference = "put")
    public  ResponseMessage<EditAccountInfo> changeAcc(@RequestBody EditAccountInfo info)  throws Exception{
        accountBiz.editAccount(info);
        return ok(info);
    }

    @PutMapping("/bindAcc")
    @ApiOperation(value = "后台-绑定系统用户账号", responseReference = "put")
    public  ResponseMessage<BindAccountInfo> bindAcc(@RequestBody BindAccountInfo info)  throws Exception{
        accountBiz.bindAcc(info);
        return ok(info);
    }
}
