package com.lmt.mbsp.user.entity.resources;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @描述: 链接资源，用于权限
 * @作者: lijing.
 * @创建时间: 2018-06-26 14:04.
 * @版本: 1.0 .
 */
@Data
@Entity
@Table(name="t_url_resources")
@ToString
public class UrlResources  implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id; //主键自增
    private String name; //资源名称
    private String uri; //资源定位
    private String icon; //资源icon图标
    private Integer state; //状态(0|1)
    private String code;// 编码
    private String parentCode;// 父编码
    private String description; // 描述
    private Integer sort;      // 序号
    private Date createTime;    // 创建时间
}
