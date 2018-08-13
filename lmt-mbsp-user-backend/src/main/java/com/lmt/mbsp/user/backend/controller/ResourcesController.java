package com.lmt.mbsp.user.backend.controller;

import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.ResourcesQuery;
import com.lmt.mbsp.user.entity.resources.Resources;
import com.lmt.mbsp.user.service.ResourcesService;
import com.lmt.mbsp.user.vo.resources.ResourcesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 6/1/2018 14:05
 * @Description:
 */
@RestController
@RequestMapping("/resources")
public class ResourcesController implements CrudController<Resources,Long,ResourcesQuery,ResourcesInfo> {
    @Autowired
    private ResourcesService resourcesService;

    @Override
    public CrudService<Resources, Long> getService() {
        return resourcesService;
    }


}
