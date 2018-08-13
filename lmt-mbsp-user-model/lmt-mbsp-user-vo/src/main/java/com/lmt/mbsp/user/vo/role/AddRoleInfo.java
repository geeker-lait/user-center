package com.lmt.mbsp.user.vo.role;

import lombok.Data;

/*
 * 描述：新增角色所需参数
 * 作者：Tangsm
 * 创建时间：2018-08-01 14:56:10
 */
@Data
public class AddRoleInfo {
    private String name;    // 角色名称
    private String descr; // 角色描述]
    private String permissionIds;   // 权限ID集合（多个英文逗号分隔）
}
