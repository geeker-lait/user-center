package com.lmt.mbsp.user.vo;

import lombok.Data;
import org.omg.CORBA.INTERNAL;

import java.util.Date;
import java.util.List;

/*
 * 描述：部门详细页需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-23 15:39:26
 */
@Data
public class DeptDetailInfo {
    private GroupInfo deptInfo;                // 部门信息
    private List<GroupInfo> parentInfos;    // 父部门信息
    private List<RoleInfo> selectedRoleIds;   // 已赋角色集合
}
