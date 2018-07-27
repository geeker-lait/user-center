package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.entity.user.UserAccount;
import com.lmt.mbsp.user.dao.mapper.user.UserAccountMapper;
import com.lmt.mbsp.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述: 用户账号.
 * 作者: Tangsm.
 * 创建时间: 2018-07-03 9:42.
 */
@Service("userAccountService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount,Long> implements UserAccountService {
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public BaseMapper<UserAccount, Long> getBaseMapper() {
        return userAccountMapper;
    }
    @Override
    public UserAccount createEntity() {
        return new UserAccount();
    }

    @Override
    public List<UserAccount> selectByUserId(Long userId) throws ServiceException {
        return  userAccountMapper.selectByUserId(userId);
    }
}
