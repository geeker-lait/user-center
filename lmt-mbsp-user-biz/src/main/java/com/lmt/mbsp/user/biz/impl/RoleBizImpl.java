package com.lmt.mbsp.user.biz.impl;

import com.lmt.framework.support.bean.EntityToModelUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.biz.RoleBiz;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.group.GroupRole;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.service.GroupRoleService;
import com.lmt.mbsp.user.service.RoleService;
import com.lmt.mbsp.user.vo.role.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 描述: 角色聚合层.
 * 作者: lijing.
 * 创建时间: 2018-07-03 16:25.
 */
@Service
public class RoleBizImpl implements RoleBiz {
    private RoleService roleService;
    private GroupRoleService groupRoleService;

    @Autowired
    public RoleBizImpl(RoleService roleService,
                       GroupRoleService groupRoleService){
        this.roleService = roleService;
        this.groupRoleService = groupRoleService;
    }
    @Override
    public List<RoleInfo> selByGroupId(Long groupId) throws Exception{
        return EntityToModelUtils.entitysToInfos(selRolesByGroupId(groupId), RoleInfo.class);
    }

    @Override
    public List<RoleInfo> selAll() throws Exception{
        return EntityToModelUtils.entitysToInfos(groupRoleService.select(), RoleInfo.class);
    }

    /**
     * 根据公司ID查询角色信息
     * @param groupId 公司ID
     * @return List<Role>
     */
    private List<Role> selRolesByGroupId(Long groupId) {
        List<GroupRole> groupRoles = groupRoleService.selectByGroupId(groupId);
        List<Long> roleIds = BeanUtils.getPropertyValues2List(groupRoles, "roleId");
        if (roleIds != null && roleIds.size() > 0){
            RoleQuery roleQuery = new RoleQuery();
            roleQuery.setIds(roleIds);

            return roleService.select(roleQuery);
        }else {
            return null;
        }
    }
}
