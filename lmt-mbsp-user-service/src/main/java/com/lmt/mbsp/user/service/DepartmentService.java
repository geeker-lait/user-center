package com.lmt.mbsp.user.service;

import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.Department;

public interface DepartmentService extends CrudService<Department,Long> {
    /**
     * 根据部门名称查询该部门是否存在
     * @param name 部门名称
     * @return Department
     */
    Department selectByName(String name);

    /**
     * 根据组ID查询部门信息
     * @param groupId 公司主键ID
     * @return Department
     */
    Department selectByGroupId(Long groupId);
}
