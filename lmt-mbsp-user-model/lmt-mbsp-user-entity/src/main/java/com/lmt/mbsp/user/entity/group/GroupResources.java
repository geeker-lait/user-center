package com.lmt.mbsp.user.entity.group;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @描述: 分页组件.
 * @作者: lijing.
 * @创建时间: 2018-06-26 15:35.
 * @版本: 1.0 .
 */
@Data
@Entity
@Table(name = "t_group_resources")
@ToString
public class GroupResources implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id; //主键自增
    private String name; //资源名称
    private String uri; //资源定位
    private String icon; //资源icon图标
    private Integer state; //状态(0|1)
}
