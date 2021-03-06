package com.lmt.mbsp.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 描述: 账号查询输入参数.
 * 作者: Tangsm.
 * 创建时间: 2018-06-28 11:51.
 */
@Data
public class GroupQuery extends PageQueryEntity {
    private Long id;    // 主键ID
    private Long groupId;   // 公司ID
    private Long pid;   // 父部门ID
    private String name;    // 名称(模糊搜索)
    private String code;    // 当前级别编码
    private String codePath;    // 当前级+父级编码组合（|01|0101|，模糊搜索）
    private List<String> codes; // 编码集合
    private List<Long> ids;     //  id集合
    private Integer state;  // 状态
    private Integer auditState; // 审核状态
    private Integer typ;        // 组类型（0内部组 1非内部组）
    private Integer grade;      // 级别（0公司 1部门）

    private String sortName;    // 排序字段
    private Integer sortType;   // 排序类型（0升序，1降序）
}
