package com.lmt.mbsp.user.vo.accredit;

import lombok.Data;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/24/2018 20:23
 * @Description:
 */
@Data
public class AccreditInfo {
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