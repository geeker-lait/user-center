package com.lmt.mbsp.user.entity.role;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 6/1/2018 10:28
 * @Description:
 */
@Data
@Table(name = "t_accredit")
public class RoleResource implements Entity {

    @Id
    private Long id;
    // 角色id
    private Long roleId;
    // 资源ID
    private Long resourceId;

}
