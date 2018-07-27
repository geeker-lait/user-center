package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * @描述：新增系统操作员传入的参数
 * @作者：Tangsm
 * @创建时间：2018-07-16 13:14:35
 */
@Data
public class AddSysUserInfo {
    private Long groupId;       // 组ID
    private String accountName; // 用户名
    private String email;       // 邮件
    private String mobile;      // 手机号
    private String password;    // 密码
    private String name;        // 姓名
    private String deptIds;     // 部门ID集合，可能兼职管理多个部门
    private Integer managerType;   // 管理员类别(0普通管理员 1超级管理员)
    private Long categoryId;    // 职位类别ID
    private String position;    // 职位名称
}
