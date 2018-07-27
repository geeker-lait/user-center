package com.lmt.mbsp.user.vo;

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
    // 创建时间
    private Date createTime;

}
