package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.RoleQuery;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.service.RoleService;

import com.lmt.mbsp.user.vo.RoleInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 6/1/2018 17:13
 * @Description:
 */
@RestController
@RequestMapping("/user/role")
public class RoleController implements CrudController<Role, Long, RoleQuery, RoleInfo> {
    Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleService roleService;

    @Override
    public CrudService<Role, Long> getService() {
        return roleService;
    }

}
