package com.lmt.mbsp.user.vo.dept;

import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * 描述：展示部门树所需数据
 * 作者：Tangsm
 * 创建时间：2018-07-31 15:57:10
 */
@Data
public class DeptTreeInfo {
    private Long id;                    // id主键
    private Long groupId;               // 公司ID
    private Long pid;                   // 父部门ID
    private String name;                // 组织名称
    private String code;                // 部门编码(公司类型可为空)
    private String codePath;          // 当前级+父级编码组合（|01|0101|）
    private String organizationCode;    // 组织机构代码证
    private Integer typ;                // 组类型（0内部组 1非内部组）
    private Integer grade;              // 级别（0公司 1部门）
    private Integer state;              // 状态
    private Integer auditState;        // 审核状态（0待审核 1审核通过2审核拒绝）
    private Date createTime;            // 创建时间

    private List<DeptTreeInfo> children;
//    private GroupInfo parent;
}
