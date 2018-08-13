package com.lmt.mbsp.user.front.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.support.bean.EntityToModelUtils;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.dto.*;
import com.lmt.mbsp.user.front.biz.UserBiz;
import com.lmt.mbsp.user.core.CommonUtils;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.account.AccountName;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.group.GroupUser;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.entity.user.UserAccount;
import com.lmt.mbsp.user.service.*;
import com.lmt.mbsp.user.vo.account.AccountListInfo;
import com.lmt.mbsp.user.vo.account.AccountNameInfo;
import com.lmt.mbsp.user.vo.enumutil.StatusEnum;
import com.lmt.mbsp.user.vo.operator.AddOperatorInfo;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.user.EditUserInfo;
import com.lmt.mbsp.user.vo.user.UserDetailInfo;
import com.lmt.mbsp.user.vo.user.UserInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述: 用户聚合层
 * 作者: Tangsm
 * 创建时间: 2018-06-27 17:14
 */
@Service
public class UserBizImpl implements UserBiz {
    private GroupUserService groupUserService;
    private UserAccountService userAccountService;
    private UserService userService;
    private AccountService accountService;
    private AccountRoleService accountRoleService;
    private AccountNameService accountNameService;
    private RoleService roleService;

    @Autowired
    public UserBizImpl(GroupUserService groupUserService,
                                    UserAccountService userAccountService,
                                    UserService userService,
                                    AccountService accountService,
                                    AccountRoleService accountRoleService,
                                    AccountNameService accountNameService,
                                    RoleService roleService){
        this.groupUserService = groupUserService;
        this.userAccountService = userAccountService;
        this.userService = userService;
        this.accountService = accountService;
        this.accountRoleService = accountRoleService;
        this.accountNameService = accountNameService;
        this.roleService = roleService;
    }

    @Override
    public UserDetailInfo userDetail(Long userId) throws Exception {
        UserDetailInfo userDetailInfo = new UserDetailInfo();

        // 1.查询用户信息
        User user = selUserById4Ex(userId);

        // 2.查询账号信息
        Account account = selAccountByUserId(user.getId());

        // 3.将账号entity转为Info的同时获取对应的子账号、角色
        AccountListInfo accountInfo = EntityToModelUtils.entityToInfo(account, new AccountListInfo());
        if (accountInfo != null){
            // 查询账号对应的角色
            List<Role> roles = selRolesByAccountId(accountInfo.getId());
            accountInfo.setRoleInfos(EntityToModelUtils.entitysToInfos(roles, RoleInfo.class));

            // 查询所有账号名
            List<AccountName> accountNames = accountNameService.selectByAccountId(accountInfo.getId());
            accountInfo.setAccounNameInfos(EntityToModelUtils.entitysToInfos(accountNames, AccountNameInfo.class));
        }

        userDetailInfo.setAccount(accountInfo);
        userDetailInfo.setUser(EntityToModelUtils.entityToInfo(user, new UserInfo()));

        return userDetailInfo;
    }

    @Override
    public UserInfo toEdit(Long userId) throws Exception{
        if (userId != null && userId > 0L){
            // 1.查询用户信息
            User user = selUserById4Ex(userId);

            UserInfo info = new UserInfo();
            info.setAge(user.getAge());
            info.setDomicile(user.getDomicile());
            info.setEmail(user.getEmail());
            info.setIdCard(user.getIdCard());
            info.setName(user.getName());
            info.setPhone(user.getPhone());
            info.setQq(user.getQq());
            info.setSex(user.getSex());

            return info;
        }else {
            return null;
        }
    }

    @Override
    public void edit(EditUserInfo info) throws Exception {
        // 更新用户表信息
        User user = selUserById4Ex(info.getId());

        // 更新用户信息
        user.setName(info.getName());
        user.setQq(info.getQq());
        user.setEmail(info.getEmail());
        user.setPhone(info.getPhone());
        user.setAge(info.getAge());
        user.setSex(info.getSex());
        user.setDomicile(info.getDomicile());
        user.setIdCard(info.getIdCard());
        if (!user.getIsSupplement()){
            user.setIsSupplement(true);
        }

        userService.updateByPk(user.getId(), user);
    }

