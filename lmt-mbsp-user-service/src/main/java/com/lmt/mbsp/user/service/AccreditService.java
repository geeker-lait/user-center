package com.lmt.mbsp.user.service;

import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.accredit.Accredit;

import java.util.List;

public interface AccreditService extends CrudService<Accredit, Long> {
    /**
     * 根据角色ID查询角色权限关联信息集合
     * @param roleId 角色ID
     * @return List<Accredit>
     */
    List<Accredit> selectByRoleId(Long roleId);

    /**
     * 根据角色ID查询角色权限关联信息集合
     * @param roleIds 角色ID
     * @return List<Accredit>
     */
    List<Accredit> selectByRoleIds(List<Long> roleIds);


    /**
     * 根据角色主键ID删除该角色下所有的权限关联信息
     * @param roleId 角色主键ID
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据角色ID查询角色所关联的资源信息集合
     * @param roleIds 角色ID
     * @return List<Accredit>
     */
    List<Accredit> selectRoleResourcesByRoleIds(List<Long> roleIds);
}
