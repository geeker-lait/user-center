package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.account.AccountRole;

import java.util.List;

/**
 * @描述：账号角色关联表接口
 * @作者：Tangsm
 * @创建时间：2018-07-12 14:42:04
 */
public interface AccountRoleService extends CrudService<AccountRole,Long> {
    /**
     * 根据账号ID删除该账号下所有角色关联表信息
     * @param accountId 账号ID
     */
    void deleteByAccountId(Long accountId) throws ServiceException;

    /**
     * 根据账号ID查询账号角色信息
     * @param accountId 账号ID
     * @return List<AccountRole>
     */
    List<AccountRole> selectByAccountId(Long accountId) throws ServiceException;
}
