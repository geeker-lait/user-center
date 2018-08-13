package com.lmt.mbsp.user.backend.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.support.bean.EntityToModelUtils;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.backend.biz.RoleBiz;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.group.GroupRole;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.accredit.Accredit;
import com.lmt.mbsp.user.service.GroupRoleService;
import com.lmt.mbsp.user.service.RolePermissionService;
import com.lmt.mbsp.user.service.RoleService;
import com.lmt.mbsp.user.vo.role.AddRoleInfo;
import com.lmt.mbsp.user.vo.role.EditRoleInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
import com.lmt.mbsp.user.vo.role.ToEditRoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 描述: 角色聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-07-03 16:25.
 */
@Service
public class RoleBizImpl implements RoleBiz {
    private RoleService roleService;
    private GroupRoleService groupRoleService;
    private RolePermissionService rolePermissionService;

    @Autowired
    public RoleBizImpl(RoleService roleService,
                       GroupRoleService groupRoleService,
                       RolePermissionService rolePermissionService){
        this.roleService = roleService;
        this.groupRoleService = groupRoleService;
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public List<RoleInfo> selByGroupId(Long groupId) throws Exception{
        return EntityToModelUtils.entitysToInfos(selRolesByGroupId(groupId), RoleInfo.class);
    }

    @Override
    public List<RoleInfo> selAll() throws Exception{
        return EntityToModelUtils.entitysToInfos(roleService.select(), RoleInfo.class);
    }

    @Override
    public Boolean checkName(String name) throws Exception{
        Role role = roleService.selectByName(name);
        return role != null;
    }

    @Override
    public Long add(AddRoleInfo info) throws Exception{
        if (StringUtils.isNullOrEmpty(info.getName())){
            throw new BusinessException("角色名称不能为空！");
        }
        if (checkName(info.getName())){
            throw new BusinessException("该角色名称已经存在！");
        }

        // 保存角色信息
        Long roleId = saveRole(info);

        // 保存角色权限关联表
        saveRolePermissions(roleId, info.getPermissionIds());

        return roleId;
    }

    @Override
    public ToEditRoleInfo toEdit(Long roleId) throws Exception{
        Role role = selById4Ex(roleId);

        List<Accredit> accredits = rolePermissionService.selectByRoleId(roleId);

        ToEditRoleInfo info = new ToEditRoleInfo();
        info.setRoleInfo(EntityToModelUtils.entityToInfo(role, new RoleInfo()));
        info.setGivenPermitIds(BeanUtils.getPropertyValues2List(accredits, "permissionId"));

        return info;
    }

    @Override
    public void edit(EditRoleInfo info) throws Exception{
        Role role = selById4Ex(info.getId());

        Role roleTemp = roleService.selectByName(info.getName());
        if (roleTemp != null && !roleTemp.getId().equals(role.getId())){
            throw new BusinessException("该角色名称已经存在！");
        }

        // 更新角色信息
        role.setName(info.getName());
        role.setDescr(info.getDescr());

        roleService.updateByPk(info.getId(), role);

        // 删除角色权限关联表
        rolePermissionService.deleteByRoleId(info.getId());

        // 重新保存角色权限关联表
        saveRolePermissions(info.getId(), info.getPermissionIds());
    }

    /**
     * 根据角色主键ID查询角色信息，不存在抛出异常
     * @param id 角色主键ID
     * @return Role
     */
    private Role selById4Ex(Long id){
        Role role = roleService.selectByPk(id);
        if (role == null){
            throw new BusinessException("对不起，未查询到该角色信息！");
        }

        return role;
    }

    /**
     * 保存角色信息
     * @param info 角色参数
     * @return Long
     */
    private Long saveRole(AddRoleInfo info)throws Exception{
        Role role = new Role();
        role.setName(info.getName());
        role.setDescr(info.getDescr());
        role.setCreateTime(new Date());

        return roleService.insert(role);
    }

    /**
     * 保存账号角色关联表
     *
     * @param roleId   角色IDID
     * @param permissionIds 权限ID集合
     */
    private void saveRolePermissions(Long roleId, String permissionIds) throws Exception {
        if (!StringUtils.isNullOrEmpty(permissionIds)) {
            String[] ids = permissionIds.split(",");
            for (String id : ids) {
                Accredit role = new Accredit();
                role.setRoleId(roleId);
                role.setPermissionId(Long.valueOf(id));

                rolePermissionService.insert(role);
            }
        }
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
