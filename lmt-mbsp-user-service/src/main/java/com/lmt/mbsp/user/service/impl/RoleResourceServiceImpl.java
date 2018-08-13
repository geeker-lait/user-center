package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.accredit.AccreditMapper;
import com.lmt.mbsp.user.entity.accredit.Accredit;
import com.lmt.mbsp.user.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/18/2018 13:22
 * @Description:
 */
@Service
public class RoleResourceServiceImpl extends BaseServiceImpl<Accredit, Long> implements RoleResourceService {


    @Autowired
    private AccreditMapper accreditMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return accreditMapper;
    }

    @Override
    public Accredit createEntity() {
        return new Accredit();
    }


    @Override
    public List<Accredit> selectRoleResourcesByRoleIds(List<Long> roleIds) {
        return accreditMapper.selectByRoleIds(roleIds);
    }
}
