package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.resources.UrlResourcesMapper;
import com.lmt.mbsp.user.dao.mapper.role.RolePermissionMapper;
import com.lmt.mbsp.user.entity.resources.UrlResources;
import com.lmt.mbsp.user.entity.role.RolePermission;
import com.lmt.mbsp.user.service.UrlResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/18/2018 13:22
 * @Description:
 */
@Service
public class UrlResourcesServiceImpl extends BaseServiceImpl<UrlResources,Long> implements UrlResourcesService {

    @Autowired
    private UrlResourcesMapper urlResourcesMapper;
    @Override
    public BaseMapper getBaseMapper() {
        return urlResourcesMapper;
    }
    @Override
    public UrlResources createEntity() {
        return new UrlResources();
    }
}
