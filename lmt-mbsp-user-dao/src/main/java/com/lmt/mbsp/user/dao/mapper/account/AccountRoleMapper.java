package com.lmt.mbsp.user.dao.mapper.account;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.account.AccountRole;

import java.util.List;
import java.util.Map;

public interface AccountRoleMapper extends BaseMapper<AccountRole, Long> {
    /**
     * 根据账号ID删除该账号下所有角色关联表信息
     * @param accountId 账号ID
     */
    void deleteByAccountId(Long accountId);

    /**
     * 根据账号ID查询账号角色信息
     * @param accountId 账号ID
     * @return List<AccountRole>
     */
    List<AccountRole> selectByAccountId(Long accountId);
}