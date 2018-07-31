package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.PermissionResourcesQuery;
import com.lmt.mbsp.user.entity.permission.PermissionResources;
import com.lmt.mbsp.user.service.PermissionResourcesService;
import com.lmt.mbsp.user.vo.permission.PermissionResourcesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/permission-resources")
public class PermissionResourcesController implements CrudController<PermissionResources,Long,PermissionResourcesQuery,PermissionResourcesInfo> {
    @Autowired
    private PermissionResourcesService permissionResourcesService;

    @Override
    public CrudService<PermissionResources, Long> getService() {
        return permissionResourcesService;
    }





}
