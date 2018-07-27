package com.lmt.mbsp.user.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.TreeCodeUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.biz.GroupCompanyBiz;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.entity.group.GroupImg;
import com.lmt.mbsp.user.entity.group.GroupInformation;
import com.lmt.mbsp.user.entity.group.GroupRole;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.service.*;
import com.lmt.mbsp.user.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 描述：组-企业商户
 * 作者：Tangsm
 * 创建时间：2018-07-24 14:03:49
 */
@Service
public class GroupCompanyBizImpl implements GroupCompanyBiz {
    private GroupInformationService groupInformationService;
    private GroupImgService groupImgService;
    private AccountRoleService accountRoleService;
    private UserService userService;
    private GroupService groupService;
    private GroupRoleService groupRoleService;
    private RoleService roleService;

    @Autowired
    public void GroupDeptBizImpl(GroupService groupService,
                                 GroupRoleService groupRoleService,
                                 RoleService roleService,
                                 GroupInformationService groupInformationService,
                                 GroupImgService groupImgService,
                                 AccountRoleService accountRoleService,
                                 UserService userService){
        this.groupService = groupService;
        this.groupRoleService = groupRoleService;
        this.roleService = roleService;
        this.groupInformationService = groupInformationService;
        this.groupImgService = groupImgService;
        this.accountRoleService = accountRoleService;
        this.userService = userService;
    }

    @Override
    public Long add(AddCompanyInfo info) throws Exception{
        // 保存企业表
        Long companyId = saveCompany(info);

        // 保存企业信息表
        saveCompanyInformation(companyId, info);

        // TODO
        // 保存证书
        // 保存授权书
        return companyId;
    }

    @Override
    public ToEditCompanyInfo toEdit(Long id) throws Exception{
        // 获取公司信息
        Group group = selGroupById(id);

        ToEditCompanyInfo info = new ToEditCompanyInfo();

        // 获取公司资料
        GroupInformation information = groupInformationService.selectByGroupId(id);
        if (information != null){
            BeanUtils.copyProperties(information, info);
        }

        // 放到information后面赋值，以免将information的主键ID进行了覆盖
        BeanUtils.copyProperties(group, info);

        // 获取公司图片
        info.setImgs(searchGroupImgToInfo(id));

        return info;
    }

    @Override
    public void edit(EditCompanyInfo info) throws Exception{
        // 更新公司信息
        updateGroup(info);

        // 更新公司资料
        updateGroupInfomation(info);

        // 删除证书
        groupImgService.deleteByGroupId(info.getId());

        // TODO
        // 重新保存证书
    }

    @Override
    public ToCompanyAuthorizeInfo toAuthorize(Long id) throws Exception{
        // 获取公司信息
        Group group = selGroupById(id);

        ToCompanyAuthorizeInfo info = new ToCompanyAuthorizeInfo();
        info.setId(group.getId());
        info.setName(group.getName());
        info.setRoles(rolesToInfos(roleService.select()));  // 所有角色信息

        // 已选择角色ID集合
        List<GroupRole> roles = groupRoleService.selectByGroupId(id);
        info.setGiveRoleIds(BeanUtils.getPropertyValues2List(roles, "id"));

        return info;
    }

    @Override
    public void authorize(SaveCompanyAuthorizeInfo info) throws Exception{
        // 获取公司信息
        Group group = selGroupById(info.getId());

        // 删除公司角色关联表
        groupRoleService.deleteByGroupId(info.getId());

        // 重新保存公司角色关联表
        saveGroupRoles(group.getGroupId(), info.getRoleIds());
    }

    @Override
    public CompanyDetailInfo detail(Long id) throws Exception{
        // 获取公司信息
        Group group = selGroupById(id);

        CompanyDetailInfo info = new CompanyDetailInfo();

        // 获取公司资料
        GroupInformation information = groupInformationService.selectByGroupId(id);
        if (information != null){
            BeanUtils.copyProperties(information, info);
        }

        // 放到information后面赋值，以免将information的主键ID进行了覆盖
        BeanUtils.copyProperties(group, info);

        // 获取公司图片
        info.setImgs(searchGroupImgToInfo(id));

        return info;
    }

