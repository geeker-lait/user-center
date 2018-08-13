package com.lmt.mbsp.user.entity.user;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * Auther: Tangsm
 * Description: 用户
 */
@Data
@Table(name = "t_user")
public class User implements Entity  {
    @Id
    private Long id;
    // 真实名称
    @Column(name="name",columnDefinition=("varchar(50)  default null comment '真实名称'"))
    private String name;
    // 年龄
    @Column(columnDefinition=("int(11)  default null comment '年龄'"))
    private Integer age;
    // 性别
    @Column(columnDefinition=("int(11)  default null comment '性别'"))
    private Integer sex;
    // 身份证
    @Column(columnDefinition=("varchar(50)  default null comment '身份证'"))
    private String idCard;
    // 出生地/户籍所在地/第一地址/户口本所在地/
    @Column(columnDefinition=("varchar(50)  default null comment '出生地'"))
    private String domicile;
    // 创建时间
    @Column(columnDefinition=("datetime  default null comment '创建时间'"))
    private Date createTime;
    // 状态
    @Column(columnDefinition=("int(11)  default 1 comment '状态,0禁用，1启用'"))
    private Integer state;
    // 出生地/户籍所在地/第一地址/户口本所在地/
    @Column(columnDefinition=("varchar(50)  default null comment '邮箱'"))
    private String email;
    // 出生地/户籍所在地/第一地址/户口本所在地/
    @Column(columnDefinition=("varchar(20)  default null comment 'QQ'"))
    private String qq;
    // 是否已经补全信息
    private Boolean isSupplement;
    // 座机
    private String phone;
}
