package com.lmt.mbsp.user.vo.user;

import lombok.Data;

/*
 * 描述：用户中心编辑个人信息所需参数
 * 作者：Tangsm
 * 创建时间：2018-07-27 14:29:29
 */
@Data
public class EditUserInfo {
    // 用户信息
    private Long id;            // 用户ID(登录信息赋值)
    private String name;        // 姓名
    private String email;       // 邮箱
    private String qq;          // QQ
    private Integer sex;        // 性别
    private Integer age;        // 年龄
    private String idCard;      // 身份证
    private String domicile;    // 户籍信息
    private String phone;       // 座机

    private Long accountId;     // 账号ID，用户中心完善资料时与账号表关联使用
}
