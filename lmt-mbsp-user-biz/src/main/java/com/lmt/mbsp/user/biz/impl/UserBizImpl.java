package com.lmt.mbsp.user.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
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
import com.lmt.mbsp.user.vo.*;
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
    private AccountNameService accountPasswordService;
    private RoleService roleService;

    @Autowired
    public void UserBizImpleService(GroupService groupService,
                                    GroupUserService groupUserService,
                                    UserAccountService userAccountService,
                                    UserService userService,
                                    UserPositionService userPositionService,
                                    AccountService accountService,
                                    AccountRoleService accountRoleService,
                                    AccountNameService accountPasswordService,
                                    RoleService roleService){
        this.groupService = groupService;
        this.groupUserService = groupUserService;
        this.userAccountService = userAccountService;
        this.userService = userService;
        this.userPositionService = userPositionService;
        this.accountService = accountService;
        this.accountRoleService = accountRoleService;
        this.accountPasswordService = accountPasswordService;
        this.roleService = roleService;
    }

    @Override
    public void supplementInfo(UserInfo info) throws Exception {
        Account account = selAccountById(info.getAccountId());

        // 前台注册未保存用户表需新增，后台及用户中心注册的则更新用户表信息
        if (account.getRegisterType() == 0) {
            Long userId = saveUser(info, 0, true);

            // 保存用户账号关联表信息
            saveUserAccount(userId, info.getAccountId());

            // 用户中心无需保存用户职位关联表
        } else {
            // 更新用户表信息
            editUserInfo(info, account);
        }
    }

    @Override
    public void editInfo(UserInfo info) throws Exception {
        editUserInfo(info, selAccountById(info.getAccountId()));
    }

    @Override
    public Long addOperator(AddOperatorInfo info) throws Exception {
        // 保存账号
        Long accId = saveAccount(info.getUsername(), "", 0, 2);

        // 保存账号密码表
        saveAccPwd(accId, info.getPassword(), info.getUsername());

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
            result.setData(usersToInfos(userPagerResult.getData()));
            result.setTotal(userPagerResult.getTotal());
        }

        return result;
    }

    @Override
    public UserDetailInfo userDetail(Long userId) throws Exception {
        UserDetailInfo userDetailInfo = new UserDetailInfo();

        // 1.查询用户信息
        User user = selUserById(userId);

        // 2.查询账号信息
//        Account account = selAccountByUserId(user.getId());

        // 3.查询用户所属公司及部门信息
        Map<Long, Group> groupMap = selGroupByUserId(user.getId());

        // 4.查询账号已赋角色信息
//        List<Role> roles = selByAccountId(account.getId());

        userDetailInfo.setUser(userToInfo(user));
//        userDetailInfo.setAccount(accountToInfo(account));
        userDetailInfo.setGroups(groupMap);
//        userDetailInfo.setGiveRoles(rolesToInfos(roles));

        return userDetailInfo;
    }

    @Override
    public Long addSysUser(AddSysUserInfo info) throws Exception {
        // 保存账号
        Long accId = saveAccount(info.getAccountName(), info.getMobile(), info.getManagerType(), 1);

        // 保存账号密码表
        String pwd = RandomUtil.randomChar();
        saveAccPwd(accId, pwd, info.getAccountName());

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
        saveGroupUser(groupId, userId);
    }

    @Override
    public UserDetailInfo getUserAuthorize(Long userId) throws Exception {
        UserDetailInfo info = userDetail(userId);
        info.setRoles(rolesToInfos(roleService.select()));

        return info;
    }

    @Override
    public void userAuthorize(SaveUserAuthorizeInfo info) throws Exception {
        selUserById(info.getUserId());

        if (info.getAccountId() == null || info.getAccountId() == 0L) {
            // 查询账号关联表获取账号ID
            UserAccount userAccount = selUserAccByUserIdAndAccId(info.getUserId(), info.getAccountId());

            info.setAccountId(userAccount.getAccountId());
        }

        // 删除账号角色关联表
        accountRoleService.deleteByAccountId(info.getAccountId());

        // 重新保存账号角色关联表
        saveAccountRoles(info.getAccountId(), info.getRoleIds());
    }

    @Override
    public ToEditSysUserInfo toEditUser(Long userId) throws Exception {
        ToEditSysUserInfo info = new ToEditSysUserInfo();

        // 1.查询用户信息
        User user = selUserById(userId);
        info.setAge(user.getAge());
        info.setDomicile(user.getDomicile());
        info.setEmail(user.getEmail());
        info.setIdCard(user.getIdCard());
        info.setName(user.getName());
        info.setPhone(user.getPhone());
        info.setQq(user.getQq());
        info.setSex(user.getSex());

        // 2.查询账号已赋部门信息
        List<GroupUser> groupUsers = groupUserService.selectByUserId(userId);
        info.setDeptIds(BeanUtils.getPropertyValues2List(groupUsers, "id"));

        return info;
    }

    @Override
    public void editSysUser(EditSysUserInfo info) throws Exception {
        User user = selUserById(info.getId());

        // 更新账号
        updateSysUserAccount(info);

        // 更新用户信息
        updateSysUserInfo(user, info);

        // 更新职位信息
        UserPosition position = userPositionService.selectByUserId(user.getId());
        if (position != null) {
            position.setCategoryId(info.getCategoryId());
            position.setPosition(info.getPosition());

            userPositionService.updateByPk(position.getId(), position);
        }

        List<GroupUser> groupUsers = groupUserService.selectByUserId(user.getId());
        if (groupUsers != null && groupUsers.size() > 0) {
            List<Long> exitIds = BeanUtils.getPropertyValues2List(groupUsers, "id");
            if (!StringUtils.isNullOrEmpty(info.getDeptIds())) {
                // 将字符串转为List
                String[] ids = info.getDeptIds().split(",");
                List<Long> currenIdList = new ArrayList<>();
                for (String id : ids) {
                    currenIdList.add(Long.valueOf(id));
                }

                // 删除组用户关联表（与部门关联）
                delGroupUserByIds(filterNotExitIds(currenIdList, exitIds));

                // 保存新的组用户关联表（与部门关联）
                saveGroupUsers(user.getId(), filterNotExitIds(currenIdList, exitIds));
            }
        } else {
            // 保存组用户关联表（与公司关联）
            saveGroupUser(info.getGroupId(), user.getId());

            // 保存组用户关联表（与部门关联）
            saveGroupUsers(user.getId(), info.getDeptIds());
        }
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
            result.setData(usersToInfos(userPagerResult.getData()));
            result.setTotal(userPagerResult.getTotal());
        }

        return result;
    }

    @Override
    public void disableUser(Long id) throws Exception {
        User user = selUserById(id);
        user.setState(1);

        userService.updateByPk(user.getId(), user);
    }

    @Override
    public void unDisableUser(Long id) throws Exception {
        User user = selUserById(id);
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
    public void addManager(Long groupId, Long accountId) throws Exception{
        Account account = selAccountById(accountId);

        List<OperatorListInfo> operatorListInfos = assemblyOperatorInfo(groupId, 1);
        if (operatorListInfos != null && operatorListInfos.size() > 0){
            for (OperatorListInfo info : operatorListInfos){
                if (info != null && info.getMaster() == 1){
                    throw new BusinessException("对不起，用户名为：" + info.getUsername() + "已经被授权管理员，不能重复操作！");
                }
            }
        }

        account.setMaster(1);

        accountService.updateByPk(accountId, account);
    }

    @Override
    public UserDetailInfo toSysUserAuthorize(Long userId, Long accountId) throws Exception {
        UserDetailInfo userDetailInfo = new UserDetailInfo();

        // 1.查询用户信息
        User user = selUserById(userId);

        // 2.查询账号信息
        Account account = selAccountByUserIdAndAccId(user.getId(), accountId);

        // 3.查询账号已赋角色信息
        List<Role> roles = selByAccountId(account.getId());

        // TODO 4.查询所有可控组角色信息

        userDetailInfo.setUser(userToInfo(user));
//        userDetailInfo.setAccount(accountToInfo(account));
        userDetailInfo.setGiveRoles(rolesToInfos(roles));

        return userDetailInfo;
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

    private Map<String, Account> searchAccounts(List<UserAccount> userAccounts){
        // 将账号ID组装至集合中
        List<Long> accIds = CommonUtils.getPropertyValues2List(userAccounts, "accountId");
        // 根据用户ID集合查询对应的用户账号信息
        if (accIds != null && accIds.size() > 0) {
            AccountQuery query = new AccountQuery();
            query.setIds(accIds);

            List<Account> accounts = accountService.select(query);

            return CommonUtils.assembly2Map(accounts, userAccounts, "id", "accountId");
        }

        return null;
    }

    /**
     * 更新用户信息
     *
     * @param info    页面传入用户信息参数
     * @param account 账号信息对象
     */
    private void editUserInfo(UserInfo info, Account account) throws Exception {
        // TODO
        // 如果填写了手机需验证短信验证码

        User user = selUserById(info.getId());
        if (!user.getIsSupplement()) {
            // 如果填写了手机更新账号表中的登录账号信息
            if (!StringUtils.isNullOrEmpty(info.getAccount())) {
                account.setAccountName(info.getAccount());

                accountService.updateByPk(info.getAccountId(), account);
            }

            // 更新用户信息
            user.setName(info.getName());
            user.setQq(info.getQq());
            user.setEmail(info.getEmail());
            user.setPhone(info.getPhone());
            user.setIsSupplement(true);

            userService.updateByPk(user.getId(), user);
        }
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
     * @param mobile       账号
     * @param managerType  管理员类别(0普通管理员 1超级管理员)
     * @param registerType 注册类型（0前台注册 1后台创建 2用户中心创建）
     * @return Long
     */
    private Long saveAccount(String username, String mobile, Integer managerType, Integer registerType) throws Exception {
        Account acc = new Account();
        acc.setAccountName(username);
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
     * 组装账号密码对象信息并返回
     *
     * @param accountId 账号ID
     * @param pwd       密码
     * @param username  用户名
     */
    private void saveAccPwd(Long accountId, String pwd, String username) throws Exception {
        AccountName accPwd = new AccountName();
        accPwd.setAccountId(accountId);
        accPwd.setAccountName(username);

        accountPasswordService.insert(accPwd);
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
     * 删除组用户关联表
     *
     * @param ids 组用户关联表主键ID集合
     */
    private void delGroupUserByIds(List<Long> ids){
        if (!StringUtils.isNullOrEmpty(ids)) {
            for (Long id : ids) {
                groupUserService.deleteByPk(id);
            }
        }
    }

    /**
     * 删除组用户关联表
     *
     * @param groupsId 组ID集合
     */
    private void saveGroupUsers(Long userId, List<Long> groupsId) throws Exception {
        // 保存账号角色关联表
        if (!StringUtils.isNullOrEmpty(groupsId)) {
            for (Long id : groupsId) {
                saveGroupUser(id, userId);
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
    private void updateSysUserInfo(User user, EditSysUserInfo info) {
        // 更新用户信息
        user.setName(info.getName());
        user.setQq(info.getQq());
        user.setEmail(info.getEmail());
        user.setPhone(info.getPhone());
        user.setAge(info.getAge());
        user.setSex(info.getSex());
        user.setDomicile(info.getDomicile());
        user.setIdCard(info.getIdCard());
        user.setIsSupplement(true);

        userService.updateByPk(user.getId(), user);
    }

    /**
     * 更新系统用户账号
     *
     * @param info 系统用户信息
     */
    private void updateSysUserAccount(EditSysUserInfo info) throws Exception {
        Account account = selAccountByUserIdAndAccId(info.getId(), info.getAccountId());
        if (!account.getAccountName().equals(info.getAccount())) {
            account.setAccountName(info.getAccount());

            accountService.updateByPk(account.getId(), account);
        }
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
            if (son.getPid() == parent.getId()){
                sons.add(son);

                parent.setChildrenList(sons);
            }else {
                for (Group sonG : sons){
                    if (son.getPid() == sonG.getId()){
                        addChildToParent(son, sonG);
                    }
                }
            }
        }
    }

    /**
     * 根据账号ID查询角色信息
     *
     * @param accountId 账号ID
     * @return List<Role>
     */
    private List<Role> selByAccountId(Long accountId) {
        List<AccountRole> accountRoles = accountRoleService.selectByAccountId(accountId);
        List<Long> roleIds = BeanUtils.getPropertyValues2List(accountRoles, "roleId");
        if (roleIds != null && roleIds.size() > 0){

            RoleQuery roleQuery = new RoleQuery();
            roleQuery.setIds(roleIds);

            return roleService.select(roleQuery);
        }else {
            return null;
        }
    }

    /**
     * 过滤出在源数据里面不存在的ID
     *
     * @param source 源数据
     * @param target 需过滤的数据
     * @return List<Long>
     */
    private List<Long> filterNotExitIds(List<Long> source, List<Long> target) {
        List<Long> ids = new ArrayList<>();
        for (Long id : target) {
            Boolean isExit = false;
            for (Long exitId : source) {
                if (Objects.equals(id, exitId)) {
                    isExit = true;
                }
            }

            if (!isExit) {
                ids.add(id);
            }
        }

        return ids;
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
     * 用户集合entity转为info集合
     * @param users 用户Entity集合
     * @return List<RoleInfo>
     */
    private List<UserInfo> usersToInfos(List<User> users){
        List<UserInfo> infos = new ArrayList<>();
        if (users != null && users.size() > 0){
            for (User user : users){
                if (user != null){
                    infos.add(userToInfo(user));
                }
            }
        }

        return infos;
    }

    /**
     * 用户entity转为info
     * @param user 用户对象
     * @return UserInfo
     */
    private UserInfo userToInfo(User user){
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(user, info);

        return info;
    }

    /**
     * 账号entity转为info
     * @param account 账号对象
     * @return AccountInfo
     */
    private AccountInfo accountToInfo(Account account){
        AccountInfo info = new AccountInfo();
        BeanUtils.copyProperties(account, info);

        return info;
    }

    /**
     * 是否要查询角色信息
     * @param groupId   公司ID
     * @param type      0查询角色信息，1不查询角色信息
     * @return List<OperatorListInfo>
     */
    private List<OperatorListInfo> assemblyOperatorInfo(Long groupId, int type) throws Exception{
        List<OperatorListInfo> infoList = new ArrayList<>();

        // 1.查询组用户关联表获取组下所有用户信息
        GroupUserQuery groupUserQuery = new GroupUserQuery();
        groupUserQuery.setGroupId(groupId);

        List<GroupUser> groupUsers = groupUserService.select(groupUserQuery);
        if (groupUsers != null && groupUsers.size() > 0) {
            // 将用户ID组装至集合中
            List<Long> userIds = CommonUtils.getPropertyValues2List(groupUsers, "userId");
            if (userIds.size() > 0) {
                // 2.根据用户ID集合查询用户账号关联表信息
                UserAccountQuery userAccountQuery = new UserAccountQuery();
                userAccountQuery.setUserIds(userIds);

                List<UserAccount> userAccounts = userAccountService.select(userAccountQuery);
                if (userAccounts != null && userAccounts.size() > 0) {
                    // 3.根据账号ID集合查询对应的用户账号信息
                    Map<String, Account> accountMap = searchAccounts(userAccounts);

                    // 4.根据用户ID集合查询用户信息
                    Map<String, User> userMap = searchUsers(userIds, userAccounts);

                    if (type == 0){
                        // TODO 查询角色权限
                    }
                    // 5.将用户和账号信息进行匹配并返回
                    for (String id : userMap.keySet()) {
                        if (id != null) {
                            OperatorListInfo userInfo = new OperatorListInfo();
                            userInfo.setGroupId(groupId);
                            if (accountMap != null && accountMap.get(id) != null) {
                                Account acc = accountMap.get(id);
                                userInfo.setAccountId(acc.getId());
                                BeanUtils.copyProperties(acc, userInfo);
                            }
                            if (userMap != null && userMap.get(id) != null) {
                                BeanUtils.copyProperties(userMap.get(id), userInfo);
                            }

                            infoList.add(userInfo);
                        }
                    }
                }
            }
        }

        return infoList;
    }

    /**
     * 根据主键ID查询用户信息，并抛出不存在异常
     *
     * @param userId 用户ID
     * @return User
     */
    private User selUserById(Long userId) throws Exception {
        User user = userService.selectByPk(userId);
        if (user == null) {
            throw new BusinessException("对不起，未查询到该用户！");
        }

        return user;
    }

    /**
     * 根据主键ID查询账号信息
     *
     * @param accountId 主键ID
     * @return Account
     */
    private Account selAccountById(Long accountId) throws Exception {
        Account account = accountService.selectByPk(accountId);
        if (account == null) {
            throw new BusinessException("对不起，未查询到该账号！");
        }

        return account;
    }

    /**
     * 根据用户ID+账号ID查询账号信息
     * @param userId 用户ID
     * @param accountId 账号ID
     * @return Account
     */
    private Account selAccountByUserIdAndAccId(Long userId, Long accountId) throws Exception {
        UserAccount userAccount = selUserAccByUserIdAndAccId(userId, accountId);

        return selAccountById(userAccount.getAccountId());
    }

    /**
     * 根据用户ID+账号ID查询用户账号关联表信息
     * @param userId 用户ID
     * @param accountId 账号ID
     * @return UserAccount
     */
    private UserAccount selUserAccByUserIdAndAccId(Long userId, Long accountId){
        UserAccountQuery userQuery = new UserAccountQuery();
        userQuery.setUserId(userId);
        userQuery.setAccountId(accountId);

        UserAccount userAccount = userAccountService.selectSingle(userQuery);
        if (userAccount == null) {
            throw new BusinessException("对不起，用户账号关联信息不存在！");
        }

        return userAccount;
    }
}
