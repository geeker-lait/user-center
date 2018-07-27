package com.lmt.mbsp.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 描述: 分页组件.
 * 作者: Tangsm.
 * 创建时间: 2018-06-29 9:49.
 */
@Data
public class UserQuery extends PageQueryEntity{
    private Long id;    // 主键ID
    private String name;   // 姓名（模糊搜索）
    private Integer state;  // 状态
    private Integer typ;    // 用户类型
    private Boolean isSupplement;    // 是否补全资料
    private List<Long> ids;     // 用户ID集合
    private List<Integer> typs;     // 类型集合

    private Long groupId;       // 公司ID
    private String deptCode;    // 部门code
}
