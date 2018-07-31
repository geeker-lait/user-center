package com.lmt.mbsp.user.vo.account;

import lombok.Data;

/*
 * 描述：账号名称参数
 * 作者：Tangsm
 * 创建时间：2018-07-26 18:30:22
 */
@Data
public class AccountNameInfo {
    private Long id;    // 主键ID
    private String accountName; // 账号名称
    private Integer type;       // 类型（0主账号，1手机，2邮箱）
}
