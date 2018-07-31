package com.lmt.mbsp.user.vo.account;

import lombok.Data;

/*
 * 描述：绑定账号所需参数
 * 作者：Tangsm
 * 创建时间：2018-07-30 16:24:31
 */
@Data
public class BindAccountInfo {
    private Long accountId; // 账号ID
    private String name;    // 账号名称
    private String code;    // 验证码
    private Integer type;   // 类型（0主账号，1手机，2邮箱）
}
