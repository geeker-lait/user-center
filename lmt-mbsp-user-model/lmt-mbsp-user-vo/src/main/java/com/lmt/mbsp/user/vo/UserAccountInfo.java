package com.lmt.mbsp.user.vo;

import lombok.Data;

/**
 * 描述: 用户账号查询参数.
 * 作者: Tangsm.
 * 创建时间: 2018-07-06 15:41.
 */
@Data
public class UserAccountInfo {
    private Long accountId;     // 账号ID
    private Long userId;        // 用户ID
    private String username;    // 用户名
    private String name;        // 姓名
    private String email;       // 邮箱
    private String qq;          // QQ
    private Integer sex;        // 性别
    private Integer age;        // 年龄
    private String idcard;      // 身份证
    private String domicile;    // 户籍信息
    private String mobile;      // 手机号
    private String smsCode;     // 手机验证码
}
