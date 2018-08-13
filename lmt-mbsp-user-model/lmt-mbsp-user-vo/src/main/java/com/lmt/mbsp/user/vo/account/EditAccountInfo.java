package com.lmt.mbsp.user.vo.account;

import lombok.Data;

/*
 * 描述：编辑系统用户账号所需参数
 * 作者：Tangsm
 * 创建时间：2018-07-27 13:45:28
 */
@Data
public class EditAccountInfo {
    private Long id;    // 主键ID
    private String oldName;  // 旧账号名称
    private String newName; // 新账号名称
    private Integer type;   // 账号名称表类型
    private String code;     // 验证码
}
