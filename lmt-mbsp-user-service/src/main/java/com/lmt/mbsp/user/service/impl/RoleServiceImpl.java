package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.dao.mapper.role.RoleMapper;
import com.lmt.mbsp.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 角色服务实现.
 * @作者: lijing.
 * @创建时间: 2018-06-26 9:59.
 * @版本: 1.0 .
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public BaseMapper getBaseMapper() {
        return roleMapper;
    }

    @Override
    public Role createEntity() {
        return new Role();
    }
}