    @Override
    public Long addOperator(AddOperatorInfo info) throws Exception {
        AccountName name = accountNameService.selectByAccountName(info.getMobile());
        if(name == null){
            throw new BusinessException("对不起，该账号不存在！");
        }

        UserAccount userAccount = userAccountService.selectByAccountId(name.getAccountId());
        if (userAccount != null){
            User user = selUserById4Ex(userAccount.getUserId());
            if (user != null){
                if (user.getName().equals("")){
                    throw new BusinessException("对不起，该用户暂未补全资料，不能进行该操作！");
                }
                if (!user.getName().equals(info.getName())){
                    throw new BusinessException("对不起，姓名与账号不匹配，不能进行该操作！");
                }

                // 保存组用户关联表
                saveGroupUser(info.getGroupId(), userAccount.getUserId());

                // 保存账号角色关联表

                saveAccountRoles(name.getAccountId(), info.getRoleIds());

                return userAccount.getUserId();
            }
        }

        throw new BusinessException("对不起，未查询到该用户信息！");
    }

    @Override
    public void disableUser(Long id) throws Exception {
        User user = selUserById4Ex(id);
        if (!user.getState().equals(StatusEnum.DISABLED.getStatus())){
            user.setState(StatusEnum.DISABLED.getStatus());

            userService.updateByPk(id, user);
        }
    }

    @Override
    public void unDisableUser(Long id) throws Exception {
        User user = selUserById4Ex(id);
        if (!user.getState().equals(StatusEnum.UNDISABLED.getStatus())){
            user.setState(StatusEnum.UNDISABLED.getStatus());

            userService.updateByPk(id, user);
        }
    }

    @Override
    public List<OperatorListInfo> operatorList(Long groupId) throws Exception {
        return assemblyOperatorInfo(groupId);
    }

    /**
     * 保存账号角色关联表
     *
     * @param accId   账号ID
     * @param rolesId 角色ID集合
     */
    private void saveAccountRoles(Long accId, String rolesId) throws Exception {
        // 保存账号角色关联表
        if (!StringUtils.isNullOrEmpty(rolesId)) {
            String[] ids = rolesId.split(",");
            for (String id : ids) {
                AccountRole role = new AccountRole();
                role.setAccountId(accId);
                role.setRoleId(Long.valueOf(id));

                accountRoleService.insert(role);
            }
        }
    }

    /**
     * 保存组用户关联表
     *
     * @param groupId 组ID
     * @param userId  用户ID
     */
    private void saveGroupUser(Long groupId, Long userId) throws Exception {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(userId);
        groupUser.setCreateTime(new Date());

        groupUserService.insert(groupUser);
    }

    /**
     * 是否要查询角色信息
     * @param groupId   公司ID
     * @return List<OperatorListInfo>
     */
    private List<OperatorListInfo> assemblyOperatorInfo(Long groupId) throws Exception{
        List<OperatorListInfo> infoList = new ArrayList<>();

        // 1.查询组用户关联表获取组下所有用户信息
        List<GroupUser> groupUsers = selGroupUsersByGroupId(groupId);
        if (groupUsers != null && groupUsers.size() > 0) {
            // 将用户ID组装至集合中
            List<Long> userIds = BeanUtils.getPropertyValues2List(groupUsers, "userId");
            if (userIds.size() > 0) {
                // 2.根据用户ID集合查询用户账号关联表信息
                List<UserAccount> userAccounts = selByUserIds(userIds);
                if (userAccounts != null && userAccounts.size() > 0) {
                    // 将账号ID组装至集合中
                    List<Long> accIds = BeanUtils.getPropertyValues2List(userAccounts, "accountId");

                    List<Account> accounts = selAccsByAccountIds(accIds);

                    // 5.将用户和账号信息进行匹配并返回
                    infoList = groupUserAndAcc(accounts, userAccounts, userIds, groupId);

                    // 循环infoList，将角色信息加入
                    Map<Long, List<Role>> roleMap = groupAccAndRole(accounts, accIds);
                    if (infoList.size() > 0 && roleMap.size() > 0){
                        for (OperatorListInfo info : infoList){
                            List<Role> roles = roleMap.get(info.getAccountId());

                            info.setRoles(EntityToModelUtils.entitysToInfos(roles, RoleInfo.class));
                        }
                    }
                }
            }
        }

        return infoList;
    }

