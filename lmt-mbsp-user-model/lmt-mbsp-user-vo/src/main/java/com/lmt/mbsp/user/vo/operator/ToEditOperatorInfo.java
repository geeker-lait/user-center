package com.lmt.mbsp.user.vo.operator;

import lombok.Data;

import java.util.List;

/*
 * 描述：进入编辑操作员页面所需展示的数据
 * 作者：Tangsm
 * 创建时间：2018-07-30 17:08:19
 */
@Data
public class ToEditOperatorInfo {
    private Long accountId;     // 账号ID
    private String accountName; // 主账号名称
    private String name;           // 真实姓名
    private List<Long> givenRoleIds;   // 已赋角色
}
