package com.lmt.mbsp.user.vo;

import com.lmt.mbsp.user.entity.group.Group;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述: 群组返回参数.
 * 作者: Tangsm.
 * 创建时间: 2018-06-28 11:51.
 */
@Data
public class GroupInfo {
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
    private Date createTime;            // 创建时间

    private List<GroupInfo> children;
    private GroupInfo parent;
}