    @Override
    public List<RoleInfo> searchRoles(Long groupId) throws Exception{
        List<GroupRole> groupRoles = groupRoleService.selectByGroupId(groupId);
        List<Long> ids = BeanUtils.getPropertyValues2List(groupRoles, "roleId");

        return searchRoleByIdsToInf(ids);
    }

    @Override
    public void disable(Long id) throws Exception{
        Group group = selGroupById(id);

        group.setState(1);

        groupService.updateByPk(id, group);
    }

    @Override
    public void unDisable(Long id) throws Exception{
        Group group = selGroupById(id);

        group.setState(0);

        groupService.updateByPk(id, group);
    }

    @Override
    public void audit(Long id, Integer type) throws Exception{
        Group group = selGroupById(id);
        if (group.getAuditState() != 0){
            throw new BusinessException("对不起，该企业商户已被审核，请勿重复操作！");
        }

        if (type == 0){
            group.setAuditState(1);
        }else {
            group.setAuditState(2);
        }

        groupService.updateByPk(id, group);
    }

    @Override
    public IndexInfo csIndex(Long accountId, Long userId, Long groupId) throws Exception{
        IndexInfo info = new IndexInfo();

        Group group = groupService.selectByPk(groupId);
        if (group != null){
            info.setName(group.getName());
            info.setOrganizationCode(group.getOrganizationCode());
            info.setState(group.getState());
            info.setAuditState(group.getAuditState());
            info.setCreateTime(group.getCreateTime());

            GroupInformation information = groupInformationService.selectByGroupId(groupId);
            if (information != null){
                info.setAddress(information.getAddress());
                info.setBusinessScope(information.getBusinessScope());
                info.setCapital(information.getCapital());
                info.setValidity(information.getValidity());
                info.setTyp(information.getTyp());
                info.setPhone(information.getPhone());
                info.setLegalPerson(information.getLegalPerson());
            }
        }

        info.setUserInfo(selByIdToInfo(userId)); // 获取用户信息
        info.setImgs(searchGroupImgToInfo(accountId));    // 获取公司图片
        info.setUserRole(searchAccountRoleAndToInfo(userId)); // 获取用户角色

        return info;
    }

    /**
     * 更加用户ID查询用户信息并转为info对象返回
     * @param userId 用户ID
     * @return UserInfo
     */
    private UserInfo selByIdToInfo(Long userId){
        User user = userService.selectByPk(userId);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);

