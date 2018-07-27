package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.user.UserAccount;

import java.util.List;

/**
 * 描述: 用户账号.
 * 作者: Tangsm.
 * 创建时间: 2018-07-03 9:42.
 */
public interface UserAccountService extends CrudService<UserAccount, Long> {
    /**
     * 根据用户ID查询用户账号关联信息
     * @param userId 用ID
     * @return UserAccount
     */
    List<UserAccount> selectByUserId(Long userId) throws ServiceException;
}
