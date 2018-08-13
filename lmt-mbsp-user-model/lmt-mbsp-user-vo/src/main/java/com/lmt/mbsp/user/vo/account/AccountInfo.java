package com.lmt.mbsp.user.vo.account;

import lombok.Data;

import java.util.Date;

/**
 * 描述: 账号查询.
 * 作者: Tangsm.
 * 创建时间: 2018-06-28 11:51.
 */
@Data
public class AccountInfo {
    private Long id;                // 账号主键ID
    private Integer managerType;    // 管理员类别(0普通管理员 1超级管理员)
    private Integer master;         // 是否被授权管理员(0否，1是)
    private Integer registerType;  // 注册类型（0前台注册 1后台创建 2用户中心创建）
    private Integer lockTimes;         // 锁定次数
    private Boolean isLock;           // 是否被锁定
    private Integer state;          // 状态(0正常 1禁用)
    private Date createTime;       // 创建时间
}
