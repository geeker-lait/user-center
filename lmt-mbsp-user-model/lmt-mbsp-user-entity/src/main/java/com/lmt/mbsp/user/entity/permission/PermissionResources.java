package com.lmt.mbsp.user.entity.permission;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:54
 * @Description:
 */
@Data
@Entity
@Table(name = "t_permission_resources")
@ToString
public class PermissionResources implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id; //主键自增
    private Long permissionId; //权限ID
    private Long resourcesId; //资源ID
}
