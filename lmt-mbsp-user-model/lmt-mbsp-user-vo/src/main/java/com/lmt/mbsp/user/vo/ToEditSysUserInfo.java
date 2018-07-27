package com.lmt.mbsp.user.vo;

import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.entity.group.GroupUser;
import com.lmt.mbsp.user.entity.role.Role;
import com.lmt.mbsp.user.entity.user.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * 描述：编辑系统用户需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-18 11:46:14
 */
@Data
public class ToEditSysUserInfo {
    // 用户信息
    private String name;            // 姓名
    private Integer age;            // 年龄
    private String domicile;        // 户籍地
    private String idCard;          // 身份证号
    private String email;           // 邮箱
    private String qq;              // QQ
    private Integer sex;            // 性别(0男 1女)
    private String phone;           // 座机

    private List<Long> deptIds;       // 所在部门Id集合信息
}