    /**
     * 将账号及对应角色进行组装并返回
     * @param accIds    账号ID集合(为了查询账号角色表以获取角色信息)
     * @return Map<Long, List<Role>> key为账号主键ID，value为账号对应的角色集合
     * @throws Exception
     */
    private Map<Long, List<Role>> groupAccAndRole(List<Account> accounts, List<Long> accIds)throws Exception{
        Map<Long, List<Role>> infoList = new HashMap<>();

        List<AccountRole> accountRoles = selByAccRolesAcctIds(accIds);

        // TODO CommonUtils改为BeanUtils
        // 3.根据账号ID集合查询对应的用户账号信息（用户账号关联表主键ID为key）
        Map<String, Account> accountMap = CommonUtils.assembly2Map(accounts, accountRoles, "id", "accountId");

        // 4.根据账号ID集合查询角色信息（账号角色关联表主键ID为key）
        List<Long> roleIds = BeanUtils.getPropertyValues2List(accountRoles, "roleId");
        Map<String, Role> roleMap = searchRoles(roleIds, accountRoles);

        if (roleMap != null){
            // 5.将角色添加至对应的账号信息中
            for (String id : roleMap.keySet()) {
                if (id != null) {
                    if (accountMap != null && accountMap.get(id) != null) {
                        Account account = accountMap.get(id);
                        List<Role> roles = infoList.get(account.getId());
                        if (roles == null){
                            roles = new ArrayList<>();
                        }

                        if (roleMap.get(id) != null) {
                            Role role = roleMap.get(id);

                            roles.add(role);
                        }

                        infoList.put(account.getId(), roles);
                    }
                }
            }
        }

        return infoList;
    }

    /**
     * 将用户及对应账号进行组装并返回
     * @param accounts  账号信息，为了与用户进行关联
     * @param userAccounts  用户账号关联表集合数据
     * @param userIds   用户ID集合(为了查询用户数据)
     * @param groupId   公司ID
     * @return List<OperatorListInfo>
     * @throws Exception
     */
    private List<OperatorListInfo> groupUserAndAcc(List<Account> accounts, List<UserAccount> userAccounts, List<Long> userIds, Long groupId)throws Exception{
        List<OperatorListInfo> infoList = new ArrayList<>();

        // 3.根据账号ID集合查询对应的用户账号信息（用户账号关联表主键ID为key）
        Map<String, Account> accountMap = searchAccounts(accounts, userAccounts);

        // 4.根据用户ID集合查询用户信息（用户账号关联表主键ID为key）
        Map<String, User> userMap = searchUsers(userIds, userAccounts);

        if(userMap != null){
            // 5.将用户和账号信息进行匹配并返回
            for (String id : userMap.keySet()) {
                if (id != null) {
                    OperatorListInfo userInfo = new OperatorListInfo();
                    userInfo.setGroupId(groupId);
                    if (accountMap != null && accountMap.get(id) != null) {
                        Account acc = accountMap.get(id);
                        userInfo.setAccountId(acc.getId());
                        userInfo.setState(acc.getState());
                        userInfo.setAccountName(acc.getAccountName());
                        userInfo.setIsLock(acc.getIsLock());
                        userInfo.setLockTimes(acc.getLockTimes());
                        userInfo.setMaster(acc.getMaster());
                    }
                    if (userMap.get(id) != null) {
                        User user = userMap.get(id);
                        userInfo.setName(user.getName());
                        userInfo.setUserId(user.getId());
                        userInfo.setIsSupplement(user.getIsSupplement());
                    }

                    infoList.add(userInfo);
                }
            }
        }

        return infoList;
    }

