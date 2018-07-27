package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.AccountRoleQuery;
import com.lmt.mbsp.user.vo.AccountRoleInfo;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.service.AccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/20/2018 11:02
 * @Description:
 */
@RestController
@RequestMapping("account-role")
public class AccountRoleController implements CrudController<AccountRole,Long,AccountRoleQuery,AccountRoleInfo> {
    @Autowired
    private AccountRoleService accountRoleService;

    @Override
    public CrudService<AccountRole, Long> getService() {
        return accountRoleService;
    }
}

