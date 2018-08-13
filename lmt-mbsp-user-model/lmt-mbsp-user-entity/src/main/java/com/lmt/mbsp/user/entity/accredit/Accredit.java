package com.lmt.mbsp.user.entity.accredit;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 6/1/2018 10:28
 * @Description:授权
 */
@Data
@Table(name = "t_accredit")
public class Accredit implements Entity {

    @Id
    private Long id;
    // 角色id
    private Long roleId;
    // 资源ID
    private Long resourceId;
    //  权限
    private String permissions;
    // 权限id
    private Long permissionId;
    // 状态
    private Integer state;

}
