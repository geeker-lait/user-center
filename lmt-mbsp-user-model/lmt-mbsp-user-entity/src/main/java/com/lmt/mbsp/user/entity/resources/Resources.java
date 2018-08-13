package com.lmt.mbsp.user.entity.resources;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;
import lombok.ToString;

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
public class Resources implements Entity {
    // id
    @Id
    private Long id;
    // 资源父ID
    private Long pid;

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
    // 具体资源ID（暂时不用，作为扩展字段）
    private Long resourceId;
    // 资源排序
    private Integer sort;
    // 状态(0正常 1禁用)
    private Integer state;
    // 创建时间
    private Date createTime;
}
