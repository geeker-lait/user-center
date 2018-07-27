package com.lmt.mbsp.user.vo;

import lombok.Data;

/**
 * @描述: 账号查询.
 * @作者: lijing.
 * @创建时间: 2018-06-28 11:51.
 * @版本: 1.0 .
 */
@Data
public class AccountInfo {
    // 手机号或邮箱
    private String account;
    // 用户名
    private String username;
    // 登陆密码
    private String password;
    // 确认密码
    private String pwdAgain;
}
