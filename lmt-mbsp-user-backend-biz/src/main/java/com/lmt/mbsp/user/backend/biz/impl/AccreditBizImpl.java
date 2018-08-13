package com.lmt.mbsp.user.backend.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.mbsp.user.backend.biz.AccreditBiz;
import com.lmt.mbsp.user.entity.permission.Permission;
import com.lmt.mbsp.user.entity.accredit.Accredit;
import com.lmt.mbsp.user.service.AccreditService;
import com.lmt.mbsp.user.service.PermissionService;
import com.lmt.mbsp.user.vo.accredit.EditAccreditInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限配置（配置角色对应的资源和权限）
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/16/2018 17:55
 * @Description:
 */
@Slf4j
@Service
public class AccreditBizImpl implements AccreditBiz {
    private AccreditService accreditService;
    private PermissionService permissionService;

    @Autowired
    public AccreditBizImpl(AccreditService accreditService, PermissionService permissionService) {
        this.accreditService = accreditService;
        this.permissionService = permissionService;
    }

    /**
     * 配置角色资源权限
     * @param editAccreditInfo
     * @return
     */
    @Override
    public boolean config(EditAccreditInfo editAccreditInfo) {
        try {
            List<Long> resIds = editAccreditInfo.getResourceIds();
            if(resIds != null) {
                for (int i = 0; i < resIds.size(); i++) {
                    Accredit accredit = new Accredit();
                    accredit.setId(editAccreditInfo.getId());
                    accredit.setRoleId(editAccreditInfo.getRoleId());
                    accredit.setResourceId(resIds.get(i));
                    accredit.setPermissions(editAccreditInfo.getPermissions().get(i));
                    accreditService.saveOrUpdate(accredit);
                    log.info("config success for ", accredit.toString());
                }
            }
        }catch (Exception e){
            log.error("config error for", editAccreditInfo.toString(),e.getMessage());
            throw  new BusinessException("权限配置发生异常:%s"+ e.getMessage());
        }
        return true;
    }


    /**
     * 根据角色id查询权限
     * @param roleId
     */
    @Override
    public List<Permission> getPermissionsByRole(Long ...roleId){

        List<Accredit> accredits = accreditService.selectByRoleIds(Arrays.asList(roleId));

        List<Integer> permissionVals = new ArrayList<>();
        accredits.forEach(rolePermission -> {
            for (char c : rolePermission.getPermissions().toCharArray()) {
                permissionVals.add(Integer.valueOf(c));
            }
        });

        List<Permission> permission = permissionService.selectByVal(permissionVals);
        return permission;
    }

    @Override
    public List<Permission> getPermissionsByRole(List<Long> roleId) {
        Long[] ids = new Long[roleId.size()];
        return getPermissionsByRole(roleId.toArray(ids));
    }
}
