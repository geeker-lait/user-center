package com.lmt.mbsp.user.vo.account;

import lombok.Data;

import java.util.List;

/*
 * 描述：进入账号授权页面需展示的数据
 * 作者：Tangsm
 * 创建时间：2018-07-27 11:56:09
 */
@Data
public class ToAccountAuthorizeInfo {
    private Long userId;    // 用户ID
    private String name;    // 用户姓名
    private Long accountId; // 账号ID
    private String accountName; // 主账号名称

    private List<Long> givenRoleIds;    // 已赋角色ID集合
//    private List<RoleInfo> roleInfos;   // 所有角色集合（所有可控组角色信息通过调用角色中的接口获取）
}
