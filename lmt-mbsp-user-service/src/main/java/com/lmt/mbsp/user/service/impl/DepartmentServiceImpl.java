package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.group.DepartmentMapper;
import com.lmt.mbsp.user.entity.group.Department;
import com.lmt.mbsp.user.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 描述：部门
 * 作者：Tangsm
 * 创建时间：2018-08-02 14:32:54
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long> implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return departmentMapper;
    }

    @Override
    public Department createEntity() {
        return new Department();
    }

    @Override
    public Department selectByName(String name) {
        return departmentMapper.selectByName(name);
    }

    @Override
    public Department selectByGroupId(Long groupId){return departmentMapper.selectByGroupId(groupId);}
}
