package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * @描述：新增操作员传入的参数
 * @作者：Tangsm
 * @创建时间：2018-07-16 10:59:47
 */
@Data
public class AddOperatorInfo {
    private Long groupId;       // 组ID

    private String username;    // 用户名
    private String name;        // 姓名
    private String password;    // 密码
    private String againPassword;   // 确认密码

    private String roleIds;       // 角色ID集合
}
