package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * 描述：用户列表需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-13 17:13:50
 */
@Data
public class OperatorListInfo {
    // 用户信息
    private Long id;                   // 用户ID
    private String name;               // 姓名
    private Integer state;             // 状态
    private Boolean isSupplement;     // 是否补全资料
    private Integer typ;                // 用户类型（0非内部用户 1内部用户 2内部用户兼外部用户）

    // 账号信息
    private Long accountId;         // 账号ID
    private String username;        // 用户名
    private String account;         // 账号(手机号或邮箱)
    private Boolean isLock;         // 是否锁定
    private Integer registerType;  // 注册类型（0前台注册 1后台创建 2用户中心创建）
    private Integer master;         // 是否被授权管理员(0否，1是)
    private Integer managerType;   // 管理员类别(0普通管理员 1超级管理员)
    private Long lock_time;        // 锁定时间

    // 公司部门信息
    private Long groupId;       // 组ID
    private Long deptId;        // 部门ID
}
