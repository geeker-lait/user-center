package com.lmt.mbsp.user.entity.account;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:48
 * @Description: 用户账号/系统账号
 */
@Data
@Table(name = "t_account")
public class Account implements Entity {
    @Id
    private Long id;
    // 登陆名
    private String accountName;
    // 密码
    private String password;
    //管理员类别(0普通管理员 1超级管理员)
    private Integer managerType;
    //是否管理员(0是，1否)
    private Integer master;
    // 注册类型（0前台注册 1后台创建 2用户中心创建）
    private Integer registerType;
    // 状态(0|1)
    private Integer state;
    // 锁定次数
    private Integer lockTime;
    // 是否被锁定
    private Boolean isLock;
    // 创建时间
    private Date createTime;
}