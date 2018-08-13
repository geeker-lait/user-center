package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.account.AccountMapper;
import com.lmt.mbsp.user.service.AccountService;
import com.lmt.mbsp.user.entity.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述: 账号服务实现.
 * 作者: Tangsm.
 * 创建时间: 2018-06-26 9:59.
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return accountMapper;
    }

    @Override
    public Account createEntity() {
        return new Account();
    }
}
