package com.lmt.mbsp.user.backend.biz;


import com.lmt.mbsp.user.vo.role.AddRoleInfo;
import com.lmt.mbsp.user.vo.role.EditRoleInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
import com.lmt.mbsp.user.vo.role.ToEditRoleInfo;

import java.util.List;

public interface RoleBiz {
    /**
     * 根据组ID查询对应的角色信息
     * @param groupId 组ID
     * @return List<RoleInfo>
     */
    List<RoleInfo> selByGroupId(Long groupId) throws Exception;

    /**
     * 查询所有角色信息
     * @return List<RoleInfo>
     */
    List<RoleInfo> selAll() throws Exception;

    /**
     * 根据角色名称查询角色是否存在
     * @param name 角色名称
     * @return Boolean true存在，false不存在
     */
    Boolean checkName(String name) throws Exception;

    /**
     * 新增角色
     * @param info 新增角色参数
     * @return Long
     */
    Long add(AddRoleInfo info) throws Exception;

    /**
     * 根据角色主键ID查询并组装编辑角色需要的信息
     * @param roleId    角色主键ID
     * @return ToEditRoleInfo
     */
    ToEditRoleInfo toEdit(Long roleId) throws Exception;

    /**
     * 修改角色信息
     * @param info 修改角色的参数
     */
    void edit(EditRoleInfo info) throws Exception;
}
