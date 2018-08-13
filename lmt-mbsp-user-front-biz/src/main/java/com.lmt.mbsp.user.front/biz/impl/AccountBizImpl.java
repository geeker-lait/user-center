package com.lmt.mbsp.user.front.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.support.bean.EntityToModelUtils;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.framework.utils.encrypt.MD5Utils;
import com.lmt.mbsp.user.adapter.service.CaptchaClient;
import com.lmt.mbsp.user.adapter.service.SenderClient;
import com.lmt.mbsp.user.adapter.service.ValidateClient;
import com.lmt.mbsp.user.front.biz.AccountBiz;
import com.lmt.mbsp.user.dto.AccountNameQuery;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.account.AccountName;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.service.*;
import com.lmt.mbsp.user.vo.account.*;
import com.lmt.mbsp.user.vo.enumutil.StatusEnum;
import com.lmt.mbsp.user.vo.regist.RegistErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 描述: 账号聚合服务.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 17:18.
 */
@Service
public class AccountBizImpl implements AccountBiz {
    private final static String FIND_PWD = "_find_pwd";

    private AccountService accountService;
    private AccountNameService accountNameService;
    private UserService userService;
    private AccountRoleService accountRoleService;
    private RoleService roleService;
    private CaptchaClient captchaClient;
    private SenderClient senderClient;
    private ValidateClient validateClient;

    @Autowired
    public AccountBizImpl(AccountService accountService, UserService userService, AccountNameService accountNameService,
                          AccountRoleService accountRoleService, RoleService roleService,
                          CaptchaClient captchaClient, SenderClient senderClient, ValidateClient validateClient) {
        this.accountService = accountService;
        this.accountNameService = accountNameService;
        this.userService = userService;
        this.captchaClient = captchaClient;
        this.senderClient = senderClient;
        this.validateClient = validateClient;
        this.accountRoleService = accountRoleService;
        this.roleService = roleService;
    }

    @Override
    public Boolean checkUserName(String userName) throws Exception {
        AccountName account = selByUserName(userName, StatusEnum.USERNAME_TYPE.getStatus());
        return account != null;
    }

    @Override
    public Boolean checkMobile(String mobile) throws Exception {
        AccountName account = selByUserName(mobile, StatusEnum.MOBILE_TYPE.getStatus());
        return account != null;
    }

