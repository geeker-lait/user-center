package com.lmt.mbsp.user.vo.account;

import lombok.Data;

/*
 * @描述：修改密码传入的参数
 * @作者：Tangsm
 * @创建时间：2018-07-16 11:27:45
 */
@Data
public class EditPasswordInfo {
    private Long accountId;         // 账号ID(登录信息赋值)
    private String oldPwd;          // 旧密码
    private String newPwd;          // 新密码
    private String newPwdAgain;     // 确认新密码
}
