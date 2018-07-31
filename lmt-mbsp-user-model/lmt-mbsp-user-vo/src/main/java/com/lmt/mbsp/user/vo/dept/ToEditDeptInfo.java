package com.lmt.mbsp.user.vo.dept;

import com.lmt.mbsp.user.vo.group.GroupInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * 描述：编辑部门信息需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-23 12:03:20
 */
@Data
public class ToEditDeptInfo {
    private Long id;    // 部门主键ID
    private String name;    // 部门名称
    private String code;    // 部门编码
    private Date createTime;    // 创建时间
    private Integer state;      // 状态(0正常 1禁用)
    private List<GroupInfo> parentInfos;    // 父部门信息
    private List<Long> selectedRoleIds;   // 已赋角色ID集合
}
