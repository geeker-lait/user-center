package com.lmt.mbsp.user.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.support.bean.EntityToModelUtils;
import com.lmt.framework.support.entity.PagerResult;
import com.lmt.framework.utils.RandomUtil;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.TreeCodeUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.biz.UserBiz;
import com.lmt.mbsp.user.core.CommonUtils;
import com.lmt.mbsp.user.dto.*;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.account.AccountName;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.entity.group.GroupUser;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.entity.user.UserAccount;
import com.lmt.mbsp.user.entity.user.UserPosition;
import com.lmt.mbsp.user.service.*;
import com.lmt.mbsp.user.vo.account.AccountInfo;
import com.lmt.mbsp.user.vo.account.AccountListInfo;
import com.lmt.mbsp.user.vo.account.AccountNameInfo;
import com.lmt.mbsp.user.vo.admin.AddAdminInfo;
import com.lmt.mbsp.user.vo.admin.EditAdminInfo;
import com.lmt.mbsp.user.vo.admin.ToEditAdminInfo;
import com.lmt.mbsp.user.vo.operator.AddOperatorInfo;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.operator.ToEditOperatorInfo;
import com.lmt.mbsp.user.vo.person.EditUserInfo;
import com.lmt.mbsp.user.vo.person.UserDetailInfo;
import com.lmt.mbsp.user.vo.person.UserInfo;
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
    private GroupService groupService;
    private GroupUserService groupUserService;
    private UserAccountService userAccountService;
    private UserService userService;
    private UserPositionService userPositionService;
    private AccountService accountService;
    private AccountRoleService accountRoleService;
    private AccountNameService accountNameService;
    private RoleService roleService;

    @Autowired
    public void UserBizImpleService(GroupService groupService,
                                    GroupUserService groupUserService,
                                    UserAccountService userAccountService,
                                    UserService userService,
                                    UserPositionService userPositionService,
                                    AccountService accountService,
                                    AccountRoleService accountRoleService,
                                    AccountNameService accountNameService,
                                    RoleService roleService){
        this.groupService = groupService;
        this.groupUserService = groupUserService;
        this.userAccountService = userAccountService;
        this.userService = userService;
        this.userPositionService = userPositionService;
        this.accountService = accountService;
        this.accountRoleService = accountRoleService;
        this.accountNameService = accountNameService;
        this.roleService = roleService;
    }

    @Override
    public ToEditAdminInfo toSupplement(Long userId) throws Exception{
        if (userId != null && userId > 0L){
            ToEditAdminInfo info = new ToEditAdminInfo();

            // 1.查询用户信息
            User user = selUserById4Ex(userId);
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
    public void supplement(EditUserInfo info) throws Exception {
        // 前台注册未保存用户表需新增，后台及用户中心注册的则更新用户表信息
        if (info.getId() != null && info.getId() > 0L){
            // 更新用户表信息
            editUserInfo(info);
        }else{
            Long userId = saveUser(info, 0, true);

            // 保存用户账号关联表信息（用户中心无需保存用户职位关联表）
            saveUserAccount(userId, info.getAccountId());
        }
    }

    @Override
    public Long addOperator(AddOperatorInfo info) throws Exception {
        AccountName name = accountNameService.selectByAccountName(info.getAccountName());
        if(name != null){
            throw new BusinessException("对不起，该账号已存在！");
        }

        // 保存账号
        Long accId = saveAccount(info.getAccountName(), info.getPassword(), 0, 2);

        // 保存账号名称表
        saveAccName(accId, info.getAccountName());

        // 保存用户表信息
        Long userId = saveUser(info, 0, false);

        // 保存用户账号关联表
        saveUserAccount(userId, accId);

        // 保存组用户关联表
        saveGroupUser(info.getGroupId(), userId);

        // 保存账号角色关联表
        saveAccountRoles(accId, info.getRoleIds());

        return userId;
    }

    @Override
    public PagerResult<UserInfo> sysUserList(UserQuery info) throws Exception {
        if (!StringUtils.isNullOrEmpty(info.getDeptCode())) {
            // 先查询部门下所有用户ID
            GroupUserQuery groupUserQuery = new GroupUserQuery();
            groupUserQuery.setGroupId(info.getGroupId());
            groupUserQuery.setCodePath("|" + info.getDeptCode() + "|");

            List<GroupUser> groupUsers = groupUserService.select(groupUserQuery);
            List<Long> userIds = BeanUtils.getPropertyValues2List(groupUsers, "userId");

            // 根据查询条件从指定userId集合中过滤用户信息
            info.setIds(userIds);
        }

        List<Integer> typs = new ArrayList<>();
        typs.add(1);
        typs.add(2);

        info.setTyps(typs);

        PagerResult<UserInfo> result = new PagerResult<>();

        PagerResult<User> userPagerResult = userService.selectPager(info);
        if (userPagerResult != null && userPagerResult.getData() != null){
            result.setData(EntityToModelUtils.entitysToInfos(userPagerResult.getData(), UserInfo.class));
            result.setTotal(userPagerResult.getTotal());
        }

        return result;
    }

    @Override
    public UserDetailInfo userDetail(Long userId, Integer type) throws Exception {
        UserDetailInfo userDetailInfo = new UserDetailInfo();

        // 1.查询用户信息
        User user = selUserById4Ex(userId);
        validateUserType(type, user.getTyp());

        // 2.查询账号信息
        List<Account> accounts = selAccountByUserId(user.getId());

        // 3.查询用户所属公司及部门信息
        Map<Long, Group> groupMap = selGroupByUserId(user.getId());

        // 4.将账号entity转为Info的同时获取对应的子账号、角色
        List<AccountListInfo> accountInfos = EntityToModelUtils.entitysToInfos(accounts, AccountListInfo.class);
        for (AccountListInfo info : accountInfos){
            if (info != null){
                // 查询账号对应的角色
                List<Role> roles = selRolesByAccountId(info.getId());
                info.setRoleInfos(EntityToModelUtils.entitysToInfos(roles, RoleInfo.class));

                // 查询所有账号名
                List<AccountName> accountNames = accountNameService.selectByAccountId(info.getId());
                info.setAccounNameInfos(EntityToModelUtils.entitysToInfos(accountNames, AccountNameInfo.class));
            }
        }

        userDetailInfo.setAccounts(accountInfos);
        userDetailInfo.setUser(EntityToModelUtils.entityToInfo(user, new UserInfo()));
        userDetailInfo.setGroups(groupMap);

        return userDetailInfo;
    }

    @Override
    public Long addSysUser(AddAdminInfo info) throws Exception {
        // 保存账号
        String pwd = RandomUtil.randomChar();

        Long accId = saveAccount(info.getAccountName(), pwd, info.getManagerType(), 1);

        // 保存账号名称表
        saveAccName(accId, info.getAccountName());

        // TODO 发送密码至手机

        // 保存用户表信息
        Long userId = saveUser(info, 1, false);

        // 保存用户职位表信息
        assembleUserPosition(userId, info.getCategoryId(), info.getPosition());

        // 保存用户账号关联表
        saveUserAccount(userId, accId);

        // 保存组用户关联表（与公司关联）
        saveGroupUser(info.getGroupId(), userId);

        // 保存组用户关联表（与部门关联）
        saveGroupUsers(userId, info.getDeptIds());

        return userId;
    }

    @Override
    public void add2Group(Long groupId, Long userId) throws Exception {
        User user = selUserById4Ex(userId);
        user.setTyp(2); // 用户类型（0非内部用户 1内部用户 2内部用户兼外部用户）

        userService.updateByPk(userId, user);

        saveGroupUser(groupId, userId);
    }

    @Override
    public ToEditAdminInfo toEditSysUser(Long userId) throws Exception {
        ToEditAdminInfo info = new ToEditAdminInfo();

        // 1.查询用户信息
        User user = selUserById4Ex(userId);
        validateUserType(1, user.getTyp());

        info.setAge(user.getAge());
        info.setDomicile(user.getDomicile());
        info.setEmail(user.getEmail());
        info.setIdCard(user.getIdCard());
        info.setName(user.getName());
        info.setPhone(user.getPhone());
        info.setQq(user.getQq());
        info.setSex(user.getSex());

        // 2.查询账号已赋部门信息(所有部门信息通过重新调用部门查询接口获取)
        List<GroupUser> groupUsers = groupUserService.selectByUserId(userId);
        info.setDeptIds(BeanUtils.getPropertyValues2List(groupUsers, "id"));

        return info;
    }

    @Override
    public void editSysUser(EditAdminInfo info) throws Exception {
        User user = selUserById4Ex(info.getId());
        validateUserType(1, user.getTyp());

        // 更新用户信息
        updateSysUserInfo(user, info);

        // 更新职位信息
        UserPosition position = userPositionService.selectByUserId(user.getId());
        if (position != null) {
            position.setCategoryId(info.getCategoryId());
            position.setPosition(info.getPosition());

            userPositionService.updateByPk(position.getId(), position);
        }

        // 删除组用户关联表（与部门、公司关联）
        groupUserService.deleteByUserId(info.getId());

        // 重新保存组用户关联表（与公司关联）
        saveGroupUser(info.getGroupId(), user.getId());

        // 重新保存组用户关联表（与部门关联）
        saveGroupUsers(user.getId(), info.getDeptIds());
    }

    @Override
    public PagerResult<UserInfo> personUserList(UserQuery info) throws Exception {
        List<Integer> tpys = new ArrayList<>();
        tpys.add(0);
        tpys.add(2);

        info.setTyps(tpys);

        PagerResult<UserInfo> result = new PagerResult<>();

        PagerResult<User> userPagerResult = userService.selectPager(info);
        if (userPagerResult != null && userPagerResult.getData() != null){
            result.setData(EntityToModelUtils.entitysToInfos(userPagerResult.getData(), UserInfo.class));
            result.setTotal(userPagerResult.getTotal());
        }

        return result;
    }

    @Override
    public void disableUser(Long id) throws Exception {
        User user = selUserById4Ex(id);
        user.setState(1);

        userService.updateByPk(user.getId(), user);
    }

    @Override
    public void unDisableUser(Long id) throws Exception {
        User user = selUserById4Ex(id);
        user.setState(0);

        userService.updateByPk(user.getId(), user);
    }

    @Override
    public List<OperatorListInfo> operatorList(Long groupId) throws Exception {
        return assemblyOperatorInfo(groupId, 0);
    }

    @Override
    public List<OperatorListInfo> userAccountList(Long groupId) throws Exception{
        return assemblyOperatorInfo(groupId, 1);
    }

    @Override
    public ToEditOperatorInfo toEditOperator(Long userId, Long accountId)throws Exception{
        Account account = selAccountById4Ex(accountId);
        User user = selUserById4Ex(userId);

        List<AccountRole> accountRoles = accountRoleService.selectByAccountId(accountId);
        List<Long> ids = BeanUtils.getPropertyValues2List(accountRoles, "roleId");

        ToEditOperatorInfo info = new ToEditOperatorInfo();
        info.setAccountName(account.getAccountName());
        info.setAccountId(account.getId());
        info.setName(user.getName());
        info.setGivenRoleIds(ids);

        return info;
    }

    /**
     * 更新用户信息
     * @param info    页面传入用户信息参数
     */
    private void editUserInfo(EditUserInfo info){
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

    /**
     * 组装保存用户需要的信息并返回
     *
     * @param info         用户信息
     * @param typ          用户类型（0非内部用户 1内部用户 2内部用户兼外部用户）
     * @param isSupplement 是否已补全资料
     * @return Long
     */
    private Long saveUser(Object info, Integer typ, boolean isSupplement) throws Exception {
        User user = new User();
        BeanUtils.copyProperties(info, user);
        user.setState(0);
        user.setTyp(typ);
        user.setIsSupplement(isSupplement);
        user.setCreateTime(new Date());

        return userService.insert(user);
    }

    /**
     * 组装用户账号关联对象并返回
     *
     * @param userId    用户ID
     * @param accountId 账号ID
     */
    private void saveUserAccount(Long userId, Long accountId) throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountId(accountId);
        userAccount.setUserId(userId);
        userAccount.setCreateTime(new Date());

        userAccountService.insert(userAccount);
    }

    /**
     * 组装保存账号需要的信息并返回
     *
     * @param username     用户名
     * @param pwd           密码
     * @param managerType  管理员类别(0普通管理员 1超级管理员)
     * @param registerType 注册类型（0前台注册 1后台创建 2用户中心创建）
     * @return Long
     */
    private Long saveAccount(String username, String pwd, Integer managerType, Integer registerType) throws Exception {
        Account acc = new Account();
        acc.setAccountName(username);
        acc.setPassword(pwd);
        acc.setCreateTime(new Date());
        acc.setManagerType(managerType);
        acc.setMaster(0);
        acc.setState(0);
        acc.setRegisterType(registerType);

        return accountService.insert(acc);
    }

    /**
     * 组装用户地址对象并返回
     *
     * @param userId     用户ID
     * @param categoryId 职位ID
     * @param position   职位名称
     */
    private void assembleUserPosition(Long userId, Long categoryId, String position) throws Exception {
        UserPosition userPosition = new UserPosition();
        userPosition.setCategoryId(categoryId);
        userPosition.setPosition(position);
        userPosition.setUserId(userId);

        userPositionService.insert(userPosition);
    }

    /**
     * 保存账号名称对象信息并返回
     * @param accountId 账号ID
     * @param username  用户名
     */
    private void saveAccName(Long accountId, String username) throws Exception {
        AccountName accPwd = new AccountName();
        accPwd.setAccountId(accountId);
        accPwd.setAccountName(username);

        accountNameService.insert(accPwd);
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
     * @param userId   用户ID
     * @param groupsId 组ID集合
     */
    private void saveGroupUsers(Long userId, String groupsId) throws Exception {
        // 保存组用户关联表
        if (!StringUtils.isNullOrEmpty(groupsId)) {
            String[] ids = groupsId.split(",");
            for (String id : ids) {
                saveGroupUser(Long.valueOf(id), userId);
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

        Group group = groupService.selectByPk(groupId);
        if (group != null && group.getGrade() != 0) {
            groupUser.setCodePath(TreeCodeUtils.splitCode(group.getCode(), 2, "|"));
        }

        groupUserService.insert(groupUser);
    }

    /**
     * 更新系统用户信息
     *
     * @param user 源信息
     * @param info 修改信息
     */
    private void updateSysUserInfo(User user, EditAdminInfo info) {
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

    /**
     * 根据用户ID查询用户所属组信息，并按公司、部门组装返回
     * @param userId 用户ID
     * @return Map<Long, Group>（{"Long":公司ID, "Group":公司信息}}）
     */
    private Map<Long, Group> selGroupByUserId(Long userId){
        Map<Long, Group> groupMap = new HashMap<>();

        // 查询组用户关联表信息
        List<GroupUser> groupUsers = groupUserService.selectByUserId(userId);
        if (groupUsers != null) {
            // 查询用户所属公司及部门信息
            List<Long> groupIds = BeanUtils.getPropertyValues2List(groupUsers, "groupId");
            if (groupIds != null && groupIds.size() > 0){

                GroupQuery groupQuery = new GroupQuery();
                groupQuery.setIds(groupIds);
                groupQuery.setSortName("code");
                groupQuery.setSortType(0);

                List<Group> groups = groupService.select(groupQuery);

                // 拆分部门及公司，按公司ID将公司和部门分配至相同组
                groupMap = assemblyCompanyAndDept(groups);
            }
        }

        return groupMap;
    }

    /**
     * 拆分部门及公司，按公司ID将公司和部门分配至相同组
     * @param groups 组集合
     * @return Map<Long, Map<String,List<Group>>>
     */
    private Map<Long, Group> assemblyCompanyAndDept(List<Group> groups) {
        Map<Long, Group> groupMap = new HashMap<>();
        for (Group group : groups) {
            if (group != null) {
                if (group.getGrade() == 0){
                    groupMap.put(group.getId(), group);
                }else {
                    Group parentGroup = groupMap.get(group.getGroupId());
                    if (parentGroup != null){
                        addChildToParent(group, parentGroup);
                    }
                }
            }
        }

        return groupMap;
    }

    /**
     * 递归，直到将子级添加至对应的父级对象中
     * @param son   子级
     * @param parent    父级
     */
    private void addChildToParent(Group son, Group parent){
        List<Group> sons = parent.getChildrenList();
        if (sons == null){
            sons = new ArrayList<>();
            sons.add(son);

            parent.setChildrenList(sons);
        }else{
            if (son.getPid().equals(parent.getId())){
                sons.add(son);

                parent.setChildrenList(sons);
            }else {
                for (Group sonG : sons){
                    if (son.getPid().equals(sonG.getId())){
                        addChildToParent(son, sonG);
                    }
                }
            }
        }
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
//
//    /**
//     * 过滤出在源数据里面不存在的ID
//     *
//     * @param source 源数据
//     * @param target 需过滤的数据
//     * @return List<Long>
//     */
//    private List<Long> filterNotExitIds(List<Long> source, List<Long> target) {
//        List<Long> ids = new ArrayList<>();
//        for (Long id : target) {
//            Boolean isExit = false;
//            for (Long exitId : source) {
//                if (Objects.equals(id, exitId)) {
//                    isExit = true;
//                }
//            }
//
//            if (!isExit) {
//                ids.add(id);
//            }
//        }
//
//        return ids;
//    }

    /**
     * 是否要查询角色信息
     * @param groupId   公司ID
     * @param type      0查询角色信息，1不查询角色信息
     * @return List<OperatorListInfo>
     */
    private List<OperatorListInfo> assemblyOperatorInfo(Long groupId, int type) throws Exception{
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

                    if (type == 0){
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
     * 根据主键ID查询账号信息
     * @param accountId 主键ID
     * @return Account
     */
    private Account selAccountById4Ex(Long accountId){
        Account account = accountService.selectByPk(accountId);
        if (account == null) {
            throw new BusinessException("对不起，未查询到该账号！");
        }

        return account;
    }

    /**
     * 根据用户ID查询用户账号关联表
     * @param userId 用户ID
     * @return List<Account>
     */
    private List<Account> selAccountByUserId(Long userId){
        List<UserAccount> accounts = userAccountService.selectByUserId(userId);
        List<Long> ids = BeanUtils.getPropertyValues2List(accounts, "accountId");
        if (ids != null && ids.size() > 0){
            AccountQuery accountQuery = new AccountQuery();
            accountQuery.setIds(ids);

            return accountService.select(accountQuery);
        }

        return null;
    }

    /**
     * 验证查询的用户类型是否跟页面请求的一致
     * @param type  0查询个人用户，1查询内部用户
     * @param userType
     */
    private void validateUserType(int type, int userType){
        if (type == 1 && userType == 0){
            throw new BusinessException("对不起，该系统用户不存在！");
        }else if (type == 0 && userType == 1){
            throw new BusinessException("对不起，该个人用户不存在！");
        }
    }
}
