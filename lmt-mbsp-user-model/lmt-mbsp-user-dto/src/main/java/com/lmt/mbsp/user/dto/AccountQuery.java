package com.lmt.mbsp.user.dto;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import java.util.List;

/**
 * @描述: 账号查询输入参数.
 * @作者: lijing.
 * @创建时间: 2018-06-28 11:51.
 * @版本: 1.0 .
 */
@Data
public class AccountQuery extends PageQueryEntity{
    private Long id;    // 主键ID
    private String accountName;// 用户名
    private String password; // 密码
    private Integer state;  //  状态
    private Boolean isLock; // 是否锁定
    private Integer master; // 授权管理员类型
    private Integer registerType;   // 注册类型
    private List<Long> ids; // id集合
}
