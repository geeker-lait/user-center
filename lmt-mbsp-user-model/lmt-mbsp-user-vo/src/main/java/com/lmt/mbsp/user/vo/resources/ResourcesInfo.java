package com.lmt.mbsp.user.vo.resources;

import lombok.Data;

import java.util.Date;

/**
 * @描述: 资源.
 * @作者: lijing.
 * @创建时间: 2018-06-28 11:55.
 * @版本: 1.0 .
 */
@Data
public class ResourcesInfo  {

    private Long id;
    // 资源父ID
    private Long pid;
    // 具体资源ID（暂时不用，作为扩展字段）
    private Long resourceId;
    // 资源名称
    private String name;
    // 资源值
    private String data;
    // 资源path
    private String path;
    // 资源的图标
    private String icon;
    // 资源类型
    private Integer typ;
    // 资源排序
    private Integer sort;
    // 状态(0正常 1禁用)
    private Integer state;
    // 创建时间
    private Date createTime;

}
