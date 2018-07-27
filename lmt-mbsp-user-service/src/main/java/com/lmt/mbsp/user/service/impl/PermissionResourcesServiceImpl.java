package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.permission.PermissionResourcesMapper;
import com.lmt.mbsp.user.entity.permission.PermissionResources;
import com.lmt.mbsp.user.service.PermissionResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 19:27
 * @Description:
 */
@Service
public class PermissionResourcesServiceImpl extends BaseServiceImpl<PermissionResources, Long> implements PermissionResourcesService {


    @Autowired
    private PermissionResourcesMapper permissionResourcesMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return permissionResourcesMapper;
    }

    @Override
    public PermissionResources createEntity() {
        return new PermissionResources();
    }
}