    /**
     * 根据角色ID集合查询角色信息，并按角色关联表中的主键ID进行分组
     *
     * @param roleIds      角色ID集合
     * @param accountRoles 角色关联表集合
     * @return Map<Long   ,       Role>(key为角色关联表主键ID，value为角色对象信息)
     */
    private Map<String, Role> searchRoles(List<Long> roleIds, List<AccountRole> accountRoles) throws Exception {
        List<Role> roles = selByIds(roleIds);
        if (roles != null && roles.size() > 0){
            // TODO CommonUtils改为BeanUtils
            return CommonUtils.assembly2Map(roles, accountRoles, "id", "roleId");
        }else {
            return null;
        }
    }

    /**
     * 根据用户ID集合查询用户信息，并按用户账号关联表中的主键ID进行分组
     *
     * @param userIds      用户ID集合
     * @param userAccounts 用户关联表集合
     * @return Map<Long   ,       User>(key为用户关联表主键ID，value为用户对象信息)
     */
    private Map<String, User> searchUsers(List<Long> userIds, List<UserAccount> userAccounts) throws Exception {
        if (userIds != null && userIds.size() > 0){
            // 根据用户ID集合查询用户信息
            UserQuery query = new UserQuery();
            query.setIds(userIds);

            List<User> users = userService.select(query);

            return CommonUtils.assembly2Map(users, userAccounts, "id", "userId");
        }else {
            return null;
        }
    }

    private Map<String, Account> searchAccounts(List<Account> accounts, List<UserAccount> userAccounts){
        // 根据两个对象中相同属性进行匹配，然后将中间表的主键ID作为key，主表对象作为value进行组装并返回
        return CommonUtils.assembly2Map(accounts, userAccounts, "id", "accountId");
    }

    private List<Account> selAccsByAccountIds(List<Long> accIds){
        AccountQuery accountQuery = new AccountQuery();
        accountQuery.setIds(accIds);

        return accountService.select(accountQuery);
    }

    private List<AccountRole> selByAccRolesAcctIds(List<Long> accIds){
        AccountRoleQuery roleQuery = new AccountRoleQuery();
        roleQuery.setAccountIds(accIds);

        return accountRoleService.select(roleQuery);
    }

    private List<GroupUser> selGroupUsersByGroupId(Long groupId){
        GroupUserQuery groupUserQuery = new GroupUserQuery();
        groupUserQuery.setGroupId(groupId);

        return groupUserService.select(groupUserQuery);
    }

   private List<UserAccount> selByUserIds(List<Long> userIds){
        UserAccountQuery userAccountQuery = new UserAccountQuery();
        userAccountQuery.setUserIds(userIds);

        return userAccountService.select(userAccountQuery);
    }

    /**
     * 根据主键ID查询用户信息，并抛出不存在异常
     * @param userId 用户ID
     * @return User
     */
    private User selUserById4Ex(Long userId){
        User user = selUserById(userId);
        if (user == null) {
            throw new BusinessException("对不起，未查询到该用户！");
        }

        return user;
    }

    /**
     * 根据主键ID查询用户信息
     * @param userId 用户ID
     * @return User
     */
    private User selUserById(Long userId){
        return userService.selectByPk(userId);
    }

    /**
     * 根据用户ID查询用户账号关联表
     * @param userId 用户ID
     * @return Account
     */
    private Account selAccountByUserId(Long userId){
        UserAccount account = userAccountService.selectByUserId(userId);
        if (account != null){
            return accountService.selectByPk(account.getAccountId());
        }

        return null;
    }

    /**
     * 根据账号ID查询角色信息
     * @param accountId 账号ID
     * @return List<Role>
     */
    private List<Role> selRolesByAccountId(Long accountId) {
        List<AccountRole> accountRoles = accountRoleService.selectByAccountId(accountId);
        List<Long> roleIds = BeanUtils.getPropertyValues2List(accountRoles, "roleId");

        return selByIds(roleIds);
    }

    private List<Role> selByIds(List<Long> ids){
        if (ids != null && ids.size() > 0){
            RoleQuery roleQuery = new RoleQuery();
            roleQuery.setIds(ids);

            return roleService.select(roleQuery);
        }else {
            return null;
        }
    }
}
