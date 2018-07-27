package com.lmt.mbsp.user.entity.role;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 6/1/2018 10:28
 * @Description:
 */
@Entity
@Data
@Table(name = "t_role_permission")
public class RolePermission implements com.lmt.framework.support.entity.Entity {

    @Id
    private Long id;
    // 角色id
    private Long roleId;
    // 权限id
    private Long permissionId;

}
