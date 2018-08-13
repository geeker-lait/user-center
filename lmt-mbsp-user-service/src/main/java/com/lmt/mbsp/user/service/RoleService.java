package com.lmt.mbsp.user.service;

import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.role.Role;

/**
 * Auther: Tangsm
 * Tel:15801818092
 * Date: 6/1/2018 10:03
 * Description:角色接口
 */
public interface RoleService extends CrudService<Role,Long> {
    /**
     * 根据角色名称查询角色信息
     * @param name 角色名称
     * @return Role
     * @throws Exception
     */
    Role selectByName(String name) throws Exception;
}