        return userInfo;
    }

    /**
     * 根据账号ID查询账号角色信息，并转为info对象返回
     * @param accountId 账号ID
     * @return List<RoleInfo>
     */
    private List<RoleInfo> searchAccountRoleAndToInfo(Long accountId){
        List<AccountRole> accountRoles = accountRoleService.selectByAccountId(accountId);
        List<Long> ids = BeanUtils.getPropertyValues2List(accountRoles, "roleId");

        return searchRoleByIdsToInf(ids);
    }

    /**
     * 更加主键ID集合查询角色信息，并转为info对象返回
     * @param ids 主键ID集合
     * @return List<RoleInfo>
     */
    private List<RoleInfo> searchRoleByIdsToInf(List<Long> ids){
        if (ids != null && ids.size() > 0){
            RoleQuery roleQuery = new RoleQuery();
            roleQuery.setIds(ids);

            List<Role> roles = roleService.select(roleQuery);

            return rolesToInfos(roles);
        }else {
            return null;
        }
    }

    /**
     * 根据公司ID查询该公司下的图片集合，并转为info对象返回
     * @param groupId 公司ID
     * @return List<GroupImgInfo>
     */
    private List<GroupImgInfo> searchGroupImgToInfo(Long groupId){
        // 获取公司图片
        List<GroupImg> imgs = groupImgService.selectByGroupId(groupId);

        return groupImgsInfoToInfos(imgs);
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
     * 角色集合entity转为info集合
     * @param roles 角色集合entity
     * @return List<RoleInfo>
     */
    private List<RoleInfo> rolesToInfos(List<Role> roles){
        List<RoleInfo> infos = new ArrayList<>();
        if (roles != null && roles.size() > 0){
            for (Role role : roles){
                if (role != null){
                    infos.add(roleToInfo(role));
                }
            }
        }

        return infos;
    }
    /**
     * 角色entity转为info
     * @param role 角色对象
     * @return RoleInfo
     */
    private RoleInfo roleToInfo(Role role){
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(role, info);

        return info;
    }

    /**
     * 更新公司信息
     * @param info  需更新的参数对象
     * @throws Exception
     */
    private void updateGroup(EditCompanyInfo info) throws Exception {
        // 获取公司信息
        Group group = selGroupById(info.getId());

        Group groupTemp = groupService.selectByGroupName(info.getName());
        if (groupTemp != null && groupTemp.getId() != group.getId()){
            throw new BusinessException("对不起，该公司名已经存在！");
        }

        // 更新公司信息
        group.setName(group.getName());
        group.setOrganizationCode(group.getOrganizationCode());
        group.setAuditState(0); // 每次修改之后都需重新审核

        groupService.updateByPk(info.getId(), group);
    }

    /**
     * 更新企业资料信息，如果不存在则保存
     * @param info 更新参数对象
     * @throws Exception
     */
    private void updateGroupInfomation(EditCompanyInfo info) throws Exception {
        // 获取公司资料
        GroupInformation information = groupInformationService.selectByGroupId(info.getId());
        if (information != null){
            // 更新公司资料
            information.setBusinessScope(info.getBusinessScope());
            information.setAddress(info.getAddress());
            information.setCapital(info.getCapital());
            information.setLegalPerson(info.getLegalPerson());
            information.setPhone(info.getPhone());
            information.setTyp(info.getType());
            information.setValidity(info.getValidity());
        }else {
            AddCompanyInfo addCompanyInfo = new AddCompanyInfo();
            BeanUtils.copyProperties(info, addCompanyInfo);

            // 保存企业信息表
            saveCompanyInformation(info.getId(), addCompanyInfo);
        }
    }

    /**
     * 保存企业商户资料信息
     * @param companyId 企业商户ID
     * @param info  新增参数对象
     * @throws Exception
     */
    private void saveCompanyInformation(Long companyId, AddCompanyInfo info) throws Exception{
        GroupInformation infor = new GroupInformation();
        infor.setTyp(info.getType());
        infor.setAddress(info.getAddress());
        infor.setBusinessScope(info.getBusinessScope());
        infor.setCapital(info.getCapital());
        infor.setGroupId(companyId);
        infor.setLegalPerson(info.getLegalPerson());
        infor.setPhone(info.getPhone());
        infor.setValidity(info.getValidity());
        infor.setState(0);
        infor.setCreateTime(new Date());

        groupInformationService.insert(infor);
    }

    /**
     * 组装并保存企业信息
     * @param info 企业信息对象
     * @return Long
     * @throws Exception
     */
    private Long saveCompany(AddCompanyInfo info) throws Exception{
        Group group = new Group();
        group.setGroupId(0L);
        group.setOrganizationCode(info.getOrganizationCode());
        group.setName(info.getName());
        group.setPid(0L);
        group.setCode("");
        group.setCodePath("");
        group.setState(0);
        group.setTyp(1);
        group.setGrade(0);
        group.setCreateTime(new Date());

        return groupService.insert(group);
    }

    /**
     * 组图片集合entity转为info集合
     * @param entitys 组图片集合entity
     * @return List<GroupImgInfo>
     */
    private List<GroupImgInfo> groupImgsInfoToInfos(List<GroupImg> entitys){
        List<GroupImgInfo> infos = new ArrayList<>();
        if (entitys != null && entitys.size() > 0){
            for (GroupImg entity : entitys){
                if (entity != null){
                    infos.add(groupImgInfoToInfo(entity));
                }
            }
        }

        return infos;
    }

    /**
     * 组图片entity转为info
     * @param entity 组图片对象
     * @return GroupImgInfo
     */
    private GroupImgInfo groupImgInfoToInfo(GroupImg entity){
        GroupImgInfo info = new GroupImgInfo();
        BeanUtils.copyProperties(entity, info);

        return info;
    }

    /**
     * 根据主键ID查询组信息
     * @param id    主键ID
     * @return Group
     * @throws Exception
     */
    private Group selGroupById(Long id) throws Exception{
        Group group = groupService.selectByPk(id);
        if (group == null){
            throw new BusinessException("未查询到该公司信息！");
        }

        return group;
    }
}
