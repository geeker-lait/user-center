package com.lmt.mbsp.user.dto;

import lombok.Data;

/*
 * @描述：账号查询入参
 * @作者：Tangsm
 * @创建时间：2018-07-16 18:58:21
 */
@Data
public class AccountNameQuery extends PageQueryEntity{
    private Long id;        // 主键ID
    private Long accountId; // 账号ID
    private String accountName;    // 用户名
    private Integer type;        // 类型（0主账号，1手机，2邮箱）

}
