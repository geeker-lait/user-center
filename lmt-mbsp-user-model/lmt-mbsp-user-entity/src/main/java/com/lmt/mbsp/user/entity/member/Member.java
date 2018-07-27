package com.lmt.mbsp.user.entity.member;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:48
 * @Description:会员表
 */
@Entity
@Data
@Table(name="t_member")
public class Member implements com.lmt.framework.support.entity.Entity {
    //主键自增
    @Id
    private Long id;
    // 会员名称
    private String name;
    //会员等级
    private Integer level;
    //会员等级勋章
    private String icon;
}
