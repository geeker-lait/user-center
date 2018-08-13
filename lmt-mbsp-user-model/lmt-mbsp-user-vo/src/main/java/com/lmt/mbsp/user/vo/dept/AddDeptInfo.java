package com.lmt.mbsp.user.vo.dept;

import lombok.Data;

/*
 * 描述：创建部门传入的参数
 * 作者：Tangsm
 * 创建时间：2018-07-20 15:27:38
 */
@Data
public class AddDeptInfo {
    private String name;    // 部门名称
    private String roleIds; // 角色ID集合
}
