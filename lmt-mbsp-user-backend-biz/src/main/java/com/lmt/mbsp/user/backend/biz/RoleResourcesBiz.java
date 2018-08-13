package com.lmt.mbsp.user.backend.biz;

import com.lmt.mbsp.user.entity.resources.Resources;
import com.lmt.mbsp.user.vo.accredit.EditAccreditInfo;

import java.util.List;

/**
 *  配置角色对应的资源和权限
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/16/2018 17:55
 * @Description:
 */
public interface RoleResourcesBiz {
    /**
     * 配置角色对应的资源和权限
     * @param editAccreditInfo
     * @return
     */
    boolean config(EditAccreditInfo editAccreditInfo);

    /**
     * 根据角色ID 获取所有的资源(结合权限值过滤)
     * @param roleId
     */
    List<Resources> getResourcesByRole(Long ...roleId);

    List<Resources> getResourcesByRole(List<Long> roleId);
}
