package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.AccountBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.AccountQuery;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.service.AccountService;
import com.lmt.mbsp.user.vo.account.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * Auther: Tangsm
 * Description: 个人用户账号
 */
@RestController
@RequestMapping("/person-account")
public class PersonAccountController implements CrudController<Account, Long, AccountQuery, AccountInfo> {
    private AccountService accountService;
    private AccountBiz accountBiz;

    @Autowired
    public PersonAccountController(AccountService accountService, AccountBiz accountBiz) {
        this.accountService = accountService;
        this.accountBiz = accountBiz;
    }

    @Override
    public CrudService<Account, Long> getService() {
        return accountService;
    }

    /****************************************************************************************** 后台-个人用户账号 ******************************************************************/

    @PutMapping("/editPwd")
    @ApiOperation(value = "修改自己密码", responseReference = "put")
    public ResponseMessage<EditPasswordInfo> editSelfPwd(@RequestBody EditPasswordInfo info) throws Exception {
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());

        accountBiz.editPwd(info);

        return ok(info);
    }

    @PutMapping("/resetPwd")
    @ApiOperation(value = "后台-重置个人用户账号密码", responseReference = "put")
    public ResponseMessage<ResetPasswordInfo> resetPwd(@RequestBody ResetPasswordInfo info) throws Exception {
        accountBiz.resetPwd(info);
        return ok(info);
    }

    @PutMapping("/addManager/{accountId}")
    @ApiOperation(value = "后台-企业商户授权管理员", responseReference = "put")
    public  ResponseMessage<Long> addManager(@PathVariable Long accountId) throws Exception{
        accountBiz.addManager(accountId);

        return ok(accountId);
    }

    @PutMapping("/diabledAccount/{accountId}")
    @ApiOperation(value = "后台-禁用个人用户账号", responseReference = "put")
    public  ResponseMessage<Long> diabledAccount(@PathVariable Long accountId)  throws Exception{
        accountBiz.disabled(accountId);
        return ok(accountId);
    }

    @PutMapping("/unDiabledAccount/{accountId}")
    @ApiOperation(value = "后台-激活个人用户账号", responseReference = "put")
    public  ResponseMessage<Long> unDiabledAccount(@PathVariable Long accountId)  throws Exception{
        accountBiz.unDisabled(accountId);
        return ok(accountId);
    }
}
