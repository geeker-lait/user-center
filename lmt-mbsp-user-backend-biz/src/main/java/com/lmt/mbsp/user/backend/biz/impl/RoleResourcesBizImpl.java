package com.lmt.mbsp.user.backend.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.mbsp.user.backend.biz.RoleResourcesBiz;
import com.lmt.mbsp.user.entity.accredit.Accredit;
import com.lmt.mbsp.user.entity.resources.Resources;
import com.lmt.mbsp.user.service.ResourcesService;
import com.lmt.mbsp.user.service.RoleResourceService;
import com.lmt.mbsp.user.vo.accredit.EditAccreditInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RoleResourcesBizImpl implements RoleResourcesBiz {
    private RoleResourceService roleResourceService;
    private ResourcesService resourcesService;

    @Autowired
    public RoleResourcesBizImpl(RoleResourceService roleResourceService,ResourcesService resourcesService) {
        this.roleResourceService = roleResourceService;
        this.resourcesService = resourcesService;
    }

    @Override
    public boolean config(EditAccreditInfo editAccreditInfo) {
        try {
            List<Long> resIds = editAccreditInfo.getResourceIds();
            if(resIds != null) {
                for (int i = 0; i < resIds.size(); i++) {
                    Accredit accredit = new Accredit();
                    //accredit.setId(editAccreditInfo.getId());
                    accredit.setRoleId(editAccreditInfo.getRoleId());
                    accredit.setResourceId(resIds.get(i));
                    //accredit.setPermissions(editAccreditInfo.getPermissions().get(i));
                    roleResourceService.saveOrUpdate(accredit);
                    log.info("role-resources(t_accredit) config success from ", accredit.toString());
                }
            }
        }catch (Exception e){
            log.error("config role-resources(t_accredit) occur error from", editAccreditInfo.toString(),e.getMessage());
            throw  new BusinessException("资源配置发生异常:%s"+ e.getMessage());
        }
        return true;
    }

    /**
     * 根据角色ID 获取所有的资源(结合权限值过滤)
     * @param roleId
     */
    public List<Resources> getResourcesByRole(Long ...roleId){
        List<Accredit> accredits = roleResourceService.selectRoleResourcesByRoleIds(Arrays.asList(roleId));
        List<Long> resourceIdList = new ArrayList<>();
        accredits.forEach(rolePermission -> {
            String permissions = rolePermission.getPermissions();
            /**
             * 过滤条件  1 可见（可读） 2 可编辑（可写） 3 可删除（）
             */
            for (char c : permissions.toCharArray()) {
                if(c=='1'){
                    resourceIdList.add(rolePermission.getResourceId());
                }
            }
        });

        List<Resources> resources = resourcesService.selectByPk(resourceIdList);
        return resources;
    }

    @Override
    public List<Resources> getResourcesByRole(List<Long> roleId) {

        Long[] ids = new Long[roleId.size()];
        return getResourcesByRole(roleId.toArray(ids));
    }
}
