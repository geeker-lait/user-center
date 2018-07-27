package com.lmt.mbsp.user.vo;

import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.user.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * 描述：用户详细页需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-18 11:46:14
 */
@Data
public class UserDetailInfo {
    private UserInfo user;              // 用户信息
    private List<AccountInfo> accounts; // 账号信息
    private Map<Long, Group> groups;   // 按公司进行分组，将公司及公司下的部门信息组合
    private List<RoleInfo> roles;       // 当前系统所有角色
    private List<RoleInfo> giveRoles;   // 已赋角色
}
