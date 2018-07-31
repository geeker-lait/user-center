package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.PermissionQuery;
import com.lmt.mbsp.user.entity.permission.Permission;
import com.lmt.mbsp.user.service.PermissionService;
import com.lmt.mbsp.user.vo.permission.PermissionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述: 权限.
 * @作者: lijing.
 * @创建时间: 2018-06-27 16:41.
 * @版本: 1.0 .
 */
@RestController
@RequestMapping("/permission")
public class PermissionController implements CrudController<Permission, Long, PermissionQuery, PermissionInfo> {
    @Autowired
    private PermissionService permissionService;

    @Override
    public CrudService<Permission, Long> getService() {
        return permissionService;
    }
}
