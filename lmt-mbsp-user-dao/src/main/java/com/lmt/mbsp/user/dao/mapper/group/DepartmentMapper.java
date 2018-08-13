package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.group.Department;

/*
 * 描述：部门
 * 作者：Tangsm
 * 创建时间：2018-08-02 14:34:28
 */
public interface DepartmentMapper extends BaseMapper<Department, Long> {
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
