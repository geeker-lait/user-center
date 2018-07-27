package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.ResourcesQuery;
import com.lmt.mbsp.user.entity.resources.Resources;
import com.lmt.mbsp.user.service.ResourcesService;
import com.lmt.mbsp.user.vo.ResourcesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/resource")
public class ResourcesController implements CrudController<Resources,Long,ResourcesQuery,ResourcesInfo> {
    @Autowired
    private ResourcesService resourcesService;

    @Override
    public CrudService<Resources, Long> getService() {
        return resourcesService;
    }




    @RequestMapping("config")
    public ResponseMessage config(){

        return null;
    }

}
