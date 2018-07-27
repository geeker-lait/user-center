package com.lmt.mbsp.user.entity.resources;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:50
 * @Description: 资源
 */
@Data
@Table(name="t_resources")
@ToString
public class Resources implements com.lmt.framework.support.entity.Entity {
    // id
    @Id
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
