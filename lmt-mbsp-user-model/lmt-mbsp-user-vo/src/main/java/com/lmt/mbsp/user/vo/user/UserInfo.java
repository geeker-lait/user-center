package com.lmt.mbsp.user.vo.user;

import lombok.Data;

import java.util.Date;

/**
 * 描述: 用户查询对象.
 * 作者: Tangsm.
 * 创建时间: 2018-06-26 16:55.
 */
@Data
public class UserInfo {
    private Long id;            // 用户ID
    private String name;        // 姓名
    private String email;       // 邮箱
    private String qq;          // QQ
    private Integer sex;        // 性别
    private Integer age;        // 年龄
    private String idCard;      // 身份证
    private String domicile;    // 户籍信息
    private String phone;       // 座机
    private Integer state;      // 状态
    private Boolean isSupplement;// 是否已经补全信息
    private Date createTime;    // 创建时间
}
