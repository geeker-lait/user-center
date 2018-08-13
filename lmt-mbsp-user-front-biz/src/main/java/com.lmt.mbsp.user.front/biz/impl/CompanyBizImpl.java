package com.lmt.mbsp.user.front.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.support.bean.EntityToModelUtils;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.entity.group.*;
import com.lmt.mbsp.user.front.biz.CompanyBiz;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.service.*;
import com.lmt.mbsp.user.vo.company.*;
import com.lmt.mbsp.user.vo.enumutil.StatusEnum;
import com.lmt.mbsp.user.vo.operator.IndexInfo;
import com.lmt.mbsp.user.vo.user.UserInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
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
public class CompanyBizImpl implements CompanyBiz {
    private CompanyImgService companyImgService;
    private AccountRoleService accountRoleService;
    private UserService userService;
    private GroupService groupService;
    private GroupRoleService groupRoleService;
    private RoleService roleService;
    private CompanyService companyService;

    @Autowired
    public CompanyBizImpl(GroupService groupService,
                                 GroupRoleService groupRoleService,
                                 RoleService roleService,
                                 CompanyImgService companyImgService,
                                 AccountRoleService accountRoleService,
                                 UserService userService,
                                 CompanyService companyService){
        this.groupService = groupService;
        this.groupRoleService = groupRoleService;
        this.roleService = roleService;
        this.companyImgService = companyImgService;
        this.accountRoleService = accountRoleService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @Override
    public Boolean checkCompanyName(String name) throws Exception{
        return selByName(name) != null;
    }

    @Override
    public Long add(AddCompanyInfo info) throws Exception{
        selByName4Ex(info.getName());

        // 保存组信息表
        Group group = new Group();
        group.setType(StatusEnum.COMPANY_TYPE.getStatus());

        Long groupId = groupService.insert(group);

        // 保存企业表
        Long companyId = saveCompany(info, groupId);

        // TODO
        // 保存证书
        // 保存授权书

        return companyId;
    }

    @Override
    public ToEditCompanyInfo toEdit(Long id) throws Exception{
        // 获取公司信息
        Company company = selById4Ex(id);
        if (company.getAuditState().equals(0)){
            throw new BusinessException("对不起，待审核状态下不能进行修改企业信息！");
        }

        ToEditCompanyInfo info = new ToEditCompanyInfo();
        BeanUtils.copyProperties(company, info);

        // 获取公司图片
        info.setImgs(searchCompanyImgToInfo(id));

        return info;
    }

    @Override
    public void edit(EditCompanyInfo info) throws Exception{
        Company company = selByName(info.getName());
        if (company != null && !info.getId().equals(company.getId())){
            throw new BusinessException("该公司名称已经存在！");
        }
        if (company.getAuditState().equals(StatusEnum.WAIT_AUDIT.getStatus())){
            throw new BusinessException("对不起，待审核状态下不能进行修改企业信息！");
        }

        // 更新公司信息
        updateCompany(info);

        // 删除证书
        companyImgService.deleteByCompanyId(info.getId());

        // TODO
        // 重新保存证书
    }

    @Override
    public IndexInfo index(Long accountId, Long userId, Long companyId) throws Exception{
        IndexInfo info = new IndexInfo();

        // 获取公司信息
        Company company = selById4Ex(companyId);
        if (company != null){
            info.setName(company.getName());
            info.setState(company.getState());
            info.setAuditState(company.getAuditState());
            info.setCreateTime(company.getCreateTime());
            info.setOrganizationCode(company.getOrganizationCode());
            info.setAddress(company.getAddress());
            info.setBusinessScope(company.getBusinessScope());
            info.setCapital(company.getCapital());
            info.setValidity(company.getValidity());
            info.setType(company.getType());
            info.setPhone(company.getPhone());
            info.setLegalPerson(company.getLegalPerson());
        }

        info.setUserInfo(selByIdToInfo(userId)); // 获取用户信息
        info.setImgs(searchCompanyImgToInfo(companyId));    // 获取公司图片
        info.setUserRole(searchAccountRoleAndToInfo(accountId)); // 获取账号角色

        return info;
    }

    /**
     * 更加用户ID查询用户信息并转为info对象返回
     * @param userId 用户ID
     * @return UserInfo
     */
    private UserInfo selByIdToInfo(Long userId){
        User user = userService.selectByPk(userId);
        if(user != null){
            return EntityToModelUtils.entityToInfo(user, new UserInfo());
        }

        return null;
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
     * 更新企业资料信息，如果不存在则保存
     * @param info 更新参数对象
     * @throws Exception
     */
    private void updateCompany(EditCompanyInfo info) throws Exception {
        // 获取公司资料
        Company company = companyService.selectByPk(info.getId());
        if (company != null){
            // 更新公司资料
            company.setName(info.getName());
            company.setOrganizationCode(info.getOrganizationCode());
            company.setBusinessScope(info.getBusinessScope());
            company.setAddress(info.getAddress());
            company.setCapital(info.getCapital());
            company.setLegalPerson(info.getLegalPerson());
            company.setPhone(info.getPhone());
            company.setType(info.getType());
            company.setValidity(info.getValidity());
            company.setAuditState(StatusEnum.WAIT_AUDIT.getStatus());

            companyService.updateByPk(company.getId(), company);
        }
    }

    /**
     * 组装并保存企业信息
     * @param info 企业信息对象
     * @param groupId 组主键ID
     * @return Long
     * @throws Exception
     */
    private Long saveCompany(AddCompanyInfo info, Long groupId) throws Exception{
        Company company = new Company();
        company.setGroupId(groupId);
        company.setName(info.getName());
        company.setOrganizationCode(info.getOrganizationCode());
        company.setType(info.getType());
        company.setAddress(info.getAddress());
        company.setBusinessScope(info.getBusinessScope());
        company.setCapital(info.getCapital());
        company.setLegalPerson(info.getLegalPerson());
        company.setPhone(info.getPhone());
        company.setValidity(info.getValidity());
        company.setState(StatusEnum.UNDISABLED.getStatus());
        company.setType(info.getType());
        company.setCreateTime(new Date());

        return companyService.insert(company);
    }

    /**
     * 根据公司ID查询该公司下的图片集合，并转为info对象返回
     * @param companyId 公司ID
     * @return List<CompanyImgInfo>
     */
    private List<CompanyImgInfo> searchCompanyImgToInfo(Long companyId) throws Exception{
        // 获取公司图片
        List<CompanyImg> imgs = companyImgService.selectByCompanyId(companyId);

        return EntityToModelUtils.entitysToInfos(imgs, CompanyImgInfo.class);
    }

    /**
     * 根据主键ID查询组信息
     * @param id    主键ID
     * @return Company
     * @throws Exception
     */
    private Company selById4Ex(Long id) throws Exception{
        Company company = companyService.selectByPk(id);
        if (company == null){
            throw new BusinessException("未查询到该公司信息！");
        }

        return company;
    }

    /**
     * 根据公司名查询公司是否已经存在，存在抛出异常
     * @param name 公司名称
     * @return Company
     */
    private Company selByName4Ex(String name){
        Company company = selByName(name);
        if (company != null){
            throw new BusinessException("该公司名称已经存在！");
        }

        return company;
    }

    /**
     * 根据公司名查询公司是否已经存在，存在抛出异常
     * @param name 公司名称
     * @return Company
     */
    private Company selByName(String name){
        return companyService.selectByName(name);
    }
}
