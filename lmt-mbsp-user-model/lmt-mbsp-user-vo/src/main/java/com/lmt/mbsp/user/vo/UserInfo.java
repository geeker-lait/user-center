package com.lmt.mbsp.user.vo;

import lombok.Data;

/**
 * 描述: 用户查询对象.
 * 作者: Tangsm.
 * 创建时间: 2018-06-26 16:55.
 */
@Data
public class UserInfo {
    private Long groupId;       // 组ID

    private Long accountId;     // 账号ID
    private Long id;            // 用户ID
    private String username;    // 用户名
    private String name;        // 姓名
    private String email;       // 邮箱
    private String qq;          // QQ
    private Integer sex;        // 性别
    private Integer age;        // 年龄
    private String idCard;      // 身份证
    private String domicile;    // 户籍信息
    private String account;      // 账号(手机号或邮箱)
    private String phone;       // 座机
    private String smsCode;     // 短信验证码
    private Integer state;      // 状态
}
