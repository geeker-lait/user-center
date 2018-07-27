package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * @描述：编辑操作员传入的信息
 * @作者：Tangsm
 * @创建时间：2018-07-16 09:57:39
 */
@Data
public class EditOperatorInfo {
    private Long userId;                // 用户ID
    private Long accountId;         // 账号ID
    private String roleIds;         // 角色ID集合
}
