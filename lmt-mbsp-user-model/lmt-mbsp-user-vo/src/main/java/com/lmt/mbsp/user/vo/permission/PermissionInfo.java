package com.lmt.mbsp.user.vo.permission;

import lombok.Data;

import java.util.Date;

/**
 * @描述: 权限查询参数.
 * @作者: lijing.
 * @创建时间: 2018-06-28 11:52.
 * @版本: 1.0 .
 */
@Data
public class PermissionInfo {
    private Long id;
    //权限名称
    private String name;
    // 权限值
    private Integer val;
    // 权限码
    private String code;
    //权限对应的图标
    private String icon;
    //状态(0|1) 0 可用  1 禁用
    private Integer state;
    // 父编码
    private String parentCode;
    // 权限的描述
    private String descr;
    // 创建时间
    private Date createTime;
}
