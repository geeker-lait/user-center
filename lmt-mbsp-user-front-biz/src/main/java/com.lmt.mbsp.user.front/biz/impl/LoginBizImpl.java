package com.lmt.mbsp.user.front.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.utils.encrypt.MD5Utils;
import com.lmt.mbsp.user.adapter.service.CaptchaClient;
import com.lmt.mbsp.user.entity.account.AccountName;
import com.lmt.mbsp.user.front.biz.LoginBiz;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.group.GroupUser;
import com.lmt.mbsp.user.entity.accredit.Accredit;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.entity.user.UserAccount;
import com.lmt.mbsp.user.front.biz.vo.LoginContext;
import com.lmt.mbsp.user.redis.RedisService;
import com.lmt.mbsp.user.service.*;
import com.lmt.mbsp.user.vo.login.LoginInfo;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 12:00
 * @Description:
 */
@Service
public class LoginBizImpl implements LoginBiz {

    private final AccountService accountService;
    private final AccountNameService accountNameService;
    private final AccountRoleService accountRoleService;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    //private final PermissionResourcesService permissionResourcesService;
    //private final UrlResourcesService urlResourcesService;
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final GroupService groupService;
    private final GroupUserService groupUserService;
    private final RedisService redisService;
    private final CaptchaClient captchaClient;

    private static final String LOGIN_KEY = "LOGIN-PERMISSION";

    @Autowired
    public LoginBizImpl(AccountService accountService, AccountNameService accountNameService, AccountRoleService accountRoleService, RoleService roleService, RolePermissionService rolePermissionService, UserService userService, UserAccountService userAccountService, GroupService groupService, GroupUserService groupUserService, RedisService redisService, CaptchaClient captchaClient) {
        this.accountService = accountService;
        this.accountNameService = accountNameService;
        this.accountRoleService = accountRoleService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
        //this.permissionResourcesService = permissionResourcesService;
        //this.urlResourcesService = urlResourcesService;
        this.userService = userService;
        this.userAccountService = userAccountService;
        this.groupService = groupService;
        this.groupUserService = groupUserService;
        this.redisService = redisService;
        this.captchaClient = captchaClient;
    }

    private HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }

    /**
     * 登陆操作
     * 1、通过登陆账号，获取账号ID
     * 2、通过账号ID查询AccountRole关联表中的所有分配的角色ID
     * 3、通过遍历角色ID查询RolePermission表中的所对应的权限ID
     * 4、通过遍历权限ID获取PermissionResource表中所对应的资源ID
     * 5、组装资源数据（菜单资源、数据资源等）
     * 6、将组装数据放入Redis
     *
     * @param info
     * @return
     * @throws Exception
     */
    @Override
    public Boolean login(LoginInfo info) throws Exception {
        if (loginContext() != null) {
            return true;
        }

        AccountName accountName = accountNameService.selectByAccountName(info.getAccountName());
        if (accountName == null){
            throw new BusinessException("登录用户不存在");
        }
        Account account = accountService.selectByPk(accountName.getAccountId());// 图片验证码与session进行验证
        if (!captchaClient.verifyImage(info.getCataptchCode())) {
            throw new BusinessException("图片验证码错误");
        }
        if (account == null || !MD5Utils.validatePassword(account.getPassword(), info.getPassword())) {
            throw new BusinessException("用户名密码未匹配到用户，请重试");
        }
        int expireTime = info.getRemember() ? 3600 * 24 * 7 : 60 * 30; // 选择记住可以记录7天，否则半小时
        getSession().setAttribute(LOGIN_KEY, buildLoginContext(account));
        getSession().setMaxInactiveInterval(expireTime);
        return loginContext() != null;
//        return redisService.set(LOGIN_KEY, buildLoginContext(accountName), expireTime);
    }

    @Override
    public LoginContext getLoginContext() throws Exception {
        return loginContext();
    }

    @Override
    public void Logout() throws Exception {

    }

    private LoginContext loginContext() {
        LoginContext loginContext = null;
        // 检查Session中是否存在
        Object loginObj = getSession().getAttribute(LOGIN_KEY);
        if (loginObj != null) {
            loginContext = (LoginContext) loginObj;
        }
        return loginContext;
    }

    private LoginContext buildLoginContext(Account account) {
        LoginContext context = new LoginContext();
        context.setAccountId(account.getId());
        context.setUsername(account.getAccountName());
        context.setMaster(account.getMaster());

        UserAccount userAccount = userAccountService.selectByAccountId(account.getId());
        User user = userService.selectByPk(userAccount.getUserId());
        context.setUserId(user.getId());
        context.setRealName(user.getName());

        // 查询账户和角色关联表,获取对应的角色
        List<AccountRole> accountRoleList = accountRoleService.selectByAccountId(account.getId());

        if (!accountRoleList.isEmpty()) {
            List<GroupUser> groupUsers = groupUserService.selectByUserId(user.getId());
            if (!groupUsers.isEmpty()) {
                context.setGroupId(groupUsers.get(0).getGroupId());
            }
        }
        return context;
    }

    private Object getMenus(LoginInfo loginInfo) {
        //1、 先从redis中查、如果查询到直接返回权限树
//        Object menuPremission = redisService.get("MENU-PERMISSION-" + loginInfo.getAccount());
//        if (menuPremission != null) {
//            return menuPremission;
//        }

//        2、如果没有查询到，去数据库中查询，然后放入redis 缓存
//        先查询出账户id
        Account account = null;
        //
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(account.getId());
        // 查询账户和角色关联表,获取对应的角色
        List<AccountRole> accountRoleList = accountRoleService.select(accountRole);
        // 获取角色ID列表，查询角色所对应的权限
        List<Accredit> accreditList = rolePermissionService.selectByPk(accountRoleList.stream().map(r -> r.getRoleId()).collect(Collectors.toList()));
        // 查询权限所对应的资源
        //List<PermissionResources> permissionResourcesList = permissionResourcesService.selectByPk(accreditList.stream().map(r -> r.getPermissionId()).collect(Collectors.toList()));
        // 查询资源(菜单资源)
        //List<UrlResources> urlResourcesList = urlResourcesService.selectByPk(permissionResourcesList.stream().map(p -> p.getResourcesId()).collect(Collectors.toList()));
        //


        redisService.set("MENU-PERMISSION", account, 30 * 1000L);

        return null;
    }
}
