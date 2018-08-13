package com.lmt.mbsp.user.dao.mapper.user;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.user.UserAccount;

import java.util.List;

public interface UserAccountMapper extends BaseMapper<UserAccount, Long> {
    /**
     * 根据用户ID查询用户账号关联信息
     * @param userId 用ID
     * @return UserAccount
     */
    UserAccount selectByUserId(Long userId);

    /**
     * 根据账号ID查询用户账号关联信息
     * @param accountId 用ID
     * @return UserAccount
     */
    UserAccount selectByAccountId(Long accountId);
}