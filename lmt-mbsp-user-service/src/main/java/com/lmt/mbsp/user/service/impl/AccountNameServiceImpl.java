package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.account.AccountNameMapper;
import com.lmt.mbsp.user.entity.account.AccountName;
import com.lmt.mbsp.user.service.AccountNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/*
 * @描述：账号密码接口实现
 * @作者：Tangsm
 * @创建时间：2018-07-10 13:35:01
 */
@Service
public class AccountNameServiceImpl extends BaseServiceImpl<AccountName,Long> implements AccountNameService {
    @Autowired
    private AccountNameMapper accountNameMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return accountNameMapper;
    }

    @Override
    public AccountName createEntity() {
        return new AccountName();
    }

    @Override
    public AccountName selectByAccountName(String accountName){
        return accountNameMapper.selectByAccountName(accountName);
    }

    @Override
    public List<AccountName> selectByAccountId(Long accountId){
        return accountNameMapper.selectByAccountId(accountId);
    }
}
