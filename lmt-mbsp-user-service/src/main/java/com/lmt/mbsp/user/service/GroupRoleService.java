package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.GroupRole;

import java.util.List;

public interface GroupRoleService extends CrudService<GroupRole,Long> {
    /**
     * 删除组下所有角色信息
     * @param groupId   组ID
     */
    void deleteByGroupId(Long groupId) throws ServiceException;

    /**
     * 根据组ID查询组下所有关联角色信息
     * @param groupId 组ID
     * @return List<GroupRole>
     */
    List<GroupRole> selectByGroupId(Long groupId) throws ServiceException;
}
