package com.lmt.mbsp.user.vo.dept;

import com.lmt.mbsp.user.vo.role.RoleInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * 描述：部门详细页需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-23 15:39:26
 */
@Data
public class DeptDetailInfo {
    private Long id;    // 部门主键ID
    private String name;    // 部门名称
    private Date createTime;    // 创建时间
    private Integer state;      // 状态(0正常 1禁用)
    private List<RoleInfo> selectedRoleIds;   // 已赋角色集合
}
