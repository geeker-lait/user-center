package com.lmt.mbsp.user.biz;


import com.lmt.mbsp.user.vo.role.RoleInfo;

import java.util.List;

public interface RoleBiz {
    /**
     * 根据组ID查询对应的角色信息
     * @param groupId 组ID
     * @return
     * @throws Exception
     */
    List<RoleInfo> selByGroupId(Long groupId) throws Exception;

    /**
     * 查询所有角色信息
     * @return
     * @throws Exception
     */
    List<RoleInfo> selAll() throws Exception;
}
