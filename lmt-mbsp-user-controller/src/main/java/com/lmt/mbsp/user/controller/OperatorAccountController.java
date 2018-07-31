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

import java.util.List;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * 描述:企业用户账号模块-操作员账号
 * 作者:Tangsm
 */
@RestController
@RequestMapping("/operator-account")
public class OperatorAccountController implements CrudController<Account, Long, AccountQuery, AccountInfo> {
    private AccountService accountService;
    private AccountBiz accountBiz;

    @Autowired
    public void CrudController(AccountService accountService, AccountBiz accountBiz){
        this.accountService = accountService;
        this.accountBiz = accountBiz;
    }

    @Override
    public CrudService<Account, Long> getService() {
        return accountService;
    }

    /****************************************************************************************** 用户中心-企业用户账号 ******************************************************************/
    @PutMapping("/resetPwd")
    @ApiOperation(value = "用户中心-重置操作员密码", responseReference = "put")
    public ResponseMessage<ResetPasswordInfo> resetPwd(@RequestBody ResetPasswordInfo info) throws Exception {
       accountBiz.resetPwd(info);

        return ok(info);
    }

    @PutMapping("/authorize")
    @ApiOperation(value = "用户中心-操作员账号授权", responseReference = "put")
    public  ResponseMessage<SaveAccountAuthorizeInfo> authorize(@RequestBody SaveAccountAuthorizeInfo info)  throws Exception{
        accountBiz.authorize(info);

        return ok();
    }

    @PutMapping("/diabledAcc/{accountId}")
    @ApiOperation(value = "用户中心-禁用操作员账号", responseReference = "put")
    public  ResponseMessage<Long> diabledAccount(@PathVariable Long accountId)  throws Exception{
        accountBiz.disabled(accountId);
        return ok(accountId);
    }

    @PutMapping("/unDiabledAcc/{accountId}")
    @ApiOperation(value = "用户中心-激活操作员账号", responseReference = "put")
    public  ResponseMessage<Long> unDiabledAccount(@PathVariable Long accountId)  throws Exception{
        accountBiz.unDisabled(accountId);
        return ok(accountId);
    }

    @GetMapping("/accList")
    @ApiOperation(value = "用户中心-进入账号管理页面", responseReference = "get")
    public  ResponseMessage<List<AccountNameInfo>> accList()  throws Exception{
        LoginContext context = LoginContext.getLoginContext();

        return ok(accountBiz.accList(context.getAccountId()));
    }

    @GetMapping("/toChangeAcc/{accountId}")
    @ApiOperation(value = "用户中心-进入变更账号页面", responseReference = "get")
    public  ResponseMessage<ToEditAccountInfo> toChangeAccount(@PathVariable Long accountId)  throws Exception{
        return ok(accountBiz.toEditAccount(accountId));
    }

    @PutMapping("/changeAcc")
    @ApiOperation(value = "用户中心-变更账号", responseReference = "put")
    public  ResponseMessage<EditAccountInfo> changeAcc(@RequestBody EditAccountInfo info)  throws Exception{
        accountBiz.editAccount(info);
        return ok(info);
    }

    @PutMapping("/bindAcc")
    @ApiOperation(value = "用户中心-绑定账号", responseReference = "put")
    public  ResponseMessage<BindAccountInfo> bindAcc(@RequestBody BindAccountInfo info)  throws Exception{
        accountBiz.bindAcc(info);
        return ok(info);
    }
}
