package com.lmt.mbsp.user.vo.role;

import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * 描述：进入编辑角色页面需展示的数据
 * 作者：Tangsm
 * 创建时间：2018-08-01 15:59:37
 */
@Data
public class ToEditRoleInfo {
    private RoleInfo roleInfo;  // 角色信息
    private List<Long> givenPermitIds; // 已赋权限ID集合
}
