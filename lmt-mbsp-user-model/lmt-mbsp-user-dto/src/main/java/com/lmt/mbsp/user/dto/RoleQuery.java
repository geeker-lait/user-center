package com.lmt.mbsp.user.dto;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import java.util.List;

/**
 * @描述: 分页组件.
 * @作者: lijing.
 * @创建时间: 2018-06-29 14:02.
 * @版本: 1.0 .
 */
@Data
public class RoleQuery extends PageQueryEntity{
    private Long id;    // 主键ID
    private String name;    // 角色名称
    private Integer state;  // 状态
    private List<Long> ids; // 主键ID集合
}
