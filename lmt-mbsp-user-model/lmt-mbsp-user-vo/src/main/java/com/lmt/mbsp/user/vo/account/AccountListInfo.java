package com.lmt.mbsp.user.vo.account;

import com.lmt.mbsp.user.vo.role.RoleInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * 描述：账号集合数据
 * 作者：Tangsm
 * 创建时间：2018-07-31 17:30:42
 */
@Data
public class AccountListInfo {
    private Long id;                // 账号主键ID
    private Integer managerType;    // 管理员类别(0普通管理员 1超级管理员)
    private Integer master;         // 是否被授权管理员(0否，1是)
    private Integer registerType;  // 注册类型（0前台注册 1后台创建 2用户中心创建）
    private Integer lockTimes;         // 锁定次数
    private Boolean isLock;           // 是否被锁定
    private Integer state;          // 状态(0正常 1禁用)
    private Date createTime;       // 创建时间

    private List<AccountNameInfo> accounNameInfos;   // 账号名称信息集合
    private List<RoleInfo> roleInfos;       // 角色信息集合
}
