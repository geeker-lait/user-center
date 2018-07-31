package com.lmt.mbsp.user.vo.dept;

import lombok.Data;

/*
 * 描述：更新部门信息参数
 * 作者：Tangsm
 * 创建时间：2018-07-23 11:36:09
 */
@Data
public class EditDeptInfo {
    private Long id;    // 主键ID
    private String name;    // 部门名称
    private String roleIds; // 角色ID集合
}