    @Override
    public Boolean findPwd(String mobile) throws Exception{
        // 创建验证码
        String code = validateClient.createCode(mobile + FIND_PWD);
        String message = String.format("您的重置密码验证码：%s，五分钟内有效", code);

        // 发送验证码
        ResponseEntity response = senderClient.sendSms(mobile, message);
        System.out.println("sms = [" + message + "]");

        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void resetPwd(ResetPasswordInfo info) throws Exception {
        // 根据用户名查询账号信息
        AccountName accountName = selByUserName4Ex(info.getMobile());

        Account account = selAccountById4Ex(accountName.getAccountId());

        // 手机短信验证码验证
        if (!validateClient.verifyCode(info.getMobile() + FIND_PWD, info.getCode())) {
            throw new BusinessException("对不起，手机号与验证码不匹配！");
        }

        // 重置密码
        resetPassword(account, info.getNewPwd(), info.getNewPwdAgain(), false, "");
    }

    @Override
    public void editPwd(EditPasswordInfo info) throws Exception {
        // 查询账号信息
        Account account = selAccountById4Ex(info.getAccountId());

        // 重置密码
        resetPassword(account, info.getNewPwd(), info.getNewPwdAgain(), true, info.getOldPwd());
    }

    @Override
    public String selectPwd(Long accountId) throws Exception {
        // 查询账号信息
        Account account = selAccountById(accountId);
        if (account != null) {
            return account.getPassword();
        } else {
            return "";
        }
    }

    @Override
    public void disabled(Long id) throws Exception {
        Account account = selAccountById4Ex(id);
        if (!account.getState().equals(StatusEnum.DISABLED.getStatus())){
            account.setState(StatusEnum.DISABLED.getStatus());

            accountService.updateByPk(id, account);
        }
    }

    @Override
    public void unDisabled(Long id) throws Exception {
        Account account = selAccountById4Ex(id);
        if (!account.getState().equals(StatusEnum.UNDISABLED.getStatus())){
            account.setState(StatusEnum.UNDISABLED.getStatus());

            accountService.updateByPk(id, account);
        }
    }

    @Override
    public ToEditAccountInfo toEditAccount(Long id) throws Exception {
        AccountName name = selAccNameById4Ex(id);
        if (name.getType().equals(StatusEnum.USERNAME_TYPE.getStatus())){
            throw new BusinessException("主账号不能进行修改！");
        }

        Account account = selAccountById4Ex(name.getAccountId());

        ToEditAccountInfo info = new ToEditAccountInfo();
        info.setAccountId(account.getId());
        info.setAccountName(account.getAccountName());
        info.setSonId(name.getId());
        info.setSonName(name.getAccountName());
        info.setType(name.getType());

        return info;
    }

    @Override
    public void editAccount(EditAccountInfo info) throws Exception {
        selAccountById4Ex(info.getId());

        AccountName name = accountNameService.selectByAccountName(info.getOldName());
        if (name == null){
            throw new BusinessException("对不起，旧账号不存在！");
        }
        if (name.getAccountName().equals(info.getNewName())){
            throw new BusinessException("对不起，旧账号与新账号一致，不能进行修改！");
        }

        validateAccName(info.getNewName(), info.getCode());

        name.setAccountName(info.getNewName());

        accountNameService.updateByPk(info.getId(), name);
    }

    @Override
    public void bindAcc(BindAccountInfo info) throws Exception{
        selAccountById4Ex(info.getId());

        validateAccName(info.getAccountName(), info.getCode());

        accountNameService.insert(assembleAccName(info.getId(), info.getAccountName(), info.getType()));
    }

    @Override
    public ToAccountAuthorizeInfo toAuthorize(Long userId, Long accountId) throws Exception {
        ToAccountAuthorizeInfo info = new ToAccountAuthorizeInfo();

        // 1.查询用户信息
        User user = selUserById4Ex(userId);
        info.setName(user.getName());
        info.setUserId(user.getId());

        // 2.查询账号信息
        Account account = selAccountById4Ex(accountId);
        info.setAccountId(account.getId());
        info.setAccountName(account.getAccountName());

        // 3.查询账号已赋角色信息（所有可控组角色信息通过调用角色中的接口获取）
        List<Role> givenRoles = selRolesByAccountId(account.getId());
        List<Long> roleIds = BeanUtils.getPropertyValues2List(givenRoles, "id");

        info.setGivenRoleIds(roleIds);

        return info;
    }

    @Override
    public void authorize(SaveAccountAuthorizeInfo info) throws Exception {
        selUserById4Ex(info.getUserId());

        selAccountById4Ex(info.getAccountId());

        // 删除账号角色关联表
        accountRoleService.deleteByAccountId(info.getAccountId());

        // 重新保存账号角色关联表
        saveAccountRoles(info.getAccountId(), info.getRoleIds());
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
     * 组装账号密码对象信息并返回
     *
     * @param accountId 账号ID
     * @param username  用户名
     * @param type      类型（0主账号，1手机，2邮箱）
     * @return AccountName
     */
    private AccountName assembleAccName(Long accountId, String username, Integer type) {
        AccountName accPwd = new AccountName();
        accPwd.setAccountId(accountId);
        accPwd.setAccountName(username);
        accPwd.setType(type);

        return accPwd;
    }

    /**
     * 验证并重置密码
     *
     * @param acc                账号信息
     * @param newPwd             新密码
     * @param newAgainPwd        确认新密码
     * @param isValidationOldPwd 是否要验证旧密码，重置不需要
     * @param oldPwd             旧密码，如果不需验证旧密码可传空字符串
     */
    private void resetPassword(Account acc, String newPwd, String newAgainPwd, Boolean isValidationOldPwd, String oldPwd) throws BusinessException {
        if (acc != null) {
            if (!newPwd.equals(newAgainPwd)) {
                throw new BusinessException("对不起，两次密码不一致，请重新输入！");
            }
            if (isValidationOldPwd) {
                if (!acc.getPassword().equals(oldPwd)) {
                    throw new BusinessException("输入的旧密码有误，请重新输入！");
                }
            }
            if (acc.getPassword().equals(newPwd)){
                throw new BusinessException("新密码不能跟旧密码一致！");
            }

            String encodePwd = MD5Utils.generatePassword(newPwd);
            acc.setPassword(encodePwd);
            acc.setState(StatusEnum.PASSWORD_LOCK.getStatus()); // 重置密码后需要被禁用，强制在登录时进行密码修改以保证密码过于简单被别人使用

            // 更新密码
            accountService.updateByPk(acc.getId(), acc);
        }
    }

    /**
     * 根据用户姓名查询账号信息，如果不存在抛出异常
     *
     * @param userName 用户名
     * @return AccountName
     */
    private AccountName selByUserName4Ex(String userName) {
        AccountName account = selByUserName(userName);
        if (account == null) {
            throw new BusinessException("对不起，该账号不存在！");
        }

        return account;
    }

    /**
     * 根据账号名称查询账号名称信息
     *
     * @param userName 账号名称
     * @return AccountName
     */
    private AccountName selByUserName(String userName) {
        return accountNameService.selectByAccountName(userName);
    }

    /**
     * 根据账号名称+账号类型查询账号名称信息
     *
     * @param userName 用户名
     * @param type     类型（0主账号，1手机，2邮箱）
     * @return AccountName
     */
    private AccountName selByUserName(String userName, Integer type) {
        AccountNameQuery query = new AccountNameQuery();
        query.setAccountName(userName);
        query.setType(type);

        return accountNameService.selectSingle(query);
    }

    /**
     * 根据登录账号查询账号信息并检测是否存在抛出异常
     *
     * @param id 主键ID
     * @return Account
     */
    private Account selAccountById4Ex(Long id) {
        Account a = selAccountById(id);
        if (a == null) {
            throw new BusinessException("对不起，该账号不存在！");
        }

        return a;
    }

    /**
     * 根据主键ID查询账号名称信息并检测是否存在抛出异常
     *
     * @param id 主键ID
     * @return AccountName
     */
    private AccountName selAccNameById4Ex(Long id) throws Exception {
        AccountName a = accountNameService.selectByPk(id);
        if (a == null) {
            throw new BusinessException("对不起，该账号名称不存在！");
        }

        return a;
    }

    /**
     * 根据主键ID查询用户信息，并抛出不存在异常
     *
     * @param userId 用户ID
     * @return User
     */
    private User selUserById4Ex(Long userId) {
        User user = userService.selectByPk(userId);
        if (user == null) {
            throw new BusinessException("对不起，未查询到该用户！");
        }

        return user;
    }

    /**
     * 根据账号ID查询角色信息
     *
     * @param accountId 账号ID
     * @return List<Role>
     */
    private List<Role> selRolesByAccountId(Long accountId) {
        List<AccountRole> accountRoles = accountRoleService.selectByAccountId(accountId);
        List<Long> roleIds = BeanUtils.getPropertyValues2List(accountRoles, "roleId");
        if (roleIds != null && roleIds.size() > 0) {

            RoleQuery roleQuery = new RoleQuery();
            roleQuery.setIds(roleIds);

            return roleService.select(roleQuery);
        } else {
            return null;
        }
    }

    /**
     * 根据账号名称查询账号名称信息
     * @param name 账号名称
     * @param code 验证码
     */
    private void validateAccName(String name, String code){
        if (StringUtils.isNullOrEmpty(name)) {
            throw new BusinessException("对不起，账号不能为空！");
        }
        if (StringUtils.isNullOrEmpty(code)) {
            throw new BusinessException("对不起，验证码不能为空！");
        }

        AccountName accountName = accountNameService.selectByAccountName(name);
        if (accountName != null) {
            throw new BusinessException("对不起，该账号已存在！");
        }
        else {
            if (!validateClient.verifyCode(name, code)) {
                throw new BusinessException(RegistErrorInfo.WRONG_VERIFYCODE_MOBILE.name());
            }
        }
    }

    /**
     * 根据登录账号查询账号信息
     *
     * @param id 主键ID
     * @return Account
     */
    private Account selAccountById(Long id) {
        return accountService.selectByPk(id);
    }
}