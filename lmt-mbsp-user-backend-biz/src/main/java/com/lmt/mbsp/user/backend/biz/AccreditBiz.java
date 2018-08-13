package com.lmt.mbsp.user.backend.biz;

import com.lmt.mbsp.user.entity.permission.Permission;
import com.lmt.mbsp.user.vo.accredit.EditAccreditInfo;

import java.util.List;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/16/2018 17:55
 * @Description:
 */
public interface AccreditBiz {
    /**
     * 配置角色对应的资源和权限
     * @param editAccreditInfo
     * @return
     */
    boolean config(EditAccreditInfo editAccreditInfo);

    List<Permission> getPermissionsByRole(Long ...roleId);

    List<Permission> getPermissionsByRole(List<Long> roleId);
}
