package com.lmt.mbsp.user.dao.mapper.account;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.account.AccountName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountNameMapper extends BaseMapper<AccountName, Long> {
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