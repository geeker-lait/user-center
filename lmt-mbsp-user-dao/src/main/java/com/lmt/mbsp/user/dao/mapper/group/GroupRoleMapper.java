package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.group.GroupRole;

import java.util.List;

public interface GroupRoleMapper extends BaseMapper<GroupRole,Long> {
    /**
     * 删除组下所有角色信息
     * @param groupId   组ID
     */
    void deleteByGroupId(Long groupId);

    /**
     * 根据组ID查询组下所有关联角色信息
     * @param groupId 组ID
     * @return List<GroupRole>
     */
    List<GroupRole> selectByGroupId(Long groupId);
}
