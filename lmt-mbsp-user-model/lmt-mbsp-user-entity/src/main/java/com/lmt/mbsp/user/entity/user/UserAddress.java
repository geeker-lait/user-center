package com.lmt.mbsp.user.entity.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @描述: 分页组件.
 * @作者: lijing.
 * @创建时间: 2018-06-26 14:49.
 * @版本: 1.0 .
 */
@Data
@Entity
@Table(name="t_user_address")
public class UserAddress implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id;
    //用户ID
    private Long user_id;
    //姓名
    private String name;
    //地址
    private String address;
    //联系方式
    private String phone;
    //排序
    private Integer sort;
    //状态 0 启动 1 禁用
    private Integer state;
    // 创建时间
    private Date createTime;
}
