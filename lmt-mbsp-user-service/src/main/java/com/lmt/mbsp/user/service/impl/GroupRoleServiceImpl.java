package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.account.AccountMapper;
import com.lmt.mbsp.user.dao.mapper.group.GroupRoleMapper;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.group.GroupRole;
import com.lmt.mbsp.user.service.GroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 描述：组角色
 * 作者：Tangsm
 * 创建时间：2018-07-20 16:29:30
 */
@Service
public class GroupRoleServiceImpl extends BaseServiceImpl<GroupRole, Long> implements GroupRoleService {
    @Autowired
    private GroupRoleMapper groupRoleMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return groupRoleMapper;
    }

    @Override
    public GroupRole createEntity() {
        return new GroupRole();
    }

    @Override
    public void deleteByGroupId(Long groupId) throws ServiceException {groupRoleMapper.deleteByGroupId(groupId);}

    @Override
    public List<GroupRole> selectByGroupId(Long groupId) throws ServiceException{return groupRoleMapper.selectByGroupId(groupId);}
}
