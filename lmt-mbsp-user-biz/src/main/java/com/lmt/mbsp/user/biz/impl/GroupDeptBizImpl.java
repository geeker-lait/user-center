package com.lmt.mbsp.user.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.TreeCodeUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.biz.GroupDeptBiz;
import com.lmt.mbsp.user.dto.GroupQuery;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.entity.group.GroupRole;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.service.GroupRoleService;
import com.lmt.mbsp.user.service.GroupService;
import com.lmt.mbsp.user.service.RoleService;
import com.lmt.mbsp.user.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 描述: 部门聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-29 9:27.
 */
@Service
public class GroupDeptBizImpl implements GroupDeptBiz {
    private GroupService groupService;
    private GroupRoleService groupRoleService;
    private RoleService roleService;

    @Autowired
    public void GroupDeptBizImpl(GroupService groupService,
                                 GroupRoleService groupRoleService,
                                 RoleService roleService){
        this.groupService = groupService;
        this.groupRoleService = groupRoleService;
        this.roleService = roleService;
    }

    @Override
    public List<GroupInfo> selById(Long id) throws Exception{
        if (id != null) {
            return groupToGroupInfos(groupService.selectSonTreeById(id));
        }else {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<GroupInfo> selByGradeAndName(Integer grade, String name) throws Exception{
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setGrade(grade);
        groupQuery.setName(name);

        return groupToGroupInfos(groupService.select(groupQuery));
    }

    @Override
    public boolean selByGroupIdAndName(Long groupId, String name) throws Exception{
        Group group = selectByGroupIdAndName(groupId, name);
        if (group == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean selByGroupName(String name) throws Exception{
        Group group = groupService.selectByGroupName(name);
        if (group == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public ToAddDeptInfo toAddDept(String parentCode) throws Exception{
        ToAddDeptInfo addDeptInfo = new ToAddDeptInfo();

        if (!StringUtils.isNullOrEmpty(parentCode)){

            // 父部门信息
            GroupQuery groupQuery = new GroupQuery();
            groupQuery.setCodes(splitToList(parentCode.substring(1, parentCode.length() - 1), "\\|"));
            groupQuery.setSortName("code");
            groupQuery.setSortType(0);
            groupQuery.setTyp(0);
            groupQuery.setGrade(1);

            addDeptInfo.setParentInfos(groupToGroupInfos(groupService.select(groupQuery)));
        }

        // 所有角色信息
        addDeptInfo.setRoleInfos(roleToRoleInfos(roleService.select()));

        return addDeptInfo;
    }

    @Override
    public Long addDept(AddDeptInfo info) throws Exception{
        // 验证数据
        checkEx(info);

        Long pid;
        if (info.getParentId() != null && info.getParentId() > 0L){
            pid = info.getParentId();
        }else {
            pid = info.getGroupId();
        }

        // 获取父级编码
        Group group = selGroupById(pid, 1);

        // 根据当前级别最大code进行+1返回
        String code = assemblyCurrentCode(groupService.selectMaxCodeByPid(pid), group.getCode());

        // 保存部门信息
        long deptId = saveDept(info, code);

        // 保存组角色关联表信息
        saveGroupRoles(deptId, info.getRoleIds());

        return deptId;
    }

    @Override
    public ToEditDeptInfo toEditDept(Long id) throws Exception{
        Group group = selGroupById(id, 1);

        ToEditDeptInfo info = new ToEditDeptInfo();
        info.setCode(group.getCode());
        info.setId(group.getId());
        info.setName(group.getName());
        info.setCreateTime(group.getCreateTime());

        // 父部门信息
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setCodes(splitToList(group.getCodePath().substring(1, group.getCodePath().length() - 1), "\\|"));
        groupQuery.setSortName("code");
        groupQuery.setSortType(0);
        groupQuery.setTyp(0);
        groupQuery.setGrade(1);

        info.setParentInfos(groupToGroupInfos(groupService.select(groupQuery)));

        // 已赋角色信息
        List<GroupRole> groupRoles = groupRoleService.selectByGroupId(group.getId());
        info.setSelectedRoleIds(BeanUtils.getPropertyValues2List(groupRoles, "roleId"));

        // 所有角色信息
        info.setRoleInfos(roleToRoleInfos(roleService.select()));

        return info;
    }

    @Override
    public void editDept(EditDeptInfo info) throws Exception{
        Group group = selGroupById(info.getId(), 1);
        Group groupName = selectByGroupIdAndName(group.getGroupId(), info.getName());

        // 检测名称是否已经存在
        if (group.getId() != groupName.getId()){
            throw new BusinessException("该部门名称已经存在！");
        }

        group.setName(info.getName());

        // 更新部门信息
        groupService.updateByPk(group.getId(), group);

        // 删除部门角色信息
        groupRoleService.deleteByGroupId(group.getGroupId());

        // 重新保存部门角色信息
        saveGroupRoles(group.getGroupId(), info.getRoleIds());
    }

    @Override
    public DeptDetailInfo detailDept(Long id) throws Exception{
        Group group = selGroupById(id, 1);

        DeptDetailInfo detailInfo = new DeptDetailInfo();
        detailInfo.setDeptInfo(groupToInfo(group));

        // 父部门信息
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setCodes(splitToList(group.getCodePath().substring(1, group.getCodePath().length() - 1), "\\|"));
        groupQuery.setSortName("code");
        groupQuery.setSortType(0);
        groupQuery.setTyp(0);
        groupQuery.setGrade(1);

        detailInfo.setParentInfos(groupToGroupInfos(groupService.select(groupQuery)));

        // 查询组角色关联表
        List<GroupRole> groupRoles = groupRoleService.selectByGroupId(group.getId());

        // 已赋角色信息
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setIds(BeanUtils.getPropertyValues2List(groupRoles, "roleId"));

        if (roleQuery.getIds().size() > 0){
            List<Role> roleList = roleService.select(roleQuery);
            detailInfo.setSelectedRoleIds(roleToRoleInfos(roleList));
        }

        return detailInfo;
    }

    @Override
    public void disable(Long id) throws Exception{
        Group group = selGroupById(id, null);

        group.setState(1);

        groupService.updateByPk(id, group);
    }

    @Override
    public void unDisable(Long id) throws Exception{
        Group group = selGroupById(id, null);

        group.setState(0);

        groupService.updateByPk(id, group);
    }

    @Override
    public List<GroupInfo> toAddCompany(Long parentId) throws Exception{
        if (parentId != null && parentId > 0L){
            // 父公司信息
            Group parentGroup = groupService.selectParentTreeById(parentId);
            List<GroupInfo> parentInfos = new ArrayList<>();

            ascSort4groupToGroupInfos(parentGroup, parentInfos);

            return parentInfos;
        }else {
            return null;
        }
    }

    @Override
    public Long addCompany(Long groupId, String name) throws Exception{
        return saveCompany(groupId, name);
    }

    @Override
    public void editCompany(Long groupId, String name) throws Exception{
        Group group = selGroupById(groupId, 0);

        group.setName(name);

        groupService.updateByPk(groupId, group);
    }

    @Override
    public InnerCompanyDetailInfo detailCompany(Long id) throws Exception{
        InnerCompanyDetailInfo info = new InnerCompanyDetailInfo();

        // 当前需修改的公司信息
        GroupInfo group = groupToInfo(selGroupById(id, 0));

        // 父公司信息
        Group parentGroup = groupService.selectParentTreeById(group.getPid());
        List<GroupInfo> parentInfos = new ArrayList<>();

        ascSort4groupToGroupInfos(parentGroup, parentInfos);

        info.setParentInfos(parentInfos);
        info.setGroupInfo(group);

        return info;
    }

    /**
     * 根据子级循环出父级信息，并按父级》子级排序
     * @param group 查询出含有父级的对象
     * @param infos 重新组装父级的集合
     * @return GroupInfo
     */
    private GroupInfo ascSort4groupToGroupInfos(Group group, List<GroupInfo> infos){
        if (group != null){
            GroupInfo info = groupToInfo(group);

            if (group.getParent() != null){
                ascSort4groupToGroupInfos(group.getParent(), infos);
            }

            infos.add(info);

            return info;
        }

        return null;
    }
    /**
     * 根据当前最大code，+1返回，如果不足两位前面自动补0
     * @param maxCode   当前最大级别编码
     * @param parentCode 父级编码
     * @return String
     */
    private static String assemblyCurrentCode(String maxCode, String parentCode){
        String code = "";
        if (StringUtils.isNullOrEmpty(maxCode)){
            code = "01";
        }else {
            String temp = maxCode.substring(maxCode.length() - 2, maxCode.length());
            int num = Integer.valueOf(temp) + 1;
            if(num > 9){
                code = num + "";
            }else {
                code = "0" + num;
            }
        }

        return parentCode + code;
    }
    /**
     * 验证数据
     * @param info  验证源
     * @throws Exception
     */
    private void checkEx(AddDeptInfo info) throws Exception{
        if (info.getGroupId() == null || info.getGroupId() == 0L){
            throw new BusinessException("组ID不能为空！");
        }

        if (StringUtils.isNullOrEmpty(info.getName())){
            throw new BusinessException("名称不能为空！");
        }
        if (selByGroupIdAndName(info.getGroupId(), info.getName())){
            throw new BusinessException("该部门已经存在！");
        }
    }

    /**
     * 保存组角色关联表
     * @param groupId 组ID
     * @param rolesId 角色ID集合
     */
    private void saveGroupRoles(Long groupId, String rolesId) throws Exception{
        if (!StringUtils.isNullOrEmpty(rolesId)){
            String[] ids = rolesId.split(",");
            for (String id : ids) {
                saveGroupRole(groupId, Long.valueOf(id));
            }
        }
    }

    /**
     * 保存组角色关联表
     * @param groupId   组ID
     * @param roleId    角色ID
     */
    private void saveGroupRole(Long groupId, Long roleId) throws Exception{
        GroupRole groupRole = new GroupRole();
        groupRole.setGroupId(groupId);
        groupRole.setRoleId(roleId);

        groupRoleService.insert(groupRole);
    }

    /**
     * 组装并保存部门信息
     * @param info 部门信息对象
     * @param code 当前级别编码
     * @return Long
     * @throws Exception
     */
    private Long saveDept(AddDeptInfo info, String code) throws Exception{
        Group group = new Group();
        group.setGroupId(info.getGroupId());
        group.setName(info.getName());
        group.setPid(info.getParentId());
        group.setCode(code);
        group.setCodePath(TreeCodeUtils.splitCode(group.getCode(), 2, "|"));
        group.setState(0);
        group.setTyp(0);
        group.setGrade(1);
        group.setCreateTime(new Date());

        return groupService.insert(group);
    }

    /**
     * 组装并保存部门信息
     * @param groupId 父公司ID（可为空，如果为空代表创建一级公司）
     * @param name  公司名称
     * @return Long
     * @throws Exception
     */
    private Long saveCompany(Long groupId, String name) throws Exception{
        Group group = new Group();
        group.setGroupId(groupId);
        group.setName(name);
        group.setPid(groupId);
        group.setCode("");
        group.setCodePath("");
        group.setState(0);
        group.setTyp(0);
        group.setGrade(0);
        group.setCreateTime(new Date());

        return groupService.insert(group);
    }

    /**
     * 根据主键ID查询组信息
     * @param id    主键ID
     * @param type  0查询公司 1查询部门
     * @return Group
     * @throws Exception
     */
    private Group selGroupById(Long id, Integer type) throws Exception{
        Group group = groupService.selectByPk(id);
        if (group == null){
            if (type == 0){
                throw new BusinessException("未查询到该部门信息！");
            }else {
                throw new BusinessException("未查询到该公司信息！");
            }
        }else {
            if (type != null){
                if (type == 0 && group.getGrade() != 0){
                    throw new BusinessException("未查询到该部门信息！");
                }else if (type == 1 && group.getGrade() != 1){
                    throw new BusinessException("未查询到该公司信息！");
                }
            }

            return group;
        }
    }

    /**
     * 根据公司ID+名称查询组信息
     * @param groupId   公司ID
     * @param name  名称
     * @return Group
     */
    private Group selectByGroupIdAndName(Long groupId, String name){
        return groupService.selectByGroupIdAndName(groupId, name);
    }

    /**
     * 递归将子数据对象进行转换为info返回
     * @param groups 公司部门信息
     * @return List<GroupInfo>
     */
    private List<GroupInfo> groupToGroupInfos(List<Group> groups){
        List<GroupInfo> groupInfos = new ArrayList<>();
        if (groups != null && groups.size() > 0){
            for (Group group : groups){
                if (group != null){
                    GroupInfo info = groupToInfo(group);

                    if (group.getChildrenList() != null && group.getChildrenList().size() > 0){
                        info.setChildren(groupToGroupInfos(group.getChildrenList()));
                    }

                    groupInfos.add(info);
                }
            }
        }

        return groupInfos;
    }

    /**
     * 将角色Entity对象进行转换为info返回
     * @param roleList 角色信息
     * @return List<RoleInfo>
     */
    private List<RoleInfo> roleToRoleInfos(List<Role> roleList){
        List<RoleInfo> roleInfoList = new ArrayList<>();
        if (roleList != null && roleList.size() > 0){
            for (Role role : roleList){
                if (role != null){
                    RoleInfo info = new RoleInfo();
                    BeanUtils.copyProperties(role, info);

                    roleInfoList.add(info);
                }
            }
        }

        return roleInfoList;
    }

    /**
     * 将组对象转为Info对象
     * @param group 组对象
     * @return
     */
    private GroupInfo groupToInfo(Group group){
        GroupInfo info = new GroupInfo();
        BeanUtils.copyProperties(group, info);

        return info;
    }

    /**
     * 将指定格式分割的字符串转为List<?>
     * @param source    含有指定分隔符的字符串，如1|2|3
     * @param splitChar 分隔符，如“|”
     * @return List<V>
     */
    private <V> List<V> splitToList(String source, String splitChar){
        List<V> result = new ArrayList<>();
        String[] split = source.split(splitChar);
        if (split.length > 0){
            for (String s : split){
                result.add((V)s);
            }
        }

        return result;
    }
}
