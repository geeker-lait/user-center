package com.lmt.mbsp.user.dao.mapper.account;

import com.lmt.mbsp.user.entity.account.Account;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account, Long> {
}