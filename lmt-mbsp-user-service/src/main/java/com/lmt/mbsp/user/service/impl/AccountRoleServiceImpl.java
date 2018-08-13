package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.account.AccountRoleMapper;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.service.AccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountRoleServiceImpl extends BaseServiceImpl<AccountRole, Long> implements AccountRoleService {
    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return accountRoleMapper;
    }

    @Override
    public AccountRole createEntity() {
        return new AccountRole();
    }

    @Override
    public void deleteByAccountId(Long accountId) throws ServiceException {
        accountRoleMapper.deleteByAccountId(accountId);
    }

    @Override
    public List<AccountRole> selectByAccountId(Long accountId) throws ServiceException{
        return accountRoleMapper.selectByAccountId(accountId);
    }
}
