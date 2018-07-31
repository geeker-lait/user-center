package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.dto.UrlResourcesQuery;
import com.lmt.mbsp.user.entity.resources.UrlResources;
import com.lmt.mbsp.user.service.UrlResourcesService;
import com.lmt.mbsp.user.vo.resources.UrlResourcesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/url-resource")
public class UrlResourcesController implements CrudController<UrlResources, Long, UrlResourcesQuery, UrlResourcesInfo> {
    @Autowired
    private UrlResourcesService urlResourcesService;

    @Override
    public CrudService<UrlResources, Long> getService() {
        return urlResourcesService;
    }
}
