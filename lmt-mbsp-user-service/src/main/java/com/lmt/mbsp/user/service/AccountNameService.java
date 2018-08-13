package com.lmt.mbsp.user.service;

import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.account.AccountName;

import java.util.List;


/**
 * 描述: 账号密码接口
 * 作者: Tangsm.
 * 创建时间: 2018-07-10 13:34.
 */
public interface AccountNameService extends CrudService<AccountName,Long> {
    /**
     * 根据账号名称查询
     * @param accountName 账号名称
     * @return AccountName
     */
    AccountName selectByAccountName(String accountName);

    /**
     * 根据主账号ID查询该账号所有账号名称
     * @param accountId 账号ID
     * @return List<AccountName>
     */
    List<AccountName> selectByAccountId(Long accountId);
}
