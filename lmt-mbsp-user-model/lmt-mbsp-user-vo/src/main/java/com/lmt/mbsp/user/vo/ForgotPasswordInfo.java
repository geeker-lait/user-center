package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * @描述：前台忘记密码传入的参数
 * @作者：Tangsm
 * @创建时间：2018-07-16 11:22:46
 */
@Data
public class ForgotPasswordInfo {
    private String account;         // 账号
    private String code;            // 图片验证码
    private String smsCode;         // 手机验证码
}
