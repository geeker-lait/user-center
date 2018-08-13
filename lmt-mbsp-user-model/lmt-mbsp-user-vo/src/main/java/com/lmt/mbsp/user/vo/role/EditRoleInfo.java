package com.lmt.mbsp.user.vo.role;

import lombok.Data;

import java.util.Date;

/*
 * 描述：编辑角色所需传入的参数
 * 作者：Tangsm
 * 创建时间：2018-08-01 16:17:19
 */
@Data
public class EditRoleInfo {
    private Long id;
    private String name;// 角色名
    private String descr; // 描述
    private String permissionIds;   // 权限ID集合（多个英文逗号分隔）
}
