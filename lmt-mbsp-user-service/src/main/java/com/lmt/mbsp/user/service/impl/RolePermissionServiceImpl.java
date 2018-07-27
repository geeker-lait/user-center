package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.role.RolePermissionMapper;
import com.lmt.mbsp.user.entity.role.RolePermission;
import com.lmt.mbsp.user.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 19:20
 * @Description:
 */
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission,Long> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public BaseMapper getBaseMapper() {
        return rolePermissionMapper;
    }

    @Override
    public RolePermission createEntity() {
        return new RolePermission();
    }
}
