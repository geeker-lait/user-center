package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.accredit.AccreditMapper;
import com.lmt.mbsp.user.entity.accredit.Accredit;
import com.lmt.mbsp.user.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 19:20
 * @Description:
 */
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<Accredit,Long> implements RolePermissionService {

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
    public List<Accredit> selectByRoleId(Long roleId){
        return accreditMapper.selectByRoleId( roleId);
    }

    @Override
    public List<Accredit> selectByRoleIds(List<Long> roleIds) {
        return accreditMapper.selectByRoleIds(roleIds);
    }

    @Override
    public void deleteByRoleId(Long roleId){
        accreditMapper.deleteByRoleId(roleId);
    }
}
