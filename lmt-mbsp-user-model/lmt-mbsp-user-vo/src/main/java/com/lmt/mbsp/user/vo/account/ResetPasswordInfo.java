package com.lmt.mbsp.user.vo.account;

import lombok.Data;

/*
 * @描述：重置密码参数
 * @作者：Tangsm
 * @创建时间：2018-07-16 11:24:29
 */
@Data
public class ResetPasswordInfo {
    private String mobile;         // 手机号
    private String code;            // 验证码
    private String newPwd;          // 新密码
    private String newPwdAgain;     // 确认新密码
}
