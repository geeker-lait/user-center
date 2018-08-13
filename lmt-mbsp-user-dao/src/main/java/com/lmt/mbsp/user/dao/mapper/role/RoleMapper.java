package com.lmt.mbsp.user.dao.mapper.role;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.role.Role;

public interface RoleMapper  extends BaseMapper<Role,Long> {
    /**
     * 根据角色名称查询角色信息
     * @param name 角色名称
     * @return Role
     * @throws Exception
     */
    Role selectByName(String name) throws Exception;
}