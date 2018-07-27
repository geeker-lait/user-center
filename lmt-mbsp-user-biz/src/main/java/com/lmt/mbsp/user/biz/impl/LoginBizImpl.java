package com.lmt.mbsp.user.biz.impl;

import com.lmt.mbsp.user.biz.LoginBiz;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.permission.PermissionResources;
import com.lmt.mbsp.user.entity.resources.UrlResources;
import com.lmt.mbsp.user.entity.role.RolePermission;
import com.lmt.mbsp.user.redis.RedisService;
import com.lmt.mbsp.user.service.*;
import com.lmt.mbsp.user.vo.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final AccountRoleService accountRoleService;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final PermissionResourcesService permissionResourcesService;
    private final UrlResourcesService urlResourcesService;
    private final RedisService redisService;

    @Autowired
    public LoginBizImpl(AccountService accountService, AccountRoleService accountRoleService, RoleService roleService,
                        RolePermissionService rolePermissionService, PermissionResourcesService permissionResourcesService,
                        UrlResourcesService urlResourcesService, RedisService redisService) {
        this.accountService = accountService;
        this.accountRoleService = accountRoleService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
        this.permissionResourcesService = permissionResourcesService;
        this.urlResourcesService = urlResourcesService;
        this.redisService = redisService;
    }

    /**
     * 登陆操作
     * 1、通过登陆账号，获取账号ID
     * 2、通过账号ID查询AccountRole关联表中的所有分配的角色ID
     * 3、通过遍历角色ID查询RolePermission表中的所对应的权限ID
     * 4、通过遍历权限ID获取PermissionResource表中所对应的资源ID
     * 5、组装资源数据（菜单资源、数据资源等）
     * 6、将组装数据放入Redis
     * @param loginInfo
     * @return
     * @throws Exception
     */
    @Override
    public Object login(LoginInfo loginInfo) throws Exception {
        //1、 先从redis中查、如果查询到直接返回权限树
        Object menuPremission = redisService.get("MENU-PERMISSION-" + loginInfo.getAccount());
        if (menuPremission != null) {
            return menuPremission;
        }

        //2、 如果没有查询到，去数据库中查询，然后放入redis 缓存
        // 先查询出账户id
        //Account account = accountService.selectByAccount(loginInfo.getAccount());
        //
        AccountRole accountRole = new AccountRole();
        //accountRole.setAccountId(account.getId());
        // 查询账户和角色关联表,获取对应的角色
        List<AccountRole> accountRoleList = accountRoleService.select(accountRole);
        // 获取角色ID列表，查询角色所对应的权限
        List<RolePermission> rolePermissionList = rolePermissionService.selectByPk(
                accountRoleList.stream().map(r -> r.getRoleId()).collect(Collectors.toList())
        );
        // 查询权限所对应的资源
        List<PermissionResources> permissionResourcesList = permissionResourcesService.selectByPk(
                rolePermissionList.stream().map(r -> r.getPermissionId()).collect(Collectors.toList())
        );
        // 查询资源(菜单资源)
        List<UrlResources> urlResourcesList = urlResourcesService.selectByPk(
                permissionResourcesList.stream().map(p -> p.getResourcesId()).collect(Collectors.toList())
        );
        //


        redisService.set("MENU-PERMISSION", null, 30 * 1000L);


        return null;
    }


    /**
     * 登出操作
     * @return
     */
    public Object logout(){


        return null;
    }
}
