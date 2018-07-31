package com.lmt.mbsp.user.dto;

import lombok.Data;

/*
 * @描述：组角色查询参数
 * @作者：Tangsm
 * @创建时间：2018-07-27 15:28:06
 */
@Data
public class GroupRoleQuery extends PageQueryEntity{
    private Long id;    // 主键ID
    private Long roleId;    // 角色ID
    private Long groupId; // 组ID
}
