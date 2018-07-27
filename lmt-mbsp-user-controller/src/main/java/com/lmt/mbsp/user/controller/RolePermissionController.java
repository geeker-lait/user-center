package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.RolePermissionQuery;
import com.lmt.mbsp.user.entity.role.RolePermission;
import com.lmt.mbsp.user.service.RolePermissionService;
import com.lmt.mbsp.user.vo.RolePermissionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/role-permission")
public class RolePermissionController implements CrudController<RolePermission, Long, RolePermissionQuery, RolePermissionInfo> {
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public CrudService<RolePermission, Long> getService() {
        return rolePermissionService;
    }
}
