package com.lmt.mbsp.user.entity.group;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * 描述：部门
 * 作者：Tangsm
 * 创建时间：2018-08-02 14:30:40
 */
@Data
@javax.persistence.Entity
@Table(name = "t_department")
public class Department implements com.lmt.framework.support.entity.Entity{
    @Id
    private Long id;    // 主键ID
    private Long groupId;   // 组ID
    private String name;    // 部门名称
    private Integer state;//状态
    private Date createTime;//创建时间
}
