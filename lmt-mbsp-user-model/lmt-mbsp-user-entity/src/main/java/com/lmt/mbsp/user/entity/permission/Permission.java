package com.lmt.mbsp.user.entity.permission;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:49
 * @Description: 权限
 */
@Data
@Table(name="t_permission")
public class Permission implements Entity {
    //主键自增
    @Id
    private Long id;
    // 父ID
    private Long pid;
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
