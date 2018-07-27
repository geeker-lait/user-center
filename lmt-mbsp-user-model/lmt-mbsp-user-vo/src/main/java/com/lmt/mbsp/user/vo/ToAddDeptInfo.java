package com.lmt.mbsp.user.vo;

import lombok.Data;

import java.util.List;

/*
 * 描述：进入新增部门页面需要的数据
 * 作者：Tangsm
 * 创建时间：2018-07-23 17:00:37
 */
@Data
public class ToAddDeptInfo {
    private List<GroupInfo> parentInfos;    // 父部门信息
    private List<RoleInfo> roleInfos;       // 所有角色信息

}
