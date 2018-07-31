package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.RoleBiz;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.service.RoleService;

import com.lmt.mbsp.user.vo.role.RoleInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Auther: lait.zhang@gmail.com
 * Tel:15801818092
 * Date: 6/1/2018 17:13
 * Description:
 */
@RestController
@RequestMapping("/user/role")
public class RoleController implements CrudController<Role, Long, RoleQuery, RoleInfo> {
    private RoleService roleService;
    private RoleBiz roleBiz;

    @Autowired
    public RoleController(RoleService roleService, RoleBiz roleBiz){
        this.roleService = roleService;
        this.roleBiz = roleBiz;
    }

    @Override
    public CrudService<Role, Long> getService() {
        return roleService;
    }

    @GetMapping("/selByGroupId/{groupId}")
    @ApiOperation(value = "根据组ID查询该组对应的角色信息", responseReference = "get")
    public ResponseMessage<List<RoleInfo>> selByGroupId(@PathVariable Long groupId) throws Exception {
        return ResponseMessage.ok(roleBiz.selByGroupId(groupId));
    }

    @GetMapping("/selAll")
    @ApiOperation(value = "查询所有角色信息", responseReference = "get")
    public ResponseMessage<List<RoleInfo>> selByGroupId() throws Exception {
        return ResponseMessage.ok(roleBiz.selAll());
    }

}
