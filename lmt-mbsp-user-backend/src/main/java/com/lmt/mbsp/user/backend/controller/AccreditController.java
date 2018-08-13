package com.lmt.mbsp.user.backend.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.backend.biz.AccreditBiz;
import com.lmt.mbsp.user.backend.biz.RoleResourcesBiz;
import com.lmt.mbsp.user.dto.AccreditQuery;
import com.lmt.mbsp.user.entity.account.AccountRole;
import com.lmt.mbsp.user.entity.accredit.Accredit;
import com.lmt.mbsp.user.entity.resources.Resources;
import com.lmt.mbsp.user.service.AccountRoleService;
import com.lmt.mbsp.user.service.AccreditService;
import com.lmt.mbsp.user.service.RolePermissionService;
import com.lmt.mbsp.user.vo.accredit.AccreditInfo;
import com.lmt.mbsp.user.vo.accredit.EditAccreditInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Auther: lait.zhang@gmail.com
 * Tel:15801818092
 * Date: 6/1/2018 17:13
 * Description: 授权管理
 */
@RestController
@RequestMapping("/accredit")
public class AccreditController implements CrudController<Accredit, Long, AccreditQuery, AccreditInfo> {
    private AccreditService accreditService;
    private AccreditBiz accreditBiz;
    private RoleResourcesBiz roleResourcesBiz;
    private AccountRoleService accountRoleService;


    @Autowired
    public AccreditController(AccreditService accreditService,
                              RoleResourcesBiz roleResourcesBiz,
                              AccreditBiz accreditBiz,
                              AccountRoleService accountRoleService) {
        this.accreditService = accreditService;
        this.roleResourcesBiz = roleResourcesBiz;
        this.accreditBiz = accreditBiz;
        this.accountRoleService = accountRoleService;

    }

    @Override
    public CrudService<Accredit, Long> getService() {
        return accreditService;
    }


    /**
     * 配置角色对应的资源
     *
     * @param editAccreditInfo
     * @return
     * @throws Exception
     */
    @PatchMapping("/role-resouces/{id}")
    @ApiOperation(value = "后台- 配置角色对应的资源", responseReference = "post")
    public ResponseMessage<EditAccreditInfo> editRoleResources(@PathVariable Long id, EditAccreditInfo editAccreditInfo) {
        editAccreditInfo.setId(id);
        roleResourcesBiz.config(editAccreditInfo);
        return ResponseMessage.ok();
    }


    /**
     * 配置角色对应的资源权限
     *
     * @param editAccreditInfo
     * @return
     * @throws Exception
     */
    @PatchMapping("/role-resources-permission/{id}")
    @ApiOperation(value = "后台- 配置角色对应的资源和权限", responseReference = "post")
    public ResponseMessage<EditAccreditInfo> editRoleResourcesPermission(@PathVariable Long id, EditAccreditInfo editAccreditInfo) {
        editAccreditInfo.setId(id);
        accreditBiz.config(editAccreditInfo);
        return ResponseMessage.ok();
    }


    @GetMapping("/{accountId}/resources")
    @ApiOperation(value = "根据账号ID获取角色对应的资源和权限", responseReference = "get")
    public ResponseMessage<List<Resources>> getResourcesByAccount(@PathVariable Long accountId) {
        // 1、获取账号所对应的所有角色id
        List<Long> roleIds = accountRoleService.selectByAccountId(accountId).stream().map(AccountRole::getRoleId).collect(Collectors.toList());
        // 2、根据角色获取角色所对应的资源
        return ResponseMessage.ok(roleResourcesBiz.getResourcesByRole(roleIds));
    }


}
