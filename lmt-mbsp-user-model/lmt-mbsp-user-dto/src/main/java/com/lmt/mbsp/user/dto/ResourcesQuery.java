package com.lmt.mbsp.user.dto;

import lombok.Data;

/**
 * @描述: 分页组件.
 * @作者: lijing.
 * @创建时间: 2018-06-29 14:01.
 * @版本: 1.0 .
 */
@Data
public class ResourcesQuery extends PageQueryEntity {
    private Long id;
    // 资源ID
    private Long resourcesId;
    // 资源path
    private String path;
    // 资源uri
    private String uri;
    // 资源名称
    private String name;
    // 资源的图标
    private Integer typ;
    // 状态(0正常 1禁用)
    private Integer state;
}
