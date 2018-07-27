package com.lmt.mbsp.user.vo;

import lombok.Data;

import java.util.List;

/*
 * 描述：进入企业商户授权页面所需展示的数据
 * 作者：Tangsm
 * 创建时间：2018-07-24 18:53:35
 */
@Data
public class ToCompanyAuthorizeInfo {
    private Long id;    // 公司ID
    private String name;    // 公司名称
    private List<RoleInfo> roles;       // 当前系统所有角色
    private List<Long> giveRoleIds;   // 已赋角色
}
