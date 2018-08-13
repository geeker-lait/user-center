package com.lmt.mbsp.user.dto;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

/*
 * @描述：用户组查询参数
 * @作者：Tangsm
 * @创建时间：2018-07-18 14:39:25
 */
@Data
public class GroupUserQuery extends PageQueryEntity{
    private Long id;    // 主键ID
    private Long groupId;    // 组ID
    private Long userId;    // 用户ID
    private Integer type;   // 组类型（0公司 1部门）
}
