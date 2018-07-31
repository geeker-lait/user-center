package com.lmt.mbsp.user.vo.operator;

import com.lmt.mbsp.user.vo.role.RoleInfo;
import lombok.Data;

import java.util.List;

/*
 * 描述：用户列表需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-13 17:13:50
 */
@Data
public class OperatorListInfo {
    // 用户信息
    private Long userId;                   // 用户ID
    private String name;               // 姓名
    private Boolean isSupplement;     // 是否补全资料
    private Integer typ;                // 用户类型（0非内部用户 1内部用户 2内部用户兼外部用户）

    // 账号信息
    private Long accountId;         // 账号ID
    private String accountName;        // 用户名
    private Boolean isLock;         // 是否锁定
    private Integer state;             // 状态
    private Integer registerType;  // 注册类型（0前台注册 1后台创建 2用户中心创建）
    private Integer master;         // 是否被授权管理员(0否，1是)
    private Integer managerType;   // 管理员类别(0普通管理员 1超级管理员)
    private Integer lockTimes;        // 锁定次数

    // 公司部门信息
    private Long groupId;       // 组ID
    private Long deptId;        // 部门ID

    private List<RoleInfo> roles;   // 拥有角色
}
