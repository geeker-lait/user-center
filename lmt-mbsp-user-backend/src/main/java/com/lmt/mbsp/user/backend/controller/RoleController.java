package com.lmt.mbsp.user.backend.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.framework.support.web.controller.QueryController;
import com.lmt.mbsp.user.backend.biz.RoleBiz;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.service.RoleService;

import com.lmt.mbsp.user.vo.role.AddRoleInfo;
import com.lmt.mbsp.user.vo.role.EditRoleInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
import com.lmt.mbsp.user.vo.role.ToEditRoleInfo;
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
@RequestMapping("/role")
public class RoleController implements CrudController<Role, Long, RoleQuery,RoleInfo> {
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
    @ApiOperation(value = "通用-根据组ID查询该组对应的角色信息（操作员账号从企业已有角色下授权）", responseReference = "get")
    public ResponseMessage<List<RoleInfo>> selByGroupId(@PathVariable Long groupId) throws Exception {
        return ResponseMessage.ok(roleBiz.selByGroupId(groupId));
    }

    @GetMapping("/selAll")
    @ApiOperation(value = "通用-查询所有角色信息", responseReference = "get")
    public ResponseMessage<List<RoleInfo>> selByGroupId() throws Exception {
        return ResponseMessage.ok(roleBiz.selAll());
    }

    /****************************************************************************************** 后台-新增角色 ******************************************************************/

    @GetMapping("/checkName/{name}")
    @ApiOperation(value = "通用-根据组ID查询该组对应的角色信息（用户账号从企业已有角色下授权）", responseReference = "get")
    public ResponseMessage<Boolean> checkName(@PathVariable String name) throws Exception {
        return ResponseMessage.ok(roleBiz.checkName(name));
    }

    @PostMapping("/add")
    @ApiOperation(value = "后台-新增角色信息", responseReference = "post")
    public ResponseMessage<Long> add(AddRoleInfo info) throws Exception {
        return ResponseMessage.ok(roleBiz.add(info));
    }

    @GetMapping("/toEdit/{id}")
    @ApiOperation(value = "后台-进入编辑角色信息页面", responseReference = "get")
    public ResponseMessage<ToEditRoleInfo> toEdit(@PathVariable Long id) throws Exception {
        return ResponseMessage.ok(roleBiz.toEdit(id));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "后台-编辑角色信息", responseReference = "put")
    public ResponseMessage<EditRoleInfo> edit(@RequestBody EditRoleInfo info) throws Exception {
        roleBiz.edit(info);
        return ResponseMessage.ok(info);
    }

}
