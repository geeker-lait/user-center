package com.lmt.mbsp.user.vo.account;

import lombok.Data;

import java.util.List;

/*
 * 描述：保存用户授权传入的参数
 * 作者：Tangsm
 * 创建时间：2018-07-19 14:13:03
 */
@Data
public class SaveAccountAuthorizeInfo {
    private Long userId;    // 用户ID
    private Long accountId; // 账号ID
    private String roleIds;         // 角色ID集合
}
