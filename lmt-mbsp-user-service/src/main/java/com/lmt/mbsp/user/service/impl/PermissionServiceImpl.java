package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.entity.permission.Permission;
import com.lmt.mbsp.user.dao.mapper.permission.PermissionMapper;
import com.lmt.mbsp.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 权限服务实现.
 * @作者: lijing.
 * @创建时间: 2018-06-27 16:54.
 * @版本: 1.0 .
 */
@Service
public class PermissionServiceImpl  extends BaseServiceImpl<Permission,Long> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return permissionMapper;
    }

    @Override
    public Permission createEntity() {
        return new Permission();
    }
}
