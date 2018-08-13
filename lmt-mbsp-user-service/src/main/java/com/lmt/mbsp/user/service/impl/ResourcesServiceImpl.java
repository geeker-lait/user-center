package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.resources.ResourcesMapper;
import com.lmt.mbsp.user.entity.resources.Resources;
import com.lmt.mbsp.user.dao.mapper.resources.UrlResourcesMapper;
import com.lmt.mbsp.user.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 资源服务实现.
 * @作者: lijing.
 * @创建时间: 2018-06-27 17:27.
 * @版本: 1.0 .
 */
@Service
public class ResourcesServiceImpl  extends BaseServiceImpl<Resources,Long> implements ResourcesService {
    @Autowired
    private ResourcesMapper resourcesMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return resourcesMapper;
    }

    @Override
    public Resources createEntity() {
        return new Resources();
    }
}
