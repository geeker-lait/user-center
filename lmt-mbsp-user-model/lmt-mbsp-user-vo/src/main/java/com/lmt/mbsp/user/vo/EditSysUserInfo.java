package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * 描述：修改系统用户传入的参数
 * 作者：Tangsm
 * 创建时间：2018-07-16 15:38:02
 */
@Data
public class EditSysUserInfo {
    // 公司信息
    private Long groupId;       // 组ID

    // 部门信息
    private String deptIds;     // 部门ID集合，可能兼职管理多个部门

    // 用户信息
    private Long id;            // 用户ID
    private String name;        // 姓名
    private String email;       // 邮箱
    private String qq;          // QQ
    private Integer sex;        // 性别
    private Integer age;        // 年龄
    private String idCard;      // 身份证
    private String domicile;    // 户籍信息
    private String phone;       // 座机

    // 账号信息
    private Long accountId;     // 账号ID
    private String username;    // 用户名
    private String account;      // 账号(手机号或邮箱)
    private Integer managerType;   // 管理员类别(0普通管理员 1超级管理员)
    private Long categoryId;    // 职位类别ID
    private String position;    // 职位名称
}
