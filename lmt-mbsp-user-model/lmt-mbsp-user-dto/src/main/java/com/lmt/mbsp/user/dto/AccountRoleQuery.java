package com.lmt.mbsp.user.dto;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import java.util.List;

/*
 * @描述：账号角色查询参数
 * @作者：Tangsm
 * @创建时间：2018-07-18 16:52:06
 */
@Data
public class AccountRoleQuery extends PageQueryEntity{
    private Long id;    // 主键ID
    private Long roleId;    // 角色ID
    private Long accountId; // 账号ID
    private List<Long> accountIds;  // 账号ID集合
}
