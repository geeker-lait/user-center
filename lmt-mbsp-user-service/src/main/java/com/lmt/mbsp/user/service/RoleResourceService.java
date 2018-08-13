package com.lmt.mbsp.user.service;


import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.accredit.Accredit;

import java.util.List;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 19:40
 * @Description:
 */
public interface RoleResourceService  extends CrudService<Accredit, Long> {

    /**
     * 根据角色ID查询角色所关联的资源信息集合
     * @param roleIds 角色ID
     * @return List<Accredit>
     */
    List<Accredit> selectRoleResourcesByRoleIds(List<Long> roleIds);
}
